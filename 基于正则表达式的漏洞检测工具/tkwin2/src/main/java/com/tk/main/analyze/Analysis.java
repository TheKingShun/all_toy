package com.tk.main.analyze;

import lombok.extern.log4j.Log4j;

import java.util.regex.Pattern;

/**
 * @author HDU_WS
 * @Classname Analysis
 * @Description TODO
 * @Date 2021/8/18 14:46
 * @Created by TheKing_Shun
 */

@Log4j
public class Analysis {

    private static final String[] modifiers = new String[]{"public","private","internal","external"};


    public Analysis() {
    }

    /**
     * 选择对修饰符进行分析，若使用默认修饰符则返回true
     * @param expression
     * @return 有问题：true
     *          没问题：false
     */
    public boolean analysisModifier(String expression) {
        for (String modifier : modifiers) {
            if (expression.contains(modifier)) {
                return false;
            }
        }
        return true;
    }

    public boolean analysisUncheck(String expr) {

        String patter = "\\(*[a-zA-Z0-9]+\\.(call|send).*";
        if (Pattern.matches(patter, expr)) {
            if(expr.startsWith("require") || expr.startsWith("if") || expr.startsWith("while")){
                return false;
            }
            return true;
        }
        return false;
    }


    public boolean analysisTimestampDep(String expr) {
        return expr.contains("block.timestamp");
    }


    public boolean anlysisBlockNumber(String expr) {
        return expr.contains("block.number");
    }
}
