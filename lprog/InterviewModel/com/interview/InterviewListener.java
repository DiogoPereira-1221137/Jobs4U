// Generated from C:/Users/orlan/Desktop/sem4pi-23-24-2dl3.git/lprog/InterviewModel/com/interview/Interview.g4 by ANTLR 4.13.1
package com.interview;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link InterviewParser}.
 */
public interface InterviewListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link InterviewParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(InterviewParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(InterviewParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#truefalse}.
	 * @param ctx the parse tree
	 */
	void enterTruefalse(InterviewParser.TruefalseContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#truefalse}.
	 * @param ctx the parse tree
	 */
	void exitTruefalse(InterviewParser.TruefalseContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#trueFalse_expected}.
	 * @param ctx the parse tree
	 */
	void enterTrueFalse_expected(InterviewParser.TrueFalse_expectedContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#trueFalse_expected}.
	 * @param ctx the parse tree
	 */
	void exitTrueFalse_expected(InterviewParser.TrueFalse_expectedContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#shortText}.
	 * @param ctx the parse tree
	 */
	void enterShortText(InterviewParser.ShortTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#shortText}.
	 * @param ctx the parse tree
	 */
	void exitShortText(InterviewParser.ShortTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#choiceSingle}.
	 * @param ctx the parse tree
	 */
	void enterChoiceSingle(InterviewParser.ChoiceSingleContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#choiceSingle}.
	 * @param ctx the parse tree
	 */
	void exitChoiceSingle(InterviewParser.ChoiceSingleContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#proficiency}.
	 * @param ctx the parse tree
	 */
	void enterProficiency(InterviewParser.ProficiencyContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#proficiency}.
	 * @param ctx the parse tree
	 */
	void exitProficiency(InterviewParser.ProficiencyContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#choiceMulti}.
	 * @param ctx the parse tree
	 */
	void enterChoiceMulti(InterviewParser.ChoiceMultiContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#choiceMulti}.
	 * @param ctx the parse tree
	 */
	void exitChoiceMulti(InterviewParser.ChoiceMultiContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#multiop}.
	 * @param ctx the parse tree
	 */
	void enterMultiop(InterviewParser.MultiopContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#multiop}.
	 * @param ctx the parse tree
	 */
	void exitMultiop(InterviewParser.MultiopContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(InterviewParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(InterviewParser.AnswerContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#intNum}.
	 * @param ctx the parse tree
	 */
	void enterIntNum(InterviewParser.IntNumContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#intNum}.
	 * @param ctx the parse tree
	 */
	void exitIntNum(InterviewParser.IntNumContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#integerNum}.
	 * @param ctx the parse tree
	 */
	void enterIntegerNum(InterviewParser.IntegerNumContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#integerNum}.
	 * @param ctx the parse tree
	 */
	void exitIntegerNum(InterviewParser.IntegerNumContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#decimal}.
	 * @param ctx the parse tree
	 */
	void enterDecimal(InterviewParser.DecimalContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#decimal}.
	 * @param ctx the parse tree
	 */
	void exitDecimal(InterviewParser.DecimalContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#decNum}.
	 * @param ctx the parse tree
	 */
	void enterDecNum(InterviewParser.DecNumContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#decNum}.
	 * @param ctx the parse tree
	 */
	void exitDecNum(InterviewParser.DecNumContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(InterviewParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(InterviewParser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#date_text}.
	 * @param ctx the parse tree
	 */
	void enterDate_text(InterviewParser.Date_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#date_text}.
	 * @param ctx the parse tree
	 */
	void exitDate_text(InterviewParser.Date_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#time}.
	 * @param ctx the parse tree
	 */
	void enterTime(InterviewParser.TimeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#time}.
	 * @param ctx the parse tree
	 */
	void exitTime(InterviewParser.TimeContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#numericScale}.
	 * @param ctx the parse tree
	 */
	void enterNumericScale(InterviewParser.NumericScaleContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#numericScale}.
	 * @param ctx the parse tree
	 */
	void exitNumericScale(InterviewParser.NumericScaleContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#startRange}.
	 * @param ctx the parse tree
	 */
	void enterStartRange(InterviewParser.StartRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#startRange}.
	 * @param ctx the parse tree
	 */
	void exitStartRange(InterviewParser.StartRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#endRange}.
	 * @param ctx the parse tree
	 */
	void enterEndRange(InterviewParser.EndRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#endRange}.
	 * @param ctx the parse tree
	 */
	void exitEndRange(InterviewParser.EndRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#parenteses}.
	 * @param ctx the parse tree
	 */
	void enterParenteses(InterviewParser.ParentesesContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#parenteses}.
	 * @param ctx the parse tree
	 */
	void exitParenteses(InterviewParser.ParentesesContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#dateType}.
	 * @param ctx the parse tree
	 */
	void enterDateType(InterviewParser.DateTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#dateType}.
	 * @param ctx the parse tree
	 */
	void exitDateType(InterviewParser.DateTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#dec}.
	 * @param ctx the parse tree
	 */
	void enterDec(InterviewParser.DecContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#dec}.
	 * @param ctx the parse tree
	 */
	void exitDec(InterviewParser.DecContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterviewParser#timeType}.
	 * @param ctx the parse tree
	 */
	void enterTimeType(InterviewParser.TimeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterviewParser#timeType}.
	 * @param ctx the parse tree
	 */
	void exitTimeType(InterviewParser.TimeTypeContext ctx);
}