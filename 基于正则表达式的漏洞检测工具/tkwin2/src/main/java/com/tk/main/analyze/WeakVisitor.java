package com.tk.main.analyze;

import com.tk.antlr.SolidityBaseListener;
import com.tk.antlr.SolidityBaseVisitor;
import com.tk.antlr.SolidityParser;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;

/**
 * @author HDU_WS
 * @Classname WeakVisitor
 * @Description TODO
 * @Date 2021/8/17 19:00
 * @Created by TheKing_Shun
 */
@Log4j
public class WeakVisitor extends SolidityBaseVisitor<SolValue> {

    private static final String[] modifier = new String[]{"external", "internal", "public", "private"};
    private static final SolValue NULL =  SolValue.NULL;
    private static final SolValue VOID =  SolValue.VOID;

    public WeakVisitor() {

    }

    @Override
    public SolValue visitSourceUnit(SolidityParser.SourceUnitContext ctx) {
        log.info("visitSourceUnit");
        return super.visitSourceUnit(ctx);
    }

    @Override
    public SolValue visitPragmaDirective(SolidityParser.PragmaDirectiveContext ctx) {
        return super.visitPragmaDirective(ctx);
    }

    @Override
    public SolValue visitPragmaName(SolidityParser.PragmaNameContext ctx) {
        return super.visitPragmaName(ctx);
    }

    @Override
    public SolValue visitPragmaValue(SolidityParser.PragmaValueContext ctx) {
        return super.visitPragmaValue(ctx);
    }

    @Override
    public SolValue visitVersion(SolidityParser.VersionContext ctx) {
        return super.visitVersion(ctx);
    }

    @Override
    public SolValue visitVersionConstraint(SolidityParser.VersionConstraintContext ctx) {
        return super.visitVersionConstraint(ctx);
    }

    @Override
    public SolValue visitVersionOperator(SolidityParser.VersionOperatorContext ctx) {
        return super.visitVersionOperator(ctx);
    }

    @Override
    public SolValue visitImportDirective(SolidityParser.ImportDirectiveContext ctx) {
        return super.visitImportDirective(ctx);
    }

    @Override
    public SolValue visitImportDeclaration(SolidityParser.ImportDeclarationContext ctx) {
        return super.visitImportDeclaration(ctx);
    }

    @Override
    public SolValue visitContractDefinition(SolidityParser.ContractDefinitionContext ctx) {
        return super.visitContractDefinition(ctx);
    }

    @Override
    public SolValue visitInheritanceSpecifier(SolidityParser.InheritanceSpecifierContext ctx) {
        return super.visitInheritanceSpecifier(ctx);
    }

    @Override
    public SolValue visitContractPart(SolidityParser.ContractPartContext ctx) {
        return super.visitContractPart(ctx);
    }

    @Override
    public SolValue visitStateVariableDeclaration(SolidityParser.StateVariableDeclarationContext ctx) {
        return super.visitStateVariableDeclaration(ctx);
    }

    @Override
    public SolValue visitOverrideSpecifier(SolidityParser.OverrideSpecifierContext ctx) {
        return super.visitOverrideSpecifier(ctx);
    }

    @Override
    public SolValue visitUsingForDeclaration(SolidityParser.UsingForDeclarationContext ctx) {
        return super.visitUsingForDeclaration(ctx);
    }

    @Override
    public SolValue visitStructDefinition(SolidityParser.StructDefinitionContext ctx) {
        return super.visitStructDefinition(ctx);
    }

    @Override
    public SolValue visitModifierDefinition(SolidityParser.ModifierDefinitionContext ctx) {
        return super.visitModifierDefinition(ctx);
    }

