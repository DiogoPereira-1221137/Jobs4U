// Generated from D:/Programme Files/IntelliJ/IntelliJ IDEA 2023.2.4/sem4pi-23-24-2dl3/lprog/RequirementPlugin/com/requirements/RequirementsA.g4 by ANTLR 4.13.1
package com.requirements;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RequirementsAParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RequirementsAVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(RequirementsAParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(RequirementsAParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#startRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStartRange(RequirementsAParser.StartRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#endRange}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndRange(RequirementsAParser.EndRangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#questionShort}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestionShort(RequirementsAParser.QuestionShortContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(RequirementsAParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#solution}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSolution(RequirementsAParser.SolutionContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#number_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber_text(RequirementsAParser.Number_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#degree}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDegree(RequirementsAParser.DegreeContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#degree_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDegree_text(RequirementsAParser.Degree_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#language}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguage(RequirementsAParser.LanguageContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#language_text}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguage_text(RequirementsAParser.Language_textContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#solutionLanguage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSolutionLanguage(RequirementsAParser.SolutionLanguageContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#multiop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiop(RequirementsAParser.MultiopContext ctx);
	/**
	 * Visit a parse tree produced by {@link RequirementsAParser#parenteses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenteses(RequirementsAParser.ParentesesContext ctx);
}