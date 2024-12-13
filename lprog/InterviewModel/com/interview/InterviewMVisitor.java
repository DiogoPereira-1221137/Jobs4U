package com.interview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InterviewMVisitor extends InterviewBaseVisitor {

    public int grade = 0;
    public String feedback = "";

    public String[] languages ;
    public static final int MAX_CHARACTERS = 200;
    public static final int NUMBER_EXCEPTED=2;
    public static final int NUMBER_EXCEPTED_2=6;
    public boolean hasErrors = false;


    @Override
    public Integer visitStart(InterviewParser.StartContext ctx){
        visit(ctx.truefalse());
        visit(ctx.shortText());
        visit(ctx.choiceSingle());
        visit(ctx.choiceMulti());
        visit(ctx.intNum());
        visit(ctx.decimal());
        visit(ctx.date());
        visit(ctx.time());
        visit(ctx.numericScale());

        return 0;
    }

    @Override
    public Integer visitTruefalse(InterviewParser.TruefalseContext ctx){
        String expected = String.valueOf(ctx.trueFalse_expected().getText());
        String answer = ctx.TRUE_FALSE().toString();

        if(answer.equals(expected)){
            feedback += ("\nTrue/False question: Correct answer!\n");
            grade= grade+10;
            feedback += String.format("current grade: %d\n", grade);
        }else{
            feedback = String.format("\nTrue/False question: Wrong answer!\n Answer: %s\n Expected: %s\n", answer, expected);
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;

    }

    @Override
    public Integer visitShortText(InterviewParser.ShortTextContext ctx){
        String shortText = ctx.TEXT().toString();

        if(shortText.length() <= MAX_CHARACTERS){
            feedback +=  String.format("\nShort Text Answer question: Correct answer! (%d characters)\n", shortText.length());
            grade= grade+12;
            feedback += String.format("current grade: %d\n", grade);
        }else{
            feedback += String.format("\nShort Text Answer question: Wrong answer!\n Answer has more than %s characters\n", MAX_CHARACTERS);
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;

    }

    @Override
    public Integer visitChoiceSingle(InterviewParser.ChoiceSingleContext ctx){
        String expected = String.valueOf(ctx.proficiency().getText());
        String answer = ctx.PROFICIENCY().toString();

        if(answer.equals(expected)){
            feedback += ("\nChoice, with Single-Answer question: Correct answer!\n");
            grade= grade+10;
            feedback += String.format("current grade: %d\n", grade);
        }else{
            feedback += String.format("\nChoice, with Single-Answer question: Wrong answer!\n A minimum %s proficiency is required for the job position.\n", expected);
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;

    }

    @Override
    public Integer visitChoiceMulti(InterviewParser.ChoiceMultiContext ctx){
        String answer_1 = String.valueOf(ctx.answer().PROGRAMMING_LANGUAGES(0));
        String answer_2 = String.valueOf(ctx.answer().PROGRAMMING_LANGUAGES(1));
        String answer_3 = String.valueOf(ctx.answer().PROGRAMMING_LANGUAGES(2));

        List<String> answers = new ArrayList<>();
        if(!answer_1.equals("null")){
            answers.add(answer_1);
        }
        if(!answer_2.equals("null")){
            answers.add(answer_2);
        }
        if(!answer_3.equals("null")){
            answers.add(answer_3);
        }

        String expected_1 = String.valueOf(ctx.multiop().PROGRAMMING_LANGUAGES(0));
        String expected_2 = String.valueOf(ctx.multiop().PROGRAMMING_LANGUAGES(1));
        String expected_3 = String.valueOf(ctx.multiop().PROGRAMMING_LANGUAGES(2));
        List<String> expected = new ArrayList<>();
        if(!expected_1.equals("null")){
            expected.add(expected_1);
        }
        if(!expected_2.equals("null")){
            expected.add(expected_2);
        }
        if(!expected_3.equals("null")){
            expected.add(expected_3);
        }

        if (answers.containsAll(Arrays.asList("Java", "Python", "C"))) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += 18;
            feedback += String.format("current grade: %d\n", grade);

        } else if (answers.containsAll(Arrays.asList("Java", "Python")) || answers.containsAll(Arrays.asList("Python", "Java"))) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += (int) (18 * 0.5);
            feedback += String.format("current grade: %d\n", grade);

        } else if (answers.containsAll(Arrays.asList("Java", "C")) || answers.containsAll(Arrays.asList("C", "Java"))) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += 18;
            feedback += String.format("current grade: %d\n", grade);

        } else if (answers.containsAll(Arrays.asList("C", "Python")) || answers.containsAll(Arrays.asList("Python", "C"))) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += (int) (18 * 0.6);
            feedback += String.format("current grade: %d\n", grade);

        }else if (answers.contains("Java") && expected.contains("Java")) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += (int) (18 * 0.4);
            feedback += String.format("current grade: %d\n", grade);

        } else if (answers.contains("C")) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += (int) (18 * 0.5);
            feedback += String.format("current grade: %d\n", grade);

        } else if (answers.contains("Python")) {
            feedback += ("\nChoice, with Multiple-Answer question: Correct answer!\n");
            grade += (int) (18 * 0.3);
            feedback += String.format("current grade: %d\n", grade);

        } else {
            feedback += String.format("\nChoice, with Multiple-Answer question: Wrong answer!\nA minimum knowledge of %s is required for the job position.\n", expected);
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;

    }


    @Override
    public Integer visitIntNum(InterviewParser.IntNumContext ctx){
        int expected = Integer.parseInt(ctx.integerNum().getText());

        int answer = Integer.parseInt(ctx.INT().getText());
        if(answer >= expected){
            feedback += ("\nInteger Number question: Correct answer!\n");
            grade= grade+10;
            feedback += String.format("current grade: %d\n", grade);
        }else{
            feedback += String.format("\nInteger Number question: Wrong answer!\n Answer: %s\n Expected: More or equal to %s\n", answer, expected);
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;
    }

    @Override
    public Integer visitDecimal(InterviewParser.DecimalContext ctx){

        float answer = Float.parseFloat(ctx.dec().getText());
        float expected = Float.parseFloat(ctx.decNum().getText());
        if(answer >= expected){
            feedback += ("\nDecimal Number question: Correct answer!\n");
            grade= grade+10;
            feedback += String.format("current grade: %d\n", grade);
        }else{
            feedback += String.format("\nDecimal Number question: Wrong answer!\n Answer: %s\n Expected: More or equal to %s\n", answer, expected);
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;
    }

    @Override
    public Integer visitDate(InterviewParser.DateContext ctx){
        String date = ctx.dateType().getText().trim();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(date);
            feedback += "\nDate question:  Correct answer! Valid date!\n";
            grade= grade+10;
            feedback += String.format("current grade: %s\n", grade);

        } catch (ParseException e) {
            feedback += "\nDate question: Wrong answer! Invalid date!\n";
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;

    }

    @Override
    public Integer visitTime(InterviewParser.TimeContext ctx){
        String date = ctx.timeType().getText().trim();

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setLenient(false);

        try {
            Date parsedDate = timeFormat.parse(date);
            feedback += "\nTime question:  Correct answer! Valid time!\n";
            grade= grade+10;
            feedback += String.format("current grade: %s\n", grade);

        } catch (ParseException e) {
            feedback += "\nTime question: Wrong answer! Invalid time!\n";
            feedback += String.format("current grade: %s\n", grade);
            hasErrors = true;
        }

        return 0;

    }

    @Override
    public Integer visitNumericScale(InterviewParser.NumericScaleContext ctx){
        int startRange = Integer.parseInt(ctx.startRange().getText());
        int endRange = Integer.parseInt(ctx.endRange().getText());

        try {
            int answer = Integer.parseInt(ctx.answer_value.getText());

            if (answer >= startRange && answer <= endRange) {
                feedback += "\nNumeric Scale question:  Correct answer! Valid answer within scale range!\n";
                if(answer >= NUMBER_EXCEPTED_2){
                    grade= grade+10;
                }else{
                    grade= grade+2;
                }
                feedback += String.format("current grade: %s\n", grade);
            } else {
                feedback += String.format("\nNumeric Scale question: Wrong answer! A minimum of %d and maximum of %s was required for this job position!\n", startRange,endRange);
                hasErrors = true;
                feedback += String.format("current grade: %s\n", grade);
            }
        } catch (NumberFormatException e) {
            feedback += "\nNumeric Scale question: Wrong Answer! Invalid answer, not a number!\n";
            hasErrors = true;
        }
        return 0;
    }

    public String feedback() {
        return feedback;
    }

    public int grade() {
        return grade;
    }
}