    // functionDefinition
    //   : functionDescriptor parameterList modifierList returnParameters? ( ';' | block ) ;
    @Override
    public SolValue visitFunctionDefinition(SolidityParser.FunctionDefinitionContext ctx) {
        SolidityParser.BlockContext block = ctx.block();
        if (block != null) {
            log.info("block is not null");
            SolValue modifierList = this.visit(ctx.modifierList());
            // log.info(modifierList);
            if (modifierList == null || modifierList.getSize() == 0) {
                log.warn("modifier is not given line:"+ctx.start.getLine());
                return SolValue.NULL;
            } else {
                StringBuilder sb = new StringBuilder();
                for (SolValue value : modifierList.asLIst()) {
                    sb.append(value.asString());
                }
                String modifiers = sb.toString();
                for (String mod : modifier) {
                    if (modifiers.contains(mod)) {
                        return SolValue.NULL;
                    }
                }
                log.error("modifier is not clear");
                log.error("line: "+ctx.start.getLine());
            }
        }
        return super.visitFunctionDefinition(ctx);
    }

    @Override
    public SolValue visitFunctionDescriptor(SolidityParser.FunctionDescriptorContext ctx) {
        return super.visitFunctionDescriptor(ctx);
    }

    @Override
    public SolValue visitReturnParameters(SolidityParser.ReturnParametersContext ctx) {
        return super.visitReturnParameters(ctx);
    }

    // modifierList
    //   : ( modifierInvocation | stateMutability | ExternalKeyword
    //     | PublicKeyword | InternalKeyword | PrivateKeyword | VirtualKeyword | overrideSpecifier )* ;
    @Override
    public SolValue visitModifierList(SolidityParser.ModifierListContext ctx) {
        ArrayList<SolValue> list = new ArrayList<>();
        if (ctx.ExternalKeyword().size() != 0 ) {
            log.info("external");
            list.add(new SolValue("external"));
        }
        if (ctx.PublicKeyword().size() != 0) {
            list.add(new SolValue("public"));
        }
        if (ctx.InternalKeyword().size() != 0) {
            list.add(new SolValue("internal"));
        }
        if (ctx.PrivateKeyword().size() != 0) {
            list.add(new SolValue("private"));
        }
        return new SolValue(list);
    }

    @Override
    public SolValue visitModifierInvocation(SolidityParser.ModifierInvocationContext ctx) {
        return super.visitModifierInvocation(ctx);
    }

    @Override
    public SolValue visitEventDefinition(SolidityParser.EventDefinitionContext ctx) {
        return super.visitEventDefinition(ctx);
    }

    @Override
    public SolValue visitEnumDefinition(SolidityParser.EnumDefinitionContext ctx) {
        return super.visitEnumDefinition(ctx);
    }

    @Override
    public SolValue visitEnumValue(SolidityParser.EnumValueContext ctx) {
        return super.visitEnumValue(ctx);
    }

    @Override
    public SolValue visitParameterList(SolidityParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }

    @Override
    public SolValue visitParameter(SolidityParser.ParameterContext ctx) {
        return super.visitParameter(ctx);
    }

    @Override
    public SolValue visitEventParameterList(SolidityParser.EventParameterListContext ctx) {
        return super.visitEventParameterList(ctx);
    }

    @Override
    public SolValue visitEventParameter(SolidityParser.EventParameterContext ctx) {
        return super.visitEventParameter(ctx);
    }

    @Override
    public SolValue visitVariableDeclaration(SolidityParser.VariableDeclarationContext ctx) {
        return super.visitVariableDeclaration(ctx);
    }

    @Override
    public SolValue visitTypeName(SolidityParser.TypeNameContext ctx) {
        return super.visitTypeName(ctx);
    }

    @Override
    public SolValue visitUserDefinedTypeName(SolidityParser.UserDefinedTypeNameContext ctx) {
        return super.visitUserDefinedTypeName(ctx);
    }

    @Override
    public SolValue visitMapping(SolidityParser.MappingContext ctx) {
        return super.visitMapping(ctx);
    }

    @Override
    public SolValue visitFunctionTypeName(SolidityParser.FunctionTypeNameContext ctx) {
        return super.visitFunctionTypeName(ctx);
    }

    @Override
    public SolValue visitStorageLocation(SolidityParser.StorageLocationContext ctx) {
        return super.visitStorageLocation(ctx);
    }

    @Override
    public SolValue visitStateMutability(SolidityParser.StateMutabilityContext ctx) {
        return super.visitStateMutability(ctx);
    }

