class Main:
    # 由于Python没有全局变量的定义，因此利用对变量名规范从而定义全局
    __str = ""
    __integer = 0
    __a = None

    def foo(self) -> int:
        self.__str = "global"
        self.__integer = 10
        ret = 1 + 2 - 3 * 5
        if self.__integer == 0:
            ret = 5
        return ret

    def bar(self, _a):
        _a = _a + 3



