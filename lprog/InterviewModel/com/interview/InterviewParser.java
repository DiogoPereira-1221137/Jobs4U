// Generated from C:/Users/orlan/Desktop/sem4pi-23-24-2dl3.git/lprog/InterviewModel/com/interview/Interview.g4 by ANTLR 4.13.1
package com.interview;

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
public class InterviewParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, TRUE_FALSE=14, INT=15, DATE=16, 
		TIME=17, PROGRAMMING_LANGUAGES=18, PROFICIENCY=19, WS=20, PHRASE=21, TEXT=22;
	public static final int
		RULE_start = 0, RULE_truefalse = 1, RULE_trueFalse_expected = 2, RULE_shortText = 3, 
		RULE_choiceSingle = 4, RULE_proficiency = 5, RULE_choiceMulti = 6, RULE_multiop = 7, 
		RULE_answer = 8, RULE_intNum = 9, RULE_integerNum = 10, RULE_decimal = 11, 
		RULE_decNum = 12, RULE_date = 13, RULE_date_text = 14, RULE_time = 15, 
		RULE_numericScale = 16, RULE_startRange = 17, RULE_endRange = 18, RULE_parenteses = 19, 
		RULE_dateType = 20, RULE_dec = 21, RULE_timeType = 22;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "truefalse", "trueFalse_expected", "shortText", "choiceSingle", 
			"proficiency", "choiceMulti", "multiop", "answer", "intNum", "integerNum", 
			"decimal", "decNum", "date", "date_text", "time", "numericScale", "startRange", 
			"endRange", "parenteses", "dateType", "dec", "timeType"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'True/False('", "'Short Text Answer: '", "'Choice, with Single-Answer('", 
			"'Choice, with Multiple-Answer('", "','", "'Integer Number('", "'Decimal Number('", 
			"'Date:'", "'Time:'", "'Numeric Scale('", "'-'", "'):'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, "TRUE_FALSE", "INT", "DATE", "TIME", "PROGRAMMING_LANGUAGES", 
			"PROFICIENCY", "WS", "PHRASE", "TEXT"
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
	public String getGrammarFileName() { return "Interview.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    double points = 0;

	public InterviewParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public TruefalseContext truefalse() {
			return getRuleContext(TruefalseContext.class,0);
		}
		public ShortTextContext shortText() {
			return getRuleContext(ShortTextContext.class,0);
		}
		public ChoiceSingleContext choiceSingle() {
			return getRuleContext(ChoiceSingleContext.class,0);
		}
		public ChoiceMultiContext choiceMulti() {
			return getRuleContext(ChoiceMultiContext.class,0);
		}
		public IntNumContext intNum() {
			return getRuleContext(IntNumContext.class,0);
		}
		public DecimalContext decimal() {
			return getRuleContext(DecimalContext.class,0);
		}
		public DateContext date() {
			return getRuleContext(DateContext.class,0);
		}
		public TimeContext time() {
			return getRuleContext(TimeContext.class,0);
		}
		public NumericScaleContext numericScale() {
			return getRuleContext(NumericScaleContext.class,0);
		}
		public TerminalNode EOF() { return getToken(InterviewParser.EOF, 0); }
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			truefalse();
			setState(47);
			shortText();
			setState(48);
			choiceSingle();
			setState(49);
			choiceMulti();
			setState(50);
			intNum();
			setState(51);
			decimal();
			setState(52);
			date();
			setState(53);
			time();
			setState(54);
			numericScale();
			setState(55);
			match(EOF);
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
	public static class TruefalseContext extends ParserRuleContext {
		public TrueFalse_expectedContext trueFalse_expected() {
			return getRuleContext(TrueFalse_expectedContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public TerminalNode TRUE_FALSE() { return getToken(InterviewParser.TRUE_FALSE, 0); }
		public TruefalseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_truefalse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterTruefalse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitTruefalse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitTruefalse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TruefalseContext truefalse() throws RecognitionException {
		TruefalseContext _localctx = new TruefalseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_truefalse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(T__0);
			setState(58);
			trueFalse_expected();
			setState(59);
			parenteses();
			setState(60);
			match(TRUE_FALSE);
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
	public static class TrueFalse_expectedContext extends ParserRuleContext {
		public TerminalNode TRUE_FALSE() { return getToken(InterviewParser.TRUE_FALSE, 0); }
		public TrueFalse_expectedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trueFalse_expected; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterTrueFalse_expected(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitTrueFalse_expected(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitTrueFalse_expected(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TrueFalse_expectedContext trueFalse_expected() throws RecognitionException {
		TrueFalse_expectedContext _localctx = new TrueFalse_expectedContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_trueFalse_expected);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(TRUE_FALSE);
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
	public static class ShortTextContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(InterviewParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(InterviewParser.TEXT, i);
		}
		public ShortTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shortText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterShortText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitShortText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitShortText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShortTextContext shortText() throws RecognitionException {
		ShortTextContext _localctx = new ShortTextContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_shortText);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(T__1);
			setState(66); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(65);
				match(TEXT);
				}
				}
				setState(68); 
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
	public static class ChoiceSingleContext extends ParserRuleContext {
		public ProficiencyContext proficiency() {
			return getRuleContext(ProficiencyContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public TerminalNode PROFICIENCY() { return getToken(InterviewParser.PROFICIENCY, 0); }
		public ChoiceSingleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choiceSingle; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterChoiceSingle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitChoiceSingle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitChoiceSingle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChoiceSingleContext choiceSingle() throws RecognitionException {
		ChoiceSingleContext _localctx = new ChoiceSingleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_choiceSingle);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(T__2);
			setState(71);
			proficiency();
			setState(72);
			parenteses();
			setState(73);
			match(PROFICIENCY);
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
	public static class ProficiencyContext extends ParserRuleContext {
		public TerminalNode PROFICIENCY() { return getToken(InterviewParser.PROFICIENCY, 0); }
		public ProficiencyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_proficiency; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterProficiency(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitProficiency(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitProficiency(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProficiencyContext proficiency() throws RecognitionException {
		ProficiencyContext _localctx = new ProficiencyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_proficiency);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(PROFICIENCY);
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
	public static class ChoiceMultiContext extends ParserRuleContext {
		public MultiopContext multiop() {
			return getRuleContext(MultiopContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public AnswerContext answer() {
			return getRuleContext(AnswerContext.class,0);
		}
		public ChoiceMultiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choiceMulti; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterChoiceMulti(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitChoiceMulti(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitChoiceMulti(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChoiceMultiContext choiceMulti() throws RecognitionException {
		ChoiceMultiContext _localctx = new ChoiceMultiContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_choiceMulti);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(T__3);
			setState(78);
			multiop();
			setState(79);
			parenteses();
			setState(80);
			answer();
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
		public List<TerminalNode> PROGRAMMING_LANGUAGES() { return getTokens(InterviewParser.PROGRAMMING_LANGUAGES); }
		public TerminalNode PROGRAMMING_LANGUAGES(int i) {
			return getToken(InterviewParser.PROGRAMMING_LANGUAGES, i);
		}
		public MultiopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterMultiop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitMultiop(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitMultiop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiopContext multiop() throws RecognitionException {
		MultiopContext _localctx = new MultiopContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_multiop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(PROGRAMMING_LANGUAGES);
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(83);
				match(T__4);
				setState(84);
				match(PROGRAMMING_LANGUAGES);
				}
				}
				setState(89);
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
	public static class AnswerContext extends ParserRuleContext {
		public List<TerminalNode> PROGRAMMING_LANGUAGES() { return getTokens(InterviewParser.PROGRAMMING_LANGUAGES); }
		public TerminalNode PROGRAMMING_LANGUAGES(int i) {
			return getToken(InterviewParser.PROGRAMMING_LANGUAGES, i);
		}
		public AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterAnswer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitAnswer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitAnswer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswerContext answer() throws RecognitionException {
		AnswerContext _localctx = new AnswerContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_answer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(PROGRAMMING_LANGUAGES);
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(91);
				match(T__4);
				setState(92);
				match(PROGRAMMING_LANGUAGES);
				}
				}
				setState(97);
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
	public static class IntNumContext extends ParserRuleContext {
		public IntegerNumContext integerNum() {
			return getRuleContext(IntegerNumContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public TerminalNode INT() { return getToken(InterviewParser.INT, 0); }
		public IntNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intNum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterIntNum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitIntNum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitIntNum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntNumContext intNum() throws RecognitionException {
		IntNumContext _localctx = new IntNumContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_intNum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__5);
			setState(99);
			integerNum();
			setState(100);
			parenteses();
			setState(101);
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
	public static class IntegerNumContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(InterviewParser.INT, 0); }
		public IntegerNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerNum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterIntegerNum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitIntegerNum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitIntegerNum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerNumContext integerNum() throws RecognitionException {
		IntegerNumContext _localctx = new IntegerNumContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_integerNum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
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
	public static class DecimalContext extends ParserRuleContext {
		public DecNumContext decNum() {
			return getRuleContext(DecNumContext.class,0);
		}
		public ParentesesContext parenteses() {
			return getRuleContext(ParentesesContext.class,0);
		}
		public DecContext dec() {
			return getRuleContext(DecContext.class,0);
		}
		public DecimalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decimal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterDecimal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitDecimal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitDecimal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecimalContext decimal() throws RecognitionException {
		DecimalContext _localctx = new DecimalContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_decimal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			match(T__6);
			setState(106);
			decNum();
			setState(107);
			parenteses();
			setState(108);
			dec();
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
	public static class DecNumContext extends ParserRuleContext {
		public DecContext dec() {
			return getRuleContext(DecContext.class,0);
		}
		public DecNumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decNum; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterDecNum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitDecNum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitDecNum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecNumContext decNum() throws RecognitionException {
		DecNumContext _localctx = new DecNumContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_decNum);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			dec();
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
	public static class DateContext extends ParserRuleContext {
		public Date_textContext date_text() {
			return getRuleContext(Date_textContext.class,0);
		}
		public DateTypeContext dateType() {
			return getRuleContext(DateTypeContext.class,0);
		}
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitDate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitDate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_date);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			date_text();
			setState(113);
			dateType();
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
	public static class Date_textContext extends ParserRuleContext {
		public Date_textContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterDate_text(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitDate_text(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitDate_text(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_textContext date_text() throws RecognitionException {
		Date_textContext _localctx = new Date_textContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_date_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
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

	@SuppressWarnings("CheckReturnValue")
	public static class TimeContext extends ParserRuleContext {
		public TimeTypeContext timeType() {
			return getRuleContext(TimeTypeContext.class,0);
		}
		public TimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitTime(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitTime(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeContext time() throws RecognitionException {
		TimeContext _localctx = new TimeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_time);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(T__8);
			setState(118);
			timeType();
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
	public static class NumericScaleContext extends ParserRuleContext {
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
		public TerminalNode INT() { return getToken(InterviewParser.INT, 0); }
		public NumericScaleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericScale; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterNumericScale(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitNumericScale(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitNumericScale(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericScaleContext numericScale() throws RecognitionException {
		NumericScaleContext _localctx = new NumericScaleContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_numericScale);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(T__9);
			setState(121);
			startRange();
			setState(122);
			match(T__10);
			setState(123);
			endRange();
			setState(124);
			parenteses();
			setState(125);
			((NumericScaleContext)_localctx).answer_value = match(INT);
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
		public TerminalNode INT() { return getToken(InterviewParser.INT, 0); }
		public StartRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterStartRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitStartRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitStartRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartRangeContext startRange() throws RecognitionException {
		StartRangeContext _localctx = new StartRangeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_startRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
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
		public TerminalNode INT() { return getToken(InterviewParser.INT, 0); }
		public EndRangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endRange; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterEndRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitEndRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitEndRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndRangeContext endRange() throws RecognitionException {
		EndRangeContext _localctx = new EndRangeContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_endRange);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
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
	public static class ParentesesContext extends ParserRuleContext {
		public ParentesesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenteses; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterParenteses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitParenteses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitParenteses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParentesesContext parenteses() throws RecognitionException {
		ParentesesContext _localctx = new ParentesesContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_parenteses);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(T__11);
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
	public static class DateTypeContext extends ParserRuleContext {
		public TerminalNode DATE() { return getToken(InterviewParser.DATE, 0); }
		public DateTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterDateType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitDateType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitDateType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateTypeContext dateType() throws RecognitionException {
		DateTypeContext _localctx = new DateTypeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_dateType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(DATE);
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
	public static class DecContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(InterviewParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(InterviewParser.INT, i);
		}
		public DecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitDec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecContext dec() throws RecognitionException {
		DecContext _localctx = new DecContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(135);
			match(INT);
			setState(136);
			match(T__12);
			setState(137);
			match(INT);
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
	public static class TimeTypeContext extends ParserRuleContext {
		public TerminalNode TIME() { return getToken(InterviewParser.TIME, 0); }
		public TimeTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_timeType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).enterTimeType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof InterviewListener ) ((InterviewListener)listener).exitTimeType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof InterviewVisitor ) return ((InterviewVisitor<? extends T>)visitor).visitTimeType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TimeTypeContext timeType() throws RecognitionException {
		TimeTypeContext _localctx = new TimeTypeContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_timeType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(TIME);
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
		"\u0004\u0001\u0016\u008e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007"+
		"\u0015\u0002\u0016\u0007\u0016\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0004\u0003C\b"+
		"\u0003\u000b\u0003\f\u0003D\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0005"+
		"\u0007V\b\u0007\n\u0007\f\u0007Y\t\u0007\u0001\b\u0001\b\u0001\b\u0005"+
		"\b^\b\b\n\b\f\ba\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f"+
		"\u0001\f\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0000"+
		"\u0000\u0017\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,\u0000\u0000y\u0000.\u0001\u0000\u0000"+
		"\u0000\u00029\u0001\u0000\u0000\u0000\u0004>\u0001\u0000\u0000\u0000\u0006"+
		"@\u0001\u0000\u0000\u0000\bF\u0001\u0000\u0000\u0000\nK\u0001\u0000\u0000"+
		"\u0000\fM\u0001\u0000\u0000\u0000\u000eR\u0001\u0000\u0000\u0000\u0010"+
		"Z\u0001\u0000\u0000\u0000\u0012b\u0001\u0000\u0000\u0000\u0014g\u0001"+
		"\u0000\u0000\u0000\u0016i\u0001\u0000\u0000\u0000\u0018n\u0001\u0000\u0000"+
		"\u0000\u001ap\u0001\u0000\u0000\u0000\u001cs\u0001\u0000\u0000\u0000\u001e"+
		"u\u0001\u0000\u0000\u0000 x\u0001\u0000\u0000\u0000\"\u007f\u0001\u0000"+
		"\u0000\u0000$\u0081\u0001\u0000\u0000\u0000&\u0083\u0001\u0000\u0000\u0000"+
		"(\u0085\u0001\u0000\u0000\u0000*\u0087\u0001\u0000\u0000\u0000,\u008b"+
		"\u0001\u0000\u0000\u0000./\u0003\u0002\u0001\u0000/0\u0003\u0006\u0003"+
		"\u000001\u0003\b\u0004\u000012\u0003\f\u0006\u000023\u0003\u0012\t\u0000"+
		"34\u0003\u0016\u000b\u000045\u0003\u001a\r\u000056\u0003\u001e\u000f\u0000"+
		"67\u0003 \u0010\u000078\u0005\u0000\u0000\u00018\u0001\u0001\u0000\u0000"+
		"\u00009:\u0005\u0001\u0000\u0000:;\u0003\u0004\u0002\u0000;<\u0003&\u0013"+
		"\u0000<=\u0005\u000e\u0000\u0000=\u0003\u0001\u0000\u0000\u0000>?\u0005"+
		"\u000e\u0000\u0000?\u0005\u0001\u0000\u0000\u0000@B\u0005\u0002\u0000"+
		"\u0000AC\u0005\u0016\u0000\u0000BA\u0001\u0000\u0000\u0000CD\u0001\u0000"+
		"\u0000\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000E\u0007"+
		"\u0001\u0000\u0000\u0000FG\u0005\u0003\u0000\u0000GH\u0003\n\u0005\u0000"+
		"HI\u0003&\u0013\u0000IJ\u0005\u0013\u0000\u0000J\t\u0001\u0000\u0000\u0000"+
		"KL\u0005\u0013\u0000\u0000L\u000b\u0001\u0000\u0000\u0000MN\u0005\u0004"+
		"\u0000\u0000NO\u0003\u000e\u0007\u0000OP\u0003&\u0013\u0000PQ\u0003\u0010"+
		"\b\u0000Q\r\u0001\u0000\u0000\u0000RW\u0005\u0012\u0000\u0000ST\u0005"+
		"\u0005\u0000\u0000TV\u0005\u0012\u0000\u0000US\u0001\u0000\u0000\u0000"+
		"VY\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000"+
		"\u0000X\u000f\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000Z_\u0005"+
		"\u0012\u0000\u0000[\\\u0005\u0005\u0000\u0000\\^\u0005\u0012\u0000\u0000"+
		"][\u0001\u0000\u0000\u0000^a\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000"+
		"\u0000_`\u0001\u0000\u0000\u0000`\u0011\u0001\u0000\u0000\u0000a_\u0001"+
		"\u0000\u0000\u0000bc\u0005\u0006\u0000\u0000cd\u0003\u0014\n\u0000de\u0003"+
		"&\u0013\u0000ef\u0005\u000f\u0000\u0000f\u0013\u0001\u0000\u0000\u0000"+
		"gh\u0005\u000f\u0000\u0000h\u0015\u0001\u0000\u0000\u0000ij\u0005\u0007"+
		"\u0000\u0000jk\u0003\u0018\f\u0000kl\u0003&\u0013\u0000lm\u0003*\u0015"+
		"\u0000m\u0017\u0001\u0000\u0000\u0000no\u0003*\u0015\u0000o\u0019\u0001"+
		"\u0000\u0000\u0000pq\u0003\u001c\u000e\u0000qr\u0003(\u0014\u0000r\u001b"+
		"\u0001\u0000\u0000\u0000st\u0005\b\u0000\u0000t\u001d\u0001\u0000\u0000"+
		"\u0000uv\u0005\t\u0000\u0000vw\u0003,\u0016\u0000w\u001f\u0001\u0000\u0000"+
		"\u0000xy\u0005\n\u0000\u0000yz\u0003\"\u0011\u0000z{\u0005\u000b\u0000"+
		"\u0000{|\u0003$\u0012\u0000|}\u0003&\u0013\u0000}~\u0005\u000f\u0000\u0000"+
		"~!\u0001\u0000\u0000\u0000\u007f\u0080\u0005\u000f\u0000\u0000\u0080#"+
		"\u0001\u0000\u0000\u0000\u0081\u0082\u0005\u000f\u0000\u0000\u0082%\u0001"+
		"\u0000\u0000\u0000\u0083\u0084\u0005\f\u0000\u0000\u0084\'\u0001\u0000"+
		"\u0000\u0000\u0085\u0086\u0005\u0010\u0000\u0000\u0086)\u0001\u0000\u0000"+
		"\u0000\u0087\u0088\u0005\u000f\u0000\u0000\u0088\u0089\u0005\r\u0000\u0000"+
		"\u0089\u008a\u0005\u000f\u0000\u0000\u008a+\u0001\u0000\u0000\u0000\u008b"+
		"\u008c\u0005\u0011\u0000\u0000\u008c-\u0001\u0000\u0000\u0000\u0003DW"+
		"_";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}