grammar RequirementsA;
@parser::header { // classes to include
    import java.util.*;
    import java.lang.*;
    import java.io.*;
}
@parser::members {
    int a = 0;
    double points = 0;
    boolean java = false;
    boolean jscript = false;
    boolean python = false;
//    Map<String, Integer> memory = new HashMap<String, Integer>();
}
start: number degree language question questionShort{
                // System.out.printf("Correct!, %f\n", points);
              };

question: 'Answer(' startRange '-' endRange parenteses answer_value=INT;
startRange: INT;
endRange: INT;

questionShort: 'Answer(Short Text Answer): ' TEXT+;

number: number_text solution parenteses INT{
        if(Integer.valueOf($INT.getText()) < 0){
            System.out.println("Years of experience can´t be less than zero");
        }
        if(Integer.valueOf($INT.getText()) < 3) {
            points+=Integer.valueOf($INT.getText());
        } else {
            if(Integer.valueOf($INT.getText()) < 7){
                points+=1.5*Integer.valueOf($INT.getText());
            } else {
                if (Integer.valueOf($INT.getText()) < 35) {
                    points+=2*Integer.valueOf($INT.getText());
                } else {
                   if (Integer.valueOf($INT.getText()) < 50) {
                       points+=0;
                   }
                    else {
                        System.out.println("Error in validation in years of experience");
                    }
                }
            }
        }
};
solution: INT;
number_text: 'Experience-years(';

degree: degree_text DEGREE{
    if($DEGREE.getText().equals("PhD")){
        points+=10;
    } else if($DEGREE.getText().equals("Master")){
        points+=5;
   } else if($DEGREE.getText().equals("Bachelor")){
                 points+=2;
   } else {
        points+=0;
   }
};


degree_text: 'Academic-degree(' DEGREE parenteses;

language: language_text solutionLanguage parenteses multiop;

language_text: 'Programming-languages(';
solutionLanguage : multiop;
multiop: LANGUAGE (',' LANGUAGE)*;


parenteses: '):';

INT: ('0'..'9')+;

DEGREE: 'None'
| 'Bachelor'
| 'Master'
| 'PhD';

LANGUAGE: 'java'
| 'javascript'
| 'python';

WS: [ \t\r\n]+ -> skip;
PHRASE: '#'('a'..'z'|'A'..'Z'|'0'..'9'|' '|'('|')'|';'|'?')* -> skip;

TEXT: ('a'..'z'|'A'..'Z'|'-'|'.')+ {getText().length() <= 200}?;