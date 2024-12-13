// Generated from C:/Users/orlan/Desktop/sem4pi-23-24-2dl3.git/lprog/InterviewModel/com/interview/Interview.g4 by ANTLR 4.13.1
package com.interview;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link InterviewParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface InterviewVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link InterviewParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(InterviewParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#truefalse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTruefalse(InterviewParser.TruefalseContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#trueFalse_expected}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueFalse_expected(InterviewParser.TrueFalse_expectedContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#shortText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShortText(InterviewParser.ShortTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#choiceSingle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoiceSingle(InterviewParser.ChoiceSingleContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#proficiency}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProficiency(InterviewParser.ProficiencyContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#choiceMulti}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoiceMulti(InterviewParser.ChoiceMultiContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#multiop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiop(InterviewParser.MultiopContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer(InterviewParser.AnswerContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#intNum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntNum(InterviewParser.IntNumContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#integerNum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerNum(InterviewParser.IntegerNumContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#decimal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal(InterviewParser.DecimalContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#decNum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecNum(InterviewParser.DecNumContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(InterviewParser.DateContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#date_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_text(InterviewParser.Date_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#time}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime(InterviewParser.TimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#numericScale}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericScale(InterviewParser.NumericScaleContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#startRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStartRange(InterviewParser.StartRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#endRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndRange(InterviewParser.EndRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#parenteses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenteses(InterviewParser.ParentesesContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#dateType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateType(InterviewParser.DateTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDec(InterviewParser.DecContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterviewParser#timeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeType(InterviewParser.TimeTypeContext ctx);
}