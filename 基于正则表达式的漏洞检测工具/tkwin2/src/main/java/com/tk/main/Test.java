package com.tk.main;

import java.util.regex.Pattern;

/**
 * @author HDU_WS
 * @Classname Test
 * @Description TODO
 * @Date 2021/8/17 17:24
 * @Created by TheKing_Shun
 */

public class Test {
    public static void main(String[] args) {
        String patter = "[a-zA-Z0-9]+\\.call.*";
        // String patter = "[a-z]+";
        String expression = "require(callee.call());";
        System.out.println(Pattern.matches(patter, expression));


        String content = "I am noob " +
                "from runoob.com.";

        String pattern = ".*runoob.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println(isMatch);
    }
}
