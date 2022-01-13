package com.tk.main;

import com.tk.antlr.SolidityLexer;
import com.tk.antlr.SolidityParser;
import com.tk.main.analyze.WeakListener;
import lombok.extern.log4j.Log4j;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author HDU_WS
 * @Classname Main
 * @Description TODO
 * @Date 2021/8/17 16:47
 * @Created by TheKing_Shun
 */

@Log4j
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        // BasicConfigurator.configure();

        String filePath = args[0];
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("文件不存在");
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("当前文件路径为：" + filePath);
        try {
            CharStream fileStream = CharStreams.fromFileName(filePath);
            SolidityLexer lexer = new SolidityLexer(fileStream);
            SolidityParser parser = new SolidityParser(new CommonTokenStream(lexer));
            SolidityParser.SourceUnitContext tree = parser.sourceUnit();

            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(new WeakListener(),tree);
            // WeakVisitor visitor = new WeakVisitor();
            // visitor.visit(tree);
            // printAST(tree,false,0);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private static void printAST(RuleContext ctx, boolean verbose, int indentation) {
        boolean toBeIgnored = !verbose && ctx.getChildCount() == 1 && ctx.getChild(0) instanceof ParserRuleContext;

        if (!toBeIgnored) {
            String ruleName = SolidityParser.ruleNames[ctx.getRuleIndex()];
            for (int i = 0; i < indentation; i++) {
                System.out.print("  ");
            }
            System.out.println(ruleName + " -> " + ctx.getText());
        }
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                printAST((RuleContext) element, verbose, indentation + (toBeIgnored ? 0 : 1));
            }
        }
    }

}
