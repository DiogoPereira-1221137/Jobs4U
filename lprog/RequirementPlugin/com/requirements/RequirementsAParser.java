// Generated from D:/Programme Files/IntelliJ/IntelliJ IDEA 2023.2.4/sem4pi-23-24-2dl3/lprog/RequirementPlugin/com/requirements/RequirementsA.g4 by ANTLR 4.13.1
package com.requirements;
 // classes to include
    import java.util.*;
    import java.lang.*;
    import java.io.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class RequirementsAParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, INT=9, 
		DEGREE=10, LANGUAGE=11, WS=12, PHRASE=13, TEXT=14;
	public static final int
		RULE_start = 0, RULE_question = 1, RULE_startRange = 2, RULE_endRange = 3, 
		RULE_questionShort = 4, RULE_number = 5, RULE_solution = 6, RULE_number_text = 7, 
		RULE_degree = 8, RULE_degree_text = 9, RULE_language = 10, RULE_language_text = 11, 
		RULE_solutionLanguage = 12, RULE_multiop = 13, RULE_parenteses = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "question", "startRange", "endRange", "questionShort", "number", 
			"solution", "number_text", "degree", "degree_text", "language", "language_text", 
			"solutionLanguage", "multiop", "parenteses"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Answer('", "'-'", "'Answer(Short Text Answer): '", "'Experience-years('", 
			"'Academic-degree('", "'Programming-languages('", "','", "'):'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "INT", "DEGREE", 
			"LANGUAGE", "WS", "PHRASE", "TEXT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "RequirementsA.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    int a = 0;
	    double points = 0;
	    boolean java = false;
	    boolean jscript = false;
	    boolean python = false;
	//    Map<String, Integer> memory = new HashMap<String, Integer>();

	public RequirementsAParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public DegreeContext degree() {
			return getRuleContext(DegreeContext.class,0);
		}
		public LanguageContext language() {
			return getRuleContext(LanguageContext.class,0);
		}
		public QuestionContext question() {
			return getRuleContext(QuestionContext.class,0);
		}
		public QuestionShortContext questionShort() {
			return getRuleContext(QuestionShortContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			number();
			setState(31);
			degree();
			setState(32);
			language();
			setState(33);
			question();
			setState(34);
			questionShort();

			                // System.out.printf("Correct!, %f\n", points);
			              
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionContext extends ParserRuleContext {
		public Token answer_value;
		public StartRangeContext startRange() {
			return getRuleContext(StartRangeContext.class,0);
		}
		public EndRangeContext endRange() {
			return getRuleContext(EndRangeContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public TerminalNode INT() { return getToken(RequirementsAParser.INT, 0); }
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			match(T__0);
			setState(38);
			startRange();
			setState(39);
			match(T__1);
			setState(40);
			endRange();
			setState(41);
			parenteses();
			setState(42);
			((QuestionContext)_localctx).answer_value = match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartRangeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(RequirementsAParser.INT, 0); }
		public StartRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterStartRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitStartRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitStartRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartRangeContext startRange() throws RecognitionException {
		StartRangeContext _localctx = new StartRangeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_startRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class EndRangeContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(RequirementsAParser.INT, 0); }
		public EndRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterEndRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitEndRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitEndRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndRangeContext endRange() throws RecognitionException {
		EndRangeContext _localctx = new EndRangeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_endRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionShortContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(RequirementsAParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(RequirementsAParser.TEXT, i);
		}
		public QuestionShortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_questionShort; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterQuestionShort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitQuestionShort(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitQuestionShort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionShortContext questionShort() throws RecognitionException {
		QuestionShortContext _localctx = new QuestionShortContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_questionShort);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(T__2);
			setState(50); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(49);
				match(TEXT);
				}
				}
				setState(52); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TEXT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends ParserRuleContext {
		public Token INT;
		public Number_textContext number_text() {
			return getRuleContext(Number_textContext.class,0);
		}
		public SolutionContext solution() {
			return getRuleContext(SolutionContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public TerminalNode INT() { return getToken(RequirementsAParser.INT, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			number_text();
			setState(55);
			solution();
			setState(56);
			parenteses();
			setState(57);
			((NumberContext)_localctx).INT = match(INT);

			        if(Integer.valueOf(((NumberContext)_localctx).INT.getText()) < 0){
			            System.out.println("Years of experience can´t be less than zero");
			        }
			        if(Integer.valueOf(((NumberContext)_localctx).INT.getText()) < 3) {
			            points+=Integer.valueOf(((NumberContext)_localctx).INT.getText());
			        } else {
			            if(Integer.valueOf(((NumberContext)_localctx).INT.getText()) < 7){
			                points+=1.5*Integer.valueOf(((NumberContext)_localctx).INT.getText());
			            } else {
			                if (Integer.valueOf(((NumberContext)_localctx).INT.getText()) < 35) {
			                    points+=2*Integer.valueOf(((NumberContext)_localctx).INT.getText());
			                } else {
			                   if (Integer.valueOf(((NumberContext)_localctx).INT.getText()) < 50) {
			                       points+=0;
			                   }
			                    else {
			                        System.out.println("Error in validation in years of experience");
			                    }
			                }
			            }
			        }

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SolutionContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(RequirementsAParser.INT, 0); }
		public SolutionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_solution; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterSolution(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitSolution(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitSolution(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SolutionContext solution() throws RecognitionException {
		SolutionContext _localctx = new SolutionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_solution);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Number_textContext extends ParserRuleContext {
		public Number_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterNumber_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitNumber_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitNumber_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Number_textContext number_text() throws RecognitionException {
		Number_textContext _localctx = new Number_textContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_number_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DegreeContext extends ParserRuleContext {
		public Token DEGREE;
		public Degree_textContext degree_text() {
			return getRuleContext(Degree_textContext.class,0);
		}
		public TerminalNode DEGREE() { return getToken(RequirementsAParser.DEGREE, 0); }
		public DegreeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_degree; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterDegree(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitDegree(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitDegree(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DegreeContext degree() throws RecognitionException {
		DegreeContext _localctx = new DegreeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_degree);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			degree_text();
			setState(65);
			((DegreeContext)_localctx).DEGREE = match(DEGREE);

			    if(((DegreeContext)_localctx).DEGREE.getText().equals("PhD")){
			        points+=10;
			    } else if(((DegreeContext)_localctx).DEGREE.getText().equals("Master")){
			        points+=5;
			   } else if(((DegreeContext)_localctx).DEGREE.getText().equals("Bachelor")){
			                 points+=2;
			   } else {
			        points+=0;
			   }

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Degree_textContext extends ParserRuleContext {
		public TerminalNode DEGREE() { return getToken(RequirementsAParser.DEGREE, 0); }
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public Degree_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_degree_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterDegree_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitDegree_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitDegree_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Degree_textContext degree_text() throws RecognitionException {
		Degree_textContext _localctx = new Degree_textContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_degree_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(T__4);
			setState(69);
			match(DEGREE);
			setState(70);
			parenteses();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LanguageContext extends ParserRuleContext {
		public Language_textContext language_text() {
			return getRuleContext(Language_textContext.class,0);
		}
		public SolutionLanguageContext solutionLanguage() {
			return getRuleContext(SolutionLanguageContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public MultiopContext multiop() {
			return getRuleContext(MultiopContext.class,0);
		}
		public LanguageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_language; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterLanguage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitLanguage(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitLanguage(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LanguageContext language() throws RecognitionException {
		LanguageContext _localctx = new LanguageContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_language);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			language_text();
			setState(73);
			solutionLanguage();
			setState(74);
			parenteses();
			setState(75);
			multiop();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Language_textContext extends ParserRuleContext {
		public Language_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_language_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterLanguage_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitLanguage_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitLanguage_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Language_textContext language_text() throws RecognitionException {
		Language_textContext _localctx = new Language_textContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_language_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SolutionLanguageContext extends ParserRuleContext {
		public MultiopContext multiop() {
			return getRuleContext(MultiopContext.class,0);
		}
		public SolutionLanguageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_solutionLanguage; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterSolutionLanguage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitSolutionLanguage(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitSolutionLanguage(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SolutionLanguageContext solutionLanguage() throws RecognitionException {
		SolutionLanguageContext _localctx = new SolutionLanguageContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_solutionLanguage);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			multiop();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MultiopContext extends ParserRuleContext {
		public List<TerminalNode> LANGUAGE() { return getTokens(RequirementsAParser.LANGUAGE); }
		public TerminalNode LANGUAGE(int i) {
			return getToken(RequirementsAParser.LANGUAGE, i);
		}
		public MultiopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterMultiop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitMultiop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitMultiop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiopContext multiop() throws RecognitionException {
		MultiopContext _localctx = new MultiopContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_multiop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(LANGUAGE);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(82);
				match(T__6);
				setState(83);
				match(LANGUAGE);
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParentesesContext extends ParserRuleContext {
		public ParentesesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenteses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).enterParenteses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof RequirementsAListener ) ((RequirementsAListener)listener).exitParenteses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof RequirementsAVisitor ) return ((RequirementsAVisitor<? extends T>)visitor).visitParenteses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParentesesContext parenteses() throws RecognitionException {
		ParentesesContext _localctx = new ParentesesContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_parenteses);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000e\\\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0004\u00043\b\u0004\u000b\u0004\f\u00044\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0005\rU\b\r\n\r\f\rX\t\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0000\u0000\u000f\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000\u0000N\u0000"+
		"\u001e\u0001\u0000\u0000\u0000\u0002%\u0001\u0000\u0000\u0000\u0004,\u0001"+
		"\u0000\u0000\u0000\u0006.\u0001\u0000\u0000\u0000\b0\u0001\u0000\u0000"+
		"\u0000\n6\u0001\u0000\u0000\u0000\f<\u0001\u0000\u0000\u0000\u000e>\u0001"+
		"\u0000\u0000\u0000\u0010@\u0001\u0000\u0000\u0000\u0012D\u0001\u0000\u0000"+
		"\u0000\u0014H\u0001\u0000\u0000\u0000\u0016M\u0001\u0000\u0000\u0000\u0018"+
		"O\u0001\u0000\u0000\u0000\u001aQ\u0001\u0000\u0000\u0000\u001cY\u0001"+
		"\u0000\u0000\u0000\u001e\u001f\u0003\n\u0005\u0000\u001f \u0003\u0010"+
		"\b\u0000 !\u0003\u0014\n\u0000!\"\u0003\u0002\u0001\u0000\"#\u0003\b\u0004"+
		"\u0000#$\u0006\u0000\uffff\uffff\u0000$\u0001\u0001\u0000\u0000\u0000"+
		"%&\u0005\u0001\u0000\u0000&\'\u0003\u0004\u0002\u0000\'(\u0005\u0002\u0000"+
		"\u0000()\u0003\u0006\u0003\u0000)*\u0003\u001c\u000e\u0000*+\u0005\t\u0000"+
		"\u0000+\u0003\u0001\u0000\u0000\u0000,-\u0005\t\u0000\u0000-\u0005\u0001"+
		"\u0000\u0000\u0000./\u0005\t\u0000\u0000/\u0007\u0001\u0000\u0000\u0000"+
		"02\u0005\u0003\u0000\u000013\u0005\u000e\u0000\u000021\u0001\u0000\u0000"+
		"\u000034\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001\u0000"+
		"\u0000\u00005\t\u0001\u0000\u0000\u000067\u0003\u000e\u0007\u000078\u0003"+
		"\f\u0006\u000089\u0003\u001c\u000e\u00009:\u0005\t\u0000\u0000:;\u0006"+
		"\u0005\uffff\uffff\u0000;\u000b\u0001\u0000\u0000\u0000<=\u0005\t\u0000"+
		"\u0000=\r\u0001\u0000\u0000\u0000>?\u0005\u0004\u0000\u0000?\u000f\u0001"+
		"\u0000\u0000\u0000@A\u0003\u0012\t\u0000AB\u0005\n\u0000\u0000BC\u0006"+
		"\b\uffff\uffff\u0000C\u0011\u0001\u0000\u0000\u0000DE\u0005\u0005\u0000"+
		"\u0000EF\u0005\n\u0000\u0000FG\u0003\u001c\u000e\u0000G\u0013\u0001\u0000"+
		"\u0000\u0000HI\u0003\u0016\u000b\u0000IJ\u0003\u0018\f\u0000JK\u0003\u001c"+
		"\u000e\u0000KL\u0003\u001a\r\u0000L\u0015\u0001\u0000\u0000\u0000MN\u0005"+
		"\u0006\u0000\u0000N\u0017\u0001\u0000\u0000\u0000OP\u0003\u001a\r\u0000"+
		"P\u0019\u0001\u0000\u0000\u0000QV\u0005\u000b\u0000\u0000RS\u0005\u0007"+
		"\u0000\u0000SU\u0005\u000b\u0000\u0000TR\u0001\u0000\u0000\u0000UX\u0001"+
		"\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000VW\u0001\u0000\u0000\u0000"+
		"W\u001b\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000YZ\u0005\b\u0000"+
		"\u0000Z\u001d\u0001\u0000\u0000\u0000\u00024V";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}