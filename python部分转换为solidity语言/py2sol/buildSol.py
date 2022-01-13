class build:
    solidity_ret = ""

    def get_sol(self):
        return self.solidity_ret

    def build_head(self):
        head = "pragma solidity ^0.4.18;\n"
        self.solidity_ret += head
        return self.solidity_ret

    def build_contract(self, name, funcs, g_vars):
        """
        这里的func是字符串类型的方法实体
        g_var :是全局变量的声明
        :param name: 合约名
        :param func: 方法字符串list
        :param g_var: 全局变量list
        :return: 返回已经完成的sol文件
        """
        self.build_head()

        con_head = "contract " + name + " {\n"

        con_tail = "}\n"

        self.solidity_ret += con_head

        for g_var in g_vars:
            self.solidity_ret += g_var
            self.solidity_ret += "\n"

        for fun in funcs:
            self.solidity_ret += str(fun)
            self.solidity_ret += "\n"

        self.solidity_ret += con_tail

        return self.solidity_ret

    def build_g_var(self, value=None):
        """
        将节点转换为字符串标识的solidity文件中的全局变量
        :param value: 值，默认没有取值 即没有全局变量
        :return: 返回字符串表示的py全局变量节点
        """
        ret = ""
        if value is None:
            pass
        else:
            for i in value:
                ret += i
                ret += "\n"
        return ret

    @staticmethod
    def build_fun(name, params, visible="public", returns=None, block=None):
        params_str = ""
        return_str = ""
        block_str = ""
        fun_var = []
        for i in params:
            fun_var.append(i)
            param = "uint " + i
            params_str += param

        if returns is None:
            pass
        else:
            return_str = "returns( " + returns + " ) "

        # if block is None:
        #     pass
        # else:
        #     block_str = "\n".join(block)
        if block is None:
            pass
        else:
            for i in block:
                i = str(i)


                # 赋值语句
                state_list = i.split("\n")
                for i in state_list:
                    if str(i).strip().startswith("uint") and "=" in str(i):
                        var_name = i[4:].strip()
                        var_name = var_name[0:var_name.index("=")].strip()
                        if var_name not in fun_var:
                            fun_var.append(var_name)
                        else:
                            i = i[4:]

                    block_str += i
                    block_str += "\n"

        fun_str = "function " + name + "(" + params_str + ") " + visible + " " + return_str + "{\n" \
                  + block_str + "\n}\n"
        return fun_str

    @staticmethod
    def build_if(condition, body=[]):
        ret = ""
        left, ops, comparators = condition

        if (ops == "Eq"):
            ops = "=="

        ret += "if( " + left + " " + ops + " " + str(comparators) + " ){\n"
        for i in body:
            ret += str(i)
            ret += "\n"

        ret += "}\n"

        return ret
