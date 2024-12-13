package com.requirements;

import java.util.Arrays;

public class RequirementsVisitor extends RequirementsABaseVisitor{
    public int solution = 0;

    public String feedback = "";

    public String degree = "";

    public String[] languages;

    public int startRange = 0;

    public int endRange = 0;

    public static final int MAX_CHARACTERS = 200;

    public boolean hasErrors = false;



    @Override
    public Integer visitStart(RequirementsAParser.StartContext ctx){
        visit(ctx.number());
        visit(ctx.degree());
        visit(ctx.language());
        visit(ctx.question());
        visit(ctx.questionShort());

        return 0;
    }

    @Override
    public Integer visitNumber(RequirementsAParser.NumberContext ctx){
        visit(ctx.solution());

        int answer = Integer.parseInt(ctx.INT.getText());

        if(answer >= solution){
            feedback += ("\nExperience-years question: Correct answer!\n");
        }else{
            feedback = String.format("\nExperience-years question: Wrong answer!\n Answer: %s\n Expected: %s\n", answer, solution);
            hasErrors = true;
        }

        return 0;
    }

    @Override
    public Integer visitSolution(RequirementsAParser.SolutionContext ctx){
        solution = Integer.parseInt(ctx.getText());

        return 0;
    }

    @Override
    public Integer visitDegree(RequirementsAParser.DegreeContext ctx){
        visit(ctx.degree_text());

        String answer = ctx.DEGREE().toString();

        if(degree.equals("None")){
            feedback += ("\nAcademic-degree question: Correct answer!\n");
        }else if(degree.equals("Bachelor") && !answer.equals("None")){
            feedback += ("\nAcademic-degree question: Correct answer!\n");
        }else if(degree.equals("Master") && !answer.equals("None") && !answer.equals("Bachelor")){
            feedback += ("\nAcademic-degree question: Correct answer!\n");
        }else if(degree.equals("PhD") && !answer.equals("None") && !answer.equals("Bachelor") && !answer.equals("Master")){
            feedback += ("\nAcademic-degree question: Correct answer!\n");
        }else{
            feedback += String.format("\nAcademic-degree question: Wrong answer!\n A minimum %s degree is required for the job position.\n", degree);
            hasErrors = true;
        }

        return 0;
    }

    @Override
    public Integer visitDegree_text(RequirementsAParser.Degree_textContext ctx){
        degree = ctx.DEGREE().getText();

        return 0;
    }

    @Override
    public Integer visitLanguage(RequirementsAParser.LanguageContext ctx){
        visit(ctx.solutionLanguage());

        String answer = ctx.multiop().getText();
        String[] answers = answer.split(",");

        int counter = 0;

        for(String language : languages){
            for(String  string : answers){
                if (string.equals(language)) {
                    counter++;
                    break;
                }
            }
        }

        if (counter == languages.length){
            feedback += ("\nProgramming-languages question: Correct answer!\n");
        } else{
            feedback += String.format("\nProgramming-languages question: Wrong answer!\n Answer: %s\n Expected: %s\n", Arrays.toString(answers),Arrays.toString(languages));
            hasErrors = true;
        }

        return 0;
    }

    @Override
    public Integer visitSolutionLanguage(RequirementsAParser.SolutionLanguageContext ctx){
        String l = ctx.multiop().getText();

        languages = l.split(",");

        return 0;
    }


    @Override
    public Integer visitQuestion(RequirementsAParser.QuestionContext ctx){
        visit(ctx.startRange());
        visit(ctx.endRange());


        int answer = Integer.parseInt(ctx.answer_value.getText());

        if(startRange <= answer && answer <= endRange){
            feedback += ("\nQuestion: Correct answer!\n");
        }else{
            feedback += String.format("\nQuestion: Wrong answer!\n Answer: %s\n Expected: %s-%s\n", answer, startRange, endRange);
            hasErrors = true;
        }

        return 0;
    }

    @Override
    public Integer visitStartRange(RequirementsAParser.StartRangeContext ctx){
        startRange = Integer.parseInt(ctx.INT().getText());

        return 0;
    }

    @Override
    public Integer visitEndRange(RequirementsAParser.EndRangeContext ctx){
        endRange = Integer.parseInt(ctx.INT().getText());

        return 0;
    }

    @Override
    public Integer visitQuestionShort(RequirementsAParser.QuestionShortContext ctx){
        String shortAnswer = ctx.TEXT().toString();

        if(shortAnswer.length() <= MAX_CHARACTERS){
            feedback +=  String.format("\nAnswer(Short Text Answer): Correct answer! (%d characters)\n", shortAnswer.length());
        }else{
            feedback += String.format("\nAnswer(Short Text Answer): Wrong answer!\n Answer has more than %s characters\n", MAX_CHARACTERS);
            hasErrors = true;
        }

        return 0;
    }


    public String feedback() {
        return feedback;
    }

    public Boolean hasErrors() {
        return hasErrors;
    }


}
