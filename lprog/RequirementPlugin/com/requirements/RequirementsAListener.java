// Generated from D:/Programme Files/IntelliJ/IntelliJ IDEA 2023.2.4/sem4pi-23-24-2dl3/lprog/RequirementPlugin/com/requirements/RequirementsA.g4 by ANTLR 4.13.1
package com.requirements;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RequirementsAParser}.
 */
public interface RequirementsAListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(RequirementsAParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(RequirementsAParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(RequirementsAParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(RequirementsAParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#startRange}.
	 * @param ctx the parse tree
	 */
	void enterStartRange(RequirementsAParser.StartRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#startRange}.
	 * @param ctx the parse tree
	 */
	void exitStartRange(RequirementsAParser.StartRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#endRange}.
	 * @param ctx the parse tree
	 */
	void enterEndRange(RequirementsAParser.EndRangeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#endRange}.
	 * @param ctx the parse tree
	 */
	void exitEndRange(RequirementsAParser.EndRangeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#questionShort}.
	 * @param ctx the parse tree
	 */
	void enterQuestionShort(RequirementsAParser.QuestionShortContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#questionShort}.
	 * @param ctx the parse tree
	 */
	void exitQuestionShort(RequirementsAParser.QuestionShortContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(RequirementsAParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(RequirementsAParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#solution}.
	 * @param ctx the parse tree
	 */
	void enterSolution(RequirementsAParser.SolutionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#solution}.
	 * @param ctx the parse tree
	 */
	void exitSolution(RequirementsAParser.SolutionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#number_text}.
	 * @param ctx the parse tree
	 */
	void enterNumber_text(RequirementsAParser.Number_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#number_text}.
	 * @param ctx the parse tree
	 */
	void exitNumber_text(RequirementsAParser.Number_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#degree}.
	 * @param ctx the parse tree
	 */
	void enterDegree(RequirementsAParser.DegreeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#degree}.
	 * @param ctx the parse tree
	 */
	void exitDegree(RequirementsAParser.DegreeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#degree_text}.
	 * @param ctx the parse tree
	 */
	void enterDegree_text(RequirementsAParser.Degree_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#degree_text}.
	 * @param ctx the parse tree
	 */
	void exitDegree_text(RequirementsAParser.Degree_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#language}.
	 * @param ctx the parse tree
	 */
	void enterLanguage(RequirementsAParser.LanguageContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#language}.
	 * @param ctx the parse tree
	 */
	void exitLanguage(RequirementsAParser.LanguageContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#language_text}.
	 * @param ctx the parse tree
	 */
	void enterLanguage_text(RequirementsAParser.Language_textContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#language_text}.
	 * @param ctx the parse tree
	 */
	void exitLanguage_text(RequirementsAParser.Language_textContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#solutionLanguage}.
	 * @param ctx the parse tree
	 */
	void enterSolutionLanguage(RequirementsAParser.SolutionLanguageContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#solutionLanguage}.
	 * @param ctx the parse tree
	 */
	void exitSolutionLanguage(RequirementsAParser.SolutionLanguageContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#multiop}.
	 * @param ctx the parse tree
	 */
	void enterMultiop(RequirementsAParser.MultiopContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#multiop}.
	 * @param ctx the parse tree
	 */
	void exitMultiop(RequirementsAParser.MultiopContext ctx);
	/**
	 * Enter a parse tree produced by {@link RequirementsAParser#parenteses}.
	 * @param ctx the parse tree
	 */
	void enterParenteses(RequirementsAParser.ParentesesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RequirementsAParser#parenteses}.
	 * @param ctx the parse tree
	 */
	void exitParenteses(RequirementsAParser.ParentesesContext ctx);
}