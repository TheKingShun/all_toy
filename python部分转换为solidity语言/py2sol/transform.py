import logging

from buildSol import build
from sol_astructs import *
from type_map import *

LOG_FORMAT = "%(filename)s - %(lineno)s - %(levelname)s ===%(message)s==="
logging.basicConfig(level=logging.DEBUG, format=LOG_FORMAT)


class ASTParse:
    old_json = None

    parser = {}
    sol = build()
    contract_name = ""
    g_vars = []
    funs = []
    index = 0

    def __init__(self):
        self.parser['ClassDef'] = self.parse_class_def
        self.parser['FunctionDef'] = self.parse_func_def
        self.parser['Assign'] = self.parse_assign
        self.parser['BinOp'] = self.parse_bop
        self.parser['Constant'] = self.parse_constant
        self.parser['Return'] = self.parse_return
        self.parser['Name'] = self.parse_name
        self.parser['If'] = self.parse_if
        self.parser['Attribute'] = self.parse_attribute
        self.parser['Expr'] = self.parse_expr

    def parse_expr(self, node):
        self.log_start("expr")
        logging.info("expr not implement!")
        self.log_end("expr")
        pass

    def parse_attribute(self, node):
        self.log_start("Attributes")

        ret = self.get_item(node, 'attr')

        self.log_end("Attributes")
        return ret

    def parse_if(self, node):
        self.log_start("If")

        statement_if = If()
        # 这里的test是条件的节点
        test_node = self.get_item(node, 'test')
        left = self.parse_node(self.get_item(test_node, 'left'))
        ops = self.get_item(self.get_item(test_node, 'ops')[0], 'ast_type')
        comparators = self.parse_node(self.get_item(test_node, 'comparators')[0])
        body = self.get_item(node, 'body')
        for node in body:
            ret = self.parser[self.get_item(node, 'ast_type')](node)
            statement_if.body.append(ret)
        condition = (left, ops, comparators)

        # logging.info(condition)

        # statement_if.body有可能是空
        ret = build.build_if(condition, statement_if.body)

        self.log_end("If")
        return ret

    def parse_name(self, node):
        self.log_start("Name")

        name = Name()
        name.name = self.get_item(node, 'id')
        # todo 不知道这个字段有什么用 先写着
        name.ctx_ast_type = self.get_item(node, ['ctx', 'ast_type'])

        self.log_end("Name")
        return name.name

    def parse_return(self, node):
        self.log_start("Return")

        ret = ""
        ret += "return "
        ret_value = self.parse_node(self.get_item(node, 'value'))
        ret += ret_value
        ret += ";"

        self.log_end("Return")
        return ret

    def parse_constant(self, node):
        self.log_start("Constant")
        con = Constant()
        con.value = self.get_item(node, 'value')
        # 这个value可能是str 或者 int
        self.log_end("Constant")
        return con.value

    def parse_assign(self, node):
        """
        类似于 ：ret = 1+2
        :param node: 分配节点
        :return:一个sol表示的方法体字符串
        """
        self.log_start("Assign")

        ass = Assign()
        targets_0 = self.get_item(node, 'targets')[0]
        ass.targets_ast_type = self.get_item(targets_0, 'ast_type')
        if ass.targets_ast_type == "Attribute":
            ass.name = self.get_item(targets_0, 'attr')
        else:
            ass.name = self.get_item(targets_0, 'id')
        # 理想情况下的ass。value是一个字符串最好
        ass.value = self.parse_node(self.get_item(node, 'value'))
        # todo 现在只支持uint类型的赋值变量

        # 分类：若为全局变量声明->放进g_vars中  ass.targets_ast_type=="name"代表为声明，Attribute
        # 代表为使用
        if str(ass.name).startswith("__") and ass.targets_ast_type == "Name":
            if type(ass.value) == int:
                if ass.value is None:
                    ret = "uint " + ass.name + ";"
                else:
                    ret = "uint " + ass.name + " = " + str(ass.value) + ";"
                # 无论是否初始化完成 结果都将放入在全局中
                self.g_vars.append(ret)
                self.log_end("Assign")
                return None

            elif type(ass.value) == str:
                if ass.value is None:
                    ret = "string " + ass.name + ";"

                else:
                    ret = "string " + ass.name + " = \"" + ass.value + "\"" + ";"
                self.g_vars.append(ret)
                self.log_end("Assign")
                return None
            else:
                # logging.warning("还没有这种类型的声明语句")
                # todo
                ret = "uint " + ass.name + ";"
                self.g_vars.append(ret)
                self.log_end("Assign")
                return None

        #  运行到此处代表为使用全局变量
        if str(ass.name).startswith("__"):
            value_type = type(ass.value)
            if ass.value is None:
                ret = str(ass.name)
            elif value_type == str:
                ret = str(ass.name) + " = \"" + str(ass.value) + "\"" + ";"
            else:
                ret = str(ass.name) + " = " + str(ass.value) + ";"
            self.log_end("Assign")
            return ret
        # 运行到此处 代表自身定义
        if ass.value is None:
            # todo 这里默认的是整型参数
            ret = "uint " + ass.name + ";"
            self.log_end("Assign")
            return ret

        ret = "uint " + ass.name + " = " + str(ass.value) + ";"
        self.log_end("Assign")
        return ret

    def parse_func_def(self, node):
        """
        对节点信息未FunctionDef的节点进行解析，将解析后的数据保存在全局的
        funs中
        :param node:FunctionDef节点
        :return: 无返回值，将解析后的信息直接保存在全局
        """
        self.log_start("FunctionDef")

        fun = FunctionDef()
        fun.name = self.get_item(node, 'name')
        # todo 返回solidity类型的函数返回值，默认返回uint
        type = self.get_item(node, ['returns', 'id'])
        if type is not None:
            fun.returns = sol_type.get(type, "uint")

        fun.params = []
        item_arr = self.get_item(node, ['args', 'args'])
        for i in item_arr:
            arg = self.get_item(i, 'arg')
            if arg != "self":
                fun.params.append(arg)

        # 继续遍历方法节点下的参数和方法体 , 此时的body是一个list类型（jsonArray)
        body = self.get_item(node, 'body')
        fun.block = self.parse_fun(body)

        # logging.info(fun.block)

        # logging.info(i for i in fun.block)

        ret = build.build_fun(fun.name, fun.params, returns=fun.returns, block=fun.block)
        self.funs.append(ret)

        self.log_end("FunctionDef")
        pass

    def parse_class_def(self, node):
        self.log_start("ClassDef")
        self.contract_name = self.get_item(node, 'name')
        self.body_parse(node)
        self.log_end("ClassDef")

        pass

    def body_parse(self, node):

        rets = []
        body = None

        try:
            body = node['body']
        except:
            logging.error("{}节点没有body字段".format(node))

        if body is None or len(body) == 0:
            return

        for i in body:
            ast_type = self.get_item(i, "ast_type")
            try:
                parser = self.parser[ast_type]
            except:
                logging.error("没有属于{}的解析器".format(ast_type))
                continue

            ret = parser(i)
            rets.append(ret)

        return rets

    @staticmethod
    def get_item(dict, target):
        if type(target) == list:

            try:
                for i in target:
                    dict = dict[i]
            except:
                logging.warning("{} list is not exist in dict".format(target))

            return dict
        else:
            item = None
            try:
                item = dict[target]
            except:
                logging.error("{}没有{}的属性".format(dict, target))
            return item

    def main_parse(self):
        self.body_parse(self.old_json)
        pass

    def start(self, json_str, save_path):
        self.old_json = json_str
        logging.info("开始")

        self.main_parse()

        solidity_file = self.sol.build_contract(self.contract_name, self.funs, self.g_vars)
        # logging.info(self.g_vars)
        # logging.info("\n" + solidity_file)
        with open(save_path, 'w') as f:
            f.write(solidity_file)

        logging.info("py 2 sol success!!!!!!!!!")

    def parse_fun(self, body):
        """
        对py文件中的函数进行解析，将函数中的方法体中的每一条
        语句保存在block中，并保证在block中的语法是完全符合
        solidity语法格式的
        :param node: 传入的函数节点的信息
        :return: 返回函数的方法体sol表示
        """
        variables = []

        block = []
        for node in body:
            ast_type = self.get_item(node, 'ast_type')
            try:
                parser = self.parser[ast_type]
            except:
                logging.error("parse_fun--没有关于{}的解析器".format(ast_type))
                continue

            ret = parser(node)
            block.append(ret)

        return block

    def parse_node(self, node):
        """
        相当于对孩子节点的信息进行解析，由于存在深层嵌套的可能
        所以在特殊情况下可能会多次调用这个！！！！中转站！！！！
        :param node: 待处理节点
        :return: 处理完节点所返回的信息
        """
        ast_type = self.get_item(node, 'ast_type')
        # logging.info("current 类型是 " + ast_type)
        try:
            parser = self.parser[ast_type]
        except:
            logging.error("[!] parse_node---do not fina {} parser".format(ast_type))
            return None

        ret = parser(node)
        return ret

    def parse_bop(self, node):
        """
        对于 BinOp的节点进行解析处理，返回一条语句的形式
        :param node:
        :return: 字符串
        """
        ret = ""
        bop = BinOp()
        bop.left = self.parse_node(self.get_item(node, 'left'))
        bop.right = self.parse_node(self.get_item(node, 'right'))
        bop.op_type = self.get_item(node, ['op', 'ast_type'])

        if bop.op_type == "Add":
            ret = str(bop.left) + " + " + str(bop.right)
        elif bop.op_type == "Sub":
            ret = str(bop.left) + " - " + str(bop.right)
        elif bop.op_type == "Mult":
            ret = str(bop.left) + " * " + str(bop.right)
        elif bop.op_type == "Div":
            ret = str(bop.left) + " / " + str(bop.right)
        else:
            logging.info("[+]parse_bop-- 中的操作{}尚未完成".format(bop.op_type))

        # logging.info(ret)
        return ret

    def log_start(self, name):
        self.index += 1
        logging.info("——" * self.index + "[+]开始解析 |" + name + "|")

    def log_end(self, name):
        logging.info("——" * self.index + "[-]完成解析 |" + name + "|")
        self.index -= 1
