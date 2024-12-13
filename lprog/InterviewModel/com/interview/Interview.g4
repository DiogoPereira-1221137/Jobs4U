grammar Interview;

@parser::header {
    import java.util.*;
    import java.lang.*;
    import java.io.*;
}

@parser::members {
    double points = 0;
}

start: truefalse shortText choiceSingle choiceMulti intNum decimal date time numericScale EOF;


truefalse: 'True/False(' trueFalse_expected parenteses TRUE_FALSE;
trueFalse_expected:  TRUE_FALSE ;
TRUE_FALSE: 'True' | 'False';


shortText: 'Short Text Answer: ' TEXT+ ;


choiceSingle: 'Choice, with Single-Answer(' proficiency parenteses PROFICIENCY ;
proficiency: PROFICIENCY;


choiceMulti: 'Choice, with Multiple-Answer(' multiop parenteses answer ;
multiop: PROGRAMMING_LANGUAGES (','PROGRAMMING_LANGUAGES)* ;
answer: PROGRAMMING_LANGUAGES (','PROGRAMMING_LANGUAGES)* ;

intNum: 'Integer Number(' integerNum parenteses INT;
integerNum: INT ;


decimal: 'Decimal Number('  decNum parenteses dec ;
decNum: dec;

date: date_text dateType;
date_text: 'Date:' ;

time: 'Time:' timeType;


numericScale: 'Numeric Scale('startRange '-' endRange parenteses answer_value=INT ;
startRange: INT;
endRange: INT;


parenteses: '):';

INT: [0-9]+ ;


dateType: DATE ;

DATE: '0'[1-9]'/''0'[1-9]'/'[1-9][0-9][0-9][0-9] | '0'[1-9]'/''1'[0-2]'/'[1-9][0-9][0-9][0-9] | [1-2][0-9]'/''0'[1-9]'/'[1-9][0-9][0-9][0-9] | [1-2][0-9]'/''1'[0-2]'/'[1-9][0-9][0-9][0-9] | '3'[0-1]'/''0'[1-9]'/'[1-9][0-9][0-9][0-9] | '3'[0-1]'/''1'[0-2]'/'[1-9][0-9][0-9][0-9] ;


dec: (INT '.' INT);

timeType: TIME;

TIME: [0-2][0-3]':'[0-5][0-9];


PROGRAMMING_LANGUAGES: 'Java' | 'Python' | 'C' | 'Ruby' ;

PROFICIENCY: 'Beginner' | 'Pre Intermediate' | 'Intermediate' | 'Upper Intermediate' | 'Advanced' | 'Mastery' ;

WS: [ \t\r\n]+ -> skip;

PHRASE: '#'('a'..'z'|'A'..'Z'|'0'..'9'|' '|'('|')'|';'|'?'|'-'|',')* -> skip;

TEXT: ('a'..'z'|'A'..'Z'|'-'|'.')+  {getText().length() <= 200}? ;

