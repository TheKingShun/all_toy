class FunctionDef:
    name = ""

    returns = None
    params=[]
    block = []
    variable = []


class Assign:
    """
    id 代表的是赋值语句的变量名
    """
    ast_type = "Assign"
    col_offset=-1
    end_col_offset=-1
    lineno = -1
    type_comment = -1
    targets = []
    name = ""
    targets_ctx_type = ""
    targets_ast_type = ""
    value = None

class BinOp:
    """
    形如： 9 +<- * /> 5
    """
    ast_type = 'BinOp'
    left = None
    right = None
    op_type = ""


class Constant:
    """
    常量，没什么好说的
    """
    # 无论是数字还是字符串 ，在现阶段还是选择字符串简单一点
    # todo
    value = ""
    kind = ""

class Return:
    """
    返回值类型
    """
    value = None

class Name:
    """
    不知道这是一个什么节点 真实无语 ！！！！！
    """
    ctx_ast_type = ""
    name = ""


class Arg:
    ast_type = "arg"
    arg = ""

class If:
    ast_type = "If"
    body = []
    condition = None