    @Override
    public SolValue visitBlock(SolidityParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public SolValue visitStatement(SolidityParser.StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public SolValue visitExpressionStatement(SolidityParser.ExpressionStatementContext ctx) {
        log.info(ctx.getText());
        return super.visitExpressionStatement(ctx);
    }

    @Override
    public SolValue visitIfStatement(SolidityParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public SolValue visitTryStatement(SolidityParser.TryStatementContext ctx) {
        return super.visitTryStatement(ctx);
    }

    @Override
    public SolValue visitCatchClause(SolidityParser.CatchClauseContext ctx) {
        return super.visitCatchClause(ctx);
    }

    @Override
    public SolValue visitWhileStatement(SolidityParser.WhileStatementContext ctx) {
        return super.visitWhileStatement(ctx);
    }

    @Override
    public SolValue visitForStatement(SolidityParser.ForStatementContext ctx) {
        return super.visitForStatement(ctx);
    }

    @Override
    public SolValue visitSimpleStatement(SolidityParser.SimpleStatementContext ctx) {
        return super.visitSimpleStatement(ctx);
    }

    @Override
    public SolValue visitInlineAssemblyStatement(SolidityParser.InlineAssemblyStatementContext ctx) {
        return super.visitInlineAssemblyStatement(ctx);
    }

    @Override
    public SolValue visitDoWhileStatement(SolidityParser.DoWhileStatementContext ctx) {
        return super.visitDoWhileStatement(ctx);
    }

    @Override
    public SolValue visitContinueStatement(SolidityParser.ContinueStatementContext ctx) {
        return super.visitContinueStatement(ctx);
    }

    @Override
    public SolValue visitBreakStatement(SolidityParser.BreakStatementContext ctx) {
        return super.visitBreakStatement(ctx);
    }

    @Override
    public SolValue visitReturnStatement(SolidityParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

    @Override
    public SolValue visitThrowStatement(SolidityParser.ThrowStatementContext ctx) {
        return super.visitThrowStatement(ctx);
    }

    @Override
    public SolValue visitEmitStatement(SolidityParser.EmitStatementContext ctx) {
        return super.visitEmitStatement(ctx);
    }

    @Override
    public SolValue visitVariableDeclarationStatement(SolidityParser.VariableDeclarationStatementContext ctx) {
        return super.visitVariableDeclarationStatement(ctx);
    }

    @Override
    public SolValue visitVariableDeclarationList(SolidityParser.VariableDeclarationListContext ctx) {
        return super.visitVariableDeclarationList(ctx);
    }

    @Override
    public SolValue visitIdentifierList(SolidityParser.IdentifierListContext ctx) {
        return super.visitIdentifierList(ctx);
    }

    @Override
    public SolValue visitElementaryTypeName(SolidityParser.ElementaryTypeNameContext ctx) {
        return super.visitElementaryTypeName(ctx);
    }

    @Override
    public SolValue visitExpression(SolidityParser.ExpressionContext ctx) {
        // log.info(ctx.getText());
        return super.visitExpression(ctx);
    }

    @Override
    public SolValue visitPrimaryExpression(SolidityParser.PrimaryExpressionContext ctx) {
        return super.visitPrimaryExpression(ctx);
    }

    @Override
    public SolValue visitExpressionList(SolidityParser.ExpressionListContext ctx) {
        return super.visitExpressionList(ctx);
    }

    @Override
    public SolValue visitNameValueList(SolidityParser.NameValueListContext ctx) {
        return super.visitNameValueList(ctx);
    }

    @Override
    public SolValue visitNameValue(SolidityParser.NameValueContext ctx) {
        return super.visitNameValue(ctx);
    }

    @Override
    public SolValue visitFunctionCallArguments(SolidityParser.FunctionCallArgumentsContext ctx) {
        return super.visitFunctionCallArguments(ctx);
    }

    @Override
    public SolValue visitFunctionCall(SolidityParser.FunctionCallContext ctx) {
        return super.visitFunctionCall(ctx);
    }

    @Override
    public SolValue visitTupleExpression(SolidityParser.TupleExpressionContext ctx) {
        return super.visitTupleExpression(ctx);
    }

    @Override
    public SolValue visitTypeNameExpression(SolidityParser.TypeNameExpressionContext ctx) {
        return super.visitTypeNameExpression(ctx);
    }

    @Override
    public SolValue visitAssemblyItem(SolidityParser.AssemblyItemContext ctx) {
        return super.visitAssemblyItem(ctx);
    }

    @Override
    public SolValue visitAssemblyBlock(SolidityParser.AssemblyBlockContext ctx) {
        return super.visitAssemblyBlock(ctx);
    }

    @Override
    public SolValue visitAssemblyExpression(SolidityParser.AssemblyExpressionContext ctx) {
        return super.visitAssemblyExpression(ctx);
    }

    @Override
    public SolValue visitAssemblyCall(SolidityParser.AssemblyCallContext ctx) {
        return super.visitAssemblyCall(ctx);
    }

    @Override
    public SolValue visitAssemblyLocalDefinition(SolidityParser.AssemblyLocalDefinitionContext ctx) {
        return super.visitAssemblyLocalDefinition(ctx);
    }

    @Override
    public SolValue visitAssemblyAssignment(SolidityParser.AssemblyAssignmentContext ctx) {
        return super.visitAssemblyAssignment(ctx);
    }

    @Override
    public SolValue visitAssemblyIdentifierList(SolidityParser.AssemblyIdentifierListContext ctx) {
        return super.visitAssemblyIdentifierList(ctx);
    }

    @Override
    public SolValue visitAssemblyStackAssignment(SolidityParser.AssemblyStackAssignmentContext ctx) {
        return super.visitAssemblyStackAssignment(ctx);
    }

    @Override
    public SolValue visitLabelDefinition(SolidityParser.LabelDefinitionContext ctx) {
        return super.visitLabelDefinition(ctx);
    }

    @Override
    public SolValue visitAssemblySwitch(SolidityParser.AssemblySwitchContext ctx) {
        return super.visitAssemblySwitch(ctx);
    }

    @Override
    public SolValue visitAssemblyCase(SolidityParser.AssemblyCaseContext ctx) {
        return super.visitAssemblyCase(ctx);
    }

    @Override
    public SolValue visitAssemblyFunctionDefinition(SolidityParser.AssemblyFunctionDefinitionContext ctx) {
        return super.visitAssemblyFunctionDefinition(ctx);
    }

    @Override
    public SolValue visitAssemblyFunctionReturns(SolidityParser.AssemblyFunctionReturnsContext ctx) {
        return super.visitAssemblyFunctionReturns(ctx);
    }

    @Override
    public SolValue visitAssemblyFor(SolidityParser.AssemblyForContext ctx) {
        return super.visitAssemblyFor(ctx);
    }

    @Override
    public SolValue visitAssemblyIf(SolidityParser.AssemblyIfContext ctx) {
        return super.visitAssemblyIf(ctx);
    }

    @Override
    public SolValue visitAssemblyLiteral(SolidityParser.AssemblyLiteralContext ctx) {
        return super.visitAssemblyLiteral(ctx);
    }

    @Override
    public SolValue visitAssemblyTypedVariableList(SolidityParser.AssemblyTypedVariableListContext ctx) {
        return super.visitAssemblyTypedVariableList(ctx);
    }

    @Override
    public SolValue visitAssemblyType(SolidityParser.AssemblyTypeContext ctx) {
        return super.visitAssemblyType(ctx);
    }

    @Override
    public SolValue visitSubAssembly(SolidityParser.SubAssemblyContext ctx) {
        return super.visitSubAssembly(ctx);
    }

    @Override
    public SolValue visitNumberLiteral(SolidityParser.NumberLiteralContext ctx) {
        return super.visitNumberLiteral(ctx);
    }

    @Override
    public SolValue visitIdentifier(SolidityParser.IdentifierContext ctx) {
        return super.visitIdentifier(ctx);
    }

    @Override
    public SolValue visitHexLiteral(SolidityParser.HexLiteralContext ctx) {
        return super.visitHexLiteral(ctx);
    }

    @Override
    public SolValue visitStringLiteral(SolidityParser.StringLiteralContext ctx) {
        return super.visitStringLiteral(ctx);
    }
}
