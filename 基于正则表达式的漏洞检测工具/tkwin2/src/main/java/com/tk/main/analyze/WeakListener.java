package com.tk.main.analyze;

import com.tk.antlr.SolidityBaseListener;
import com.tk.antlr.SolidityParser;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;


/**
 * @author HDU_WS
 * @Classname WeakListener
 * @Description TODO
 * @Date 2021/8/17 16:49
 * @Created by TheKing_Shun
 */

@Log4j
public class WeakListener extends SolidityBaseListener {

    private Analysis ana;

    public WeakListener() {
        ana = new Analysis();
    }

    @Override
    public void enterSourceUnit(SolidityParser.SourceUnitContext ctx) {
        super.enterSourceUnit(ctx);
    }

    @Override
    public void exitSourceUnit(SolidityParser.SourceUnitContext ctx) {
        super.exitSourceUnit(ctx);
    }

    @Override
    public void enterPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) {
        super.enterPragmaDirective(ctx);
    }

    @Override
    public void exitPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) {
        super.exitPragmaDirective(ctx);
    }

    @Override
    public void enterPragmaName(SolidityParser.PragmaNameContext ctx) {
        super.enterPragmaName(ctx);
    }

    @Override
    public void exitPragmaName(SolidityParser.PragmaNameContext ctx) {
        super.exitPragmaName(ctx);
    }

    @Override
    public void enterPragmaValue(SolidityParser.PragmaValueContext ctx) {
        super.enterPragmaValue(ctx);
    }

    @Override
    public void exitPragmaValue(SolidityParser.PragmaValueContext ctx) {
        super.exitPragmaValue(ctx);
    }

    @Override
    public void enterVersion(SolidityParser.VersionContext ctx) {
        super.enterVersion(ctx);
    }

    @Override
    public void exitVersion(SolidityParser.VersionContext ctx) {
        super.exitVersion(ctx);
    }

    @Override
    public void enterVersionConstraint(SolidityParser.VersionConstraintContext ctx) {
        SolidityParser.VersionOperatorContext versionOP = ctx.versionOperator();
        String version = ctx.VersionLiteral().getText();
        String op = "";
        if (versionOP != null) {
            op = versionOP.getText();
        }
        if (version == null) {
            version = "";
        }
        log.info(String.format("current version is %s%s",op,version));
        // log.info(ctx.versionOperator());
        super.enterVersionConstraint(ctx);
    }

    @Override
    public void exitVersionConstraint(SolidityParser.VersionConstraintContext ctx) {
        super.exitVersionConstraint(ctx);
    }

    @Override
    public void enterVersionOperator(SolidityParser.VersionOperatorContext ctx) {
        super.enterVersionOperator(ctx);
    }

    @Override
    public void exitVersionOperator(SolidityParser.VersionOperatorContext ctx) {
        super.exitVersionOperator(ctx);
    }

    @Override
    public void enterImportDirective(SolidityParser.ImportDirectiveContext ctx) {
        super.enterImportDirective(ctx);
    }

    @Override
    public void exitImportDirective(SolidityParser.ImportDirectiveContext ctx) {
        super.exitImportDirective(ctx);
    }

    @Override
    public void enterImportDeclaration(SolidityParser.ImportDeclarationContext ctx) {
        super.enterImportDeclaration(ctx);
    }

    @Override
    public void exitImportDeclaration(SolidityParser.ImportDeclarationContext ctx) {
        super.exitImportDeclaration(ctx);
    }

    @Override
    public void enterContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        super.enterContractDefinition(ctx);
    }

    @Override
    public void exitContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        super.exitContractDefinition(ctx);
    }

    @Override
    public void enterInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) {
        super.enterInheritanceSpecifier(ctx);
    }

    @Override
    public void exitInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) {
        super.exitInheritanceSpecifier(ctx);
    }

    @Override
    public void enterContractPart(SolidityParser.ContractPartContext ctx) {
        super.enterContractPart(ctx);
    }

    @Override
    public void exitContractPart(SolidityParser.ContractPartContext ctx) {
        super.exitContractPart(ctx);
    }

    @Override
    public void enterStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) {
        if (ctx.PrivateKeyword().size()==0
                && ctx.PublicKeyword().size()==0
                && ctx.InternalKeyword().size()==0
                && ctx.ConstantKeyword().size()==0
                && ctx.ImmutableKeyword().size()==0
        ) {
            log.warn("state variable modifier is default! -> line is "+ctx.start.getLine());
        }
        super.enterStateVariableDeclaration(ctx);
    }

    @Override
    public void exitStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) {
        super.exitStateVariableDeclaration(ctx);
    }

    @Override
    public void enterOverrideSpecifier(SolidityParser.OverrideSpecifierContext ctx) {
        super.enterOverrideSpecifier(ctx);
    }

    @Override
    public void exitOverrideSpecifier(SolidityParser.OverrideSpecifierContext ctx) {
        super.exitOverrideSpecifier(ctx);
    }

    @Override
    public void enterUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) {
        super.enterUsingForDeclaration(ctx);
    }

    @Override
    public void exitUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) {
        super.exitUsingForDeclaration(ctx);
    }

    @Override
    public void enterStructDefinition(SolidityParser.StructDefinitionContext ctx) {
        super.enterStructDefinition(ctx);
    }

    @Override
    public void exitStructDefinition(SolidityParser.StructDefinitionContext ctx) {
        super.exitStructDefinition(ctx);
    }

    @Override
    public void enterModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) {
        super.enterModifierDefinition(ctx);
    }

    @Override
    public void exitModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) {
        super.exitModifierDefinition(ctx);
    }

    @Override
    public void enterFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        SolidityParser.BlockContext block = ctx.block();
        if (block != null) {
            String modifier = ctx.modifierList().getText();
            boolean isWeak = ana.analysisModifier(modifier);
            if (isWeak) {
                log.warn(String.format("modifier is wrong , u should given a modifier like \"public\" -> line is %d ",ctx.start.getLine()));
            }
        }
        super.enterFunctionDefinition(ctx);
    }

    @Override
    public void exitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        super.exitFunctionDefinition(ctx);
    }

    @Override
    public void enterFunctionDescriptor(SolidityParser.FunctionDescriptorContext ctx) {
        super.enterFunctionDescriptor(ctx);
    }

    @Override
    public void exitFunctionDescriptor(SolidityParser.FunctionDescriptorContext ctx) {
        super.exitFunctionDescriptor(ctx);
    }

    @Override
    public void enterReturnParameters(SolidityParser.ReturnParametersContext ctx) {
        super.enterReturnParameters(ctx);
    }

    @Override
    public void exitReturnParameters(SolidityParser.ReturnParametersContext ctx) {
        super.exitReturnParameters(ctx);
    }

    @Override
    public void enterModifierList(SolidityParser.ModifierListContext ctx) {
        super.enterModifierList(ctx);
    }

    @Override
    public void exitModifierList(SolidityParser.ModifierListContext ctx) {
        super.exitModifierList(ctx);
    }

    @Override
    public void enterModifierInvocation(SolidityParser.ModifierInvocationContext ctx) {
        super.enterModifierInvocation(ctx);
    }

    @Override
    public void exitModifierInvocation(SolidityParser.ModifierInvocationContext ctx) {
        super.exitModifierInvocation(ctx);
    }

    @Override
    public void enterEventDefinition(SolidityParser.EventDefinitionContext ctx) {
        super.enterEventDefinition(ctx);
    }

    @Override
    public void exitEventDefinition(SolidityParser.EventDefinitionContext ctx) {
        super.exitEventDefinition(ctx);
    }

    @Override
    public void enterEnumDefinition(SolidityParser.EnumDefinitionContext ctx) {
        super.enterEnumDefinition(ctx);
    }

    @Override
    public void exitEnumDefinition(SolidityParser.EnumDefinitionContext ctx) {
        super.exitEnumDefinition(ctx);
    }

    @Override
    public void enterEnumValue(SolidityParser.EnumValueContext ctx) {
        super.enterEnumValue(ctx);
    }

    @Override
    public void exitEnumValue(SolidityParser.EnumValueContext ctx) {
        super.exitEnumValue(ctx);
    }

    @Override
    public void enterParameterList(SolidityParser.ParameterListContext ctx) {
        super.enterParameterList(ctx);
    }

    @Override
    public void exitParameterList(SolidityParser.ParameterListContext ctx) {
        super.exitParameterList(ctx);
    }

    @Override
    public void enterParameter(SolidityParser.ParameterContext ctx) {
        super.enterParameter(ctx);
    }

    @Override
    public void exitParameter(SolidityParser.ParameterContext ctx) {
        super.exitParameter(ctx);
    }

    @Override
    public void enterEventParameterList(SolidityParser.EventParameterListContext ctx) {
        super.enterEventParameterList(ctx);
    }

    @Override
    public void exitEventParameterList(SolidityParser.EventParameterListContext ctx) {
        super.exitEventParameterList(ctx);
    }

    @Override
    public void enterEventParameter(SolidityParser.EventParameterContext ctx) {
        super.enterEventParameter(ctx);
    }

    @Override
    public void exitEventParameter(SolidityParser.EventParameterContext ctx) {
        super.exitEventParameter(ctx);
    }

    @Override
    public void enterVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) {
        super.enterVariableDeclaration(ctx);
    }

    @Override
    public void exitVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) {
        super.exitVariableDeclaration(ctx);
    }

    @Override
    public void enterTypeName(SolidityParser.TypeNameContext ctx) {
        super.enterTypeName(ctx);
    }

    @Override
    public void exitTypeName(SolidityParser.TypeNameContext ctx) {
        super.exitTypeName(ctx);
    }

    @Override
    public void enterUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) {
        super.enterUserDefinedTypeName(ctx);
    }

    @Override
    public void exitUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) {
        super.exitUserDefinedTypeName(ctx);
    }

    @Override
    public void enterMapping(SolidityParser.MappingContext ctx) {
        super.enterMapping(ctx);
    }

    @Override
    public void exitMapping(SolidityParser.MappingContext ctx) {
        super.exitMapping(ctx);
    }

    @Override
    public void enterFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) {
        super.enterFunctionTypeName(ctx);
    }

    @Override
    public void exitFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) {
        super.exitFunctionTypeName(ctx);
    }

    @Override
    public void enterStorageLocation(SolidityParser.StorageLocationContext ctx) {
        super.enterStorageLocation(ctx);
    }

    @Override
    public void exitStorageLocation(SolidityParser.StorageLocationContext ctx) {
        super.exitStorageLocation(ctx);
    }

    @Override
    public void enterStateMutability(SolidityParser.StateMutabilityContext ctx) {
        super.enterStateMutability(ctx);
    }

    @Override
    public void exitStateMutability(SolidityParser.StateMutabilityContext ctx) {
        super.exitStateMutability(ctx);
    }

    @Override
    public void enterBlock(SolidityParser.BlockContext ctx) {
        super.enterBlock(ctx);
    }

    @Override
    public void exitBlock(SolidityParser.BlockContext ctx) {
        super.exitBlock(ctx);
    }

    @Override
    public void enterStatement(SolidityParser.StatementContext ctx) {
        super.enterStatement(ctx);
    }

    @Override
    public void exitStatement(SolidityParser.StatementContext ctx) {
        super.exitStatement(ctx);
    }

    @Override
    public void enterExpressionStatement(SolidityParser.ExpressionStatementContext ctx) {

        String expr = ctx.expression().getText();
        if (ana.analysisUncheck(expr)) {
            log.error(String.format("Uncheck return value : %s  -> line is %d",expr,ctx.start.getLine()));
        }
        if (ana.analysisTimestampDep(expr)) {
            log.error(String.format("timestamp dependency : %s  -> line is %d",expr,ctx.start.getLine()));
        }
        if (ana.anlysisBlockNumber(expr)) {
            log.error(String.format("BlockNumber dependency : %s  -> line is %d",expr,ctx.start.getLine()));
        }
        super.enterExpressionStatement(ctx);
    }

    @Override
    public void exitExpressionStatement(SolidityParser.ExpressionStatementContext ctx) {
        super.exitExpressionStatement(ctx);
    }

    @Override
    public void enterIfStatement(SolidityParser.IfStatementContext ctx) {
        super.enterIfStatement(ctx);
    }

    @Override
    public void exitIfStatement(SolidityParser.IfStatementContext ctx) {
        super.exitIfStatement(ctx);
    }

    @Override
    public void enterTryStatement(SolidityParser.TryStatementContext ctx) {
        super.enterTryStatement(ctx);
    }

    @Override
    public void exitTryStatement(SolidityParser.TryStatementContext ctx) {
        super.exitTryStatement(ctx);
    }

    @Override
    public void enterCatchClause(SolidityParser.CatchClauseContext ctx) {
        super.enterCatchClause(ctx);
    }

    @Override
    public void exitCatchClause(SolidityParser.CatchClauseContext ctx) {
        super.exitCatchClause(ctx);
    }

    @Override
    public void enterWhileStatement(SolidityParser.WhileStatementContext ctx) {
        super.enterWhileStatement(ctx);
    }

    @Override
    public void exitWhileStatement(SolidityParser.WhileStatementContext ctx) {
        super.exitWhileStatement(ctx);
    }

    @Override
    public void enterForStatement(SolidityParser.ForStatementContext ctx) {
        super.enterForStatement(ctx);
    }

    @Override
    public void exitForStatement(SolidityParser.ForStatementContext ctx) {
        super.exitForStatement(ctx);
    }

    @Override
    public void enterSimpleStatement(SolidityParser.SimpleStatementContext ctx) {
        super.enterSimpleStatement(ctx);
    }

    @Override
    public void exitSimpleStatement(SolidityParser.SimpleStatementContext ctx) {
        super.exitSimpleStatement(ctx);
    }

    @Override
    public void enterInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) {
        super.enterInlineAssemblyStatement(ctx);
    }

    @Override
    public void exitInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) {
        super.exitInlineAssemblyStatement(ctx);
    }

    @Override
    public void enterDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) {
        super.enterDoWhileStatement(ctx);
    }

    @Override
    public void exitDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) {
        super.exitDoWhileStatement(ctx);
    }

    @Override
    public void enterContinueStatement(SolidityParser.ContinueStatementContext ctx) {
        super.enterContinueStatement(ctx);
    }

    @Override
    public void exitContinueStatement(SolidityParser.ContinueStatementContext ctx) {
        super.exitContinueStatement(ctx);
    }

    @Override
    public void enterBreakStatement(SolidityParser.BreakStatementContext ctx) {
        super.enterBreakStatement(ctx);
    }

    @Override
    public void exitBreakStatement(SolidityParser.BreakStatementContext ctx) {
        super.exitBreakStatement(ctx);
    }

    @Override
    public void enterReturnStatement(SolidityParser.ReturnStatementContext ctx) {
        super.enterReturnStatement(ctx);
    }

    @Override
    public void exitReturnStatement(SolidityParser.ReturnStatementContext ctx) {
        super.exitReturnStatement(ctx);
    }

    @Override
    public void enterThrowStatement(SolidityParser.ThrowStatementContext ctx) {
        super.enterThrowStatement(ctx);
    }

    @Override
    public void exitThrowStatement(SolidityParser.ThrowStatementContext ctx) {
        super.exitThrowStatement(ctx);
    }

    @Override
    public void enterEmitStatement(SolidityParser.EmitStatementContext ctx) {
        super.enterEmitStatement(ctx);
    }

    @Override
    public void exitEmitStatement(SolidityParser.EmitStatementContext ctx) {
        super.exitEmitStatement(ctx);
    }

    @Override
    public void enterVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) {
        super.enterVariableDeclarationStatement(ctx);
    }

    @Override
    public void exitVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) {
        super.exitVariableDeclarationStatement(ctx);
    }

    @Override
    public void enterVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) {
        super.enterVariableDeclarationList(ctx);
    }

    @Override
    public void exitVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) {
        super.exitVariableDeclarationList(ctx);
    }

    @Override
    public void enterIdentifierList(SolidityParser.IdentifierListContext ctx) {
        super.enterIdentifierList(ctx);
    }

    @Override
    public void exitIdentifierList(SolidityParser.IdentifierListContext ctx) {
        super.exitIdentifierList(ctx);
    }

    @Override
    public void enterElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {
        super.enterElementaryTypeName(ctx);
    }

    @Override
    public void exitElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {
        super.exitElementaryTypeName(ctx);
    }

    @Override
    public void enterExpression(SolidityParser.ExpressionContext ctx) {
        super.enterExpression(ctx);
    }

    @Override
    public void exitExpression(SolidityParser.ExpressionContext ctx) {
        super.exitExpression(ctx);
    }

    @Override
    public void enterPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) {
        super.enterPrimaryExpression(ctx);
    }

    @Override
    public void exitPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) {
        super.exitPrimaryExpression(ctx);
    }

    @Override
    public void enterExpressionList(SolidityParser.ExpressionListContext ctx) {
        super.enterExpressionList(ctx);
    }

    @Override
    public void exitExpressionList(SolidityParser.ExpressionListContext ctx) {
        super.exitExpressionList(ctx);
    }

    @Override
    public void enterNameValueList(SolidityParser.NameValueListContext ctx) {
        super.enterNameValueList(ctx);
    }

    @Override
    public void exitNameValueList(SolidityParser.NameValueListContext ctx) {
        super.exitNameValueList(ctx);
    }

    @Override
    public void enterNameValue(SolidityParser.NameValueContext ctx) {
        super.enterNameValue(ctx);
    }

    @Override
    public void exitNameValue(SolidityParser.NameValueContext ctx) {
        super.exitNameValue(ctx);
    }

    @Override
    public void enterFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) {
        super.enterFunctionCallArguments(ctx);
    }

    @Override
    public void exitFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) {
        super.exitFunctionCallArguments(ctx);
    }

    @Override
    public void enterFunctionCall(SolidityParser.FunctionCallContext ctx) {
        super.enterFunctionCall(ctx);
    }

    @Override
    public void exitFunctionCall(SolidityParser.FunctionCallContext ctx) {
        super.exitFunctionCall(ctx);
    }

    @Override
    public void enterTupleExpression(SolidityParser.TupleExpressionContext ctx) {
        super.enterTupleExpression(ctx);
    }

    @Override
    public void exitTupleExpression(SolidityParser.TupleExpressionContext ctx) {
        super.exitTupleExpression(ctx);
    }

    @Override
    public void enterTypeNameExpression(SolidityParser.TypeNameExpressionContext ctx) {
        super.enterTypeNameExpression(ctx);
    }

    @Override
    public void exitTypeNameExpression(SolidityParser.TypeNameExpressionContext ctx) {
        super.exitTypeNameExpression(ctx);
    }

    @Override
    public void enterAssemblyItem(SolidityParser.AssemblyItemContext ctx) {
        super.enterAssemblyItem(ctx);
    }

    @Override
    public void exitAssemblyItem(SolidityParser.AssemblyItemContext ctx) {
        super.exitAssemblyItem(ctx);
    }

    @Override
    public void enterAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) {
        super.enterAssemblyBlock(ctx);
    }

    @Override
    public void exitAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) {
        super.exitAssemblyBlock(ctx);
    }

    @Override
    public void enterAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) {
        super.enterAssemblyExpression(ctx);
    }

    @Override
    public void exitAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) {
        super.exitAssemblyExpression(ctx);
    }

    @Override
    public void enterAssemblyCall(SolidityParser.AssemblyCallContext ctx) {
        super.enterAssemblyCall(ctx);
    }

    @Override
    public void exitAssemblyCall(SolidityParser.AssemblyCallContext ctx) {
        super.exitAssemblyCall(ctx);
    }

    @Override
    public void enterAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) {
        super.enterAssemblyLocalDefinition(ctx);
    }

    @Override
    public void exitAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) {
        super.exitAssemblyLocalDefinition(ctx);
    }

    @Override
    public void enterAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) {
        super.enterAssemblyAssignment(ctx);
    }

    @Override
    public void exitAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) {
        super.exitAssemblyAssignment(ctx);
    }

    @Override
    public void enterAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) {
        super.enterAssemblyIdentifierList(ctx);
    }

    @Override
    public void exitAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) {
        super.exitAssemblyIdentifierList(ctx);
    }

    @Override
    public void enterAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) {
        super.enterAssemblyStackAssignment(ctx);
    }

    @Override
    public void exitAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) {
        super.exitAssemblyStackAssignment(ctx);
    }

    @Override
    public void enterLabelDefinition(SolidityParser.LabelDefinitionContext ctx) {
        super.enterLabelDefinition(ctx);
    }

    @Override
    public void exitLabelDefinition(SolidityParser.LabelDefinitionContext ctx) {
        super.exitLabelDefinition(ctx);
    }

    @Override
    public void enterAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) {
        super.enterAssemblySwitch(ctx);
    }

    @Override
    public void exitAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) {
        super.exitAssemblySwitch(ctx);
    }

    @Override
    public void enterAssemblyCase(SolidityParser.AssemblyCaseContext ctx) {
        super.enterAssemblyCase(ctx);
    }

    @Override
    public void exitAssemblyCase(SolidityParser.AssemblyCaseContext ctx) {
        super.exitAssemblyCase(ctx);
    }

    @Override
    public void enterAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) {
        super.enterAssemblyFunctionDefinition(ctx);
    }

    @Override
    public void exitAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) {
        super.exitAssemblyFunctionDefinition(ctx);
    }

    @Override
    public void enterAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) {
        super.enterAssemblyFunctionReturns(ctx);
    }

    @Override
    public void exitAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) {
        super.exitAssemblyFunctionReturns(ctx);
    }

    @Override
    public void enterAssemblyFor(SolidityParser.AssemblyForContext ctx) {
        super.enterAssemblyFor(ctx);
    }

    @Override
    public void exitAssemblyFor(SolidityParser.AssemblyForContext ctx) {
        super.exitAssemblyFor(ctx);
    }

    @Override
    public void enterAssemblyIf(SolidityParser.AssemblyIfContext ctx) {
        super.enterAssemblyIf(ctx);
    }

    @Override
    public void exitAssemblyIf(SolidityParser.AssemblyIfContext ctx) {
        super.exitAssemblyIf(ctx);
    }

    @Override
    public void enterAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) {
        super.enterAssemblyLiteral(ctx);
    }

    @Override
    public void exitAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) {
        super.exitAssemblyLiteral(ctx);
    }

    @Override
    public void enterAssemblyTypedVariableList(SolidityParser.AssemblyTypedVariableListContext ctx) {
        super.enterAssemblyTypedVariableList(ctx);
    }

    @Override
    public void exitAssemblyTypedVariableList(SolidityParser.AssemblyTypedVariableListContext ctx) {
        super.exitAssemblyTypedVariableList(ctx);
    }

    @Override
    public void enterAssemblyType(SolidityParser.AssemblyTypeContext ctx) {
        super.enterAssemblyType(ctx);
    }

    @Override
    public void exitAssemblyType(SolidityParser.AssemblyTypeContext ctx) {
        super.exitAssemblyType(ctx);
    }

    @Override
    public void enterSubAssembly(SolidityParser.SubAssemblyContext ctx) {
        super.enterSubAssembly(ctx);
    }

    @Override
    public void exitSubAssembly(SolidityParser.SubAssemblyContext ctx) {
        super.exitSubAssembly(ctx);
    }

    @Override
    public void enterNumberLiteral(SolidityParser.NumberLiteralContext ctx) {
        super.enterNumberLiteral(ctx);
    }

    @Override
    public void exitNumberLiteral(SolidityParser.NumberLiteralContext ctx) {
        super.exitNumberLiteral(ctx);
    }

    @Override
    public void enterIdentifier(SolidityParser.IdentifierContext ctx) {
        super.enterIdentifier(ctx);
    }

    @Override
    public void exitIdentifier(SolidityParser.IdentifierContext ctx) {
        super.exitIdentifier(ctx);
    }

    @Override
    public void enterHexLiteral(SolidityParser.HexLiteralContext ctx) {
        super.enterHexLiteral(ctx);
    }

    @Override
    public void exitHexLiteral(SolidityParser.HexLiteralContext ctx) {
        super.exitHexLiteral(ctx);
    }

    @Override
    public void enterStringLiteral(SolidityParser.StringLiteralContext ctx) {
        super.enterStringLiteral(ctx);
    }

    @Override
    public void exitStringLiteral(SolidityParser.StringLiteralContext ctx) {
        super.exitStringLiteral(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
