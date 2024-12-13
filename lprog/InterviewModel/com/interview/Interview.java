package com.interview;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interview {
    public static void main(String[] args) throws IOException {
        int opt = -1;
        Scanner scan = new Scanner(System.in);
        while (opt != 0){
            System.out.println("#=====================#");
            System.out.println("Interview Model Plugin");
            System.out.println("#=====================#");
            System.out.println("1> Analyse Text File");
            System.out.println("2> Export Template");
            System.out.println("3> Evaluate the Interview");
            System.out.println();
            System.out.println("0> Exit");
            System.out.println();
            System.out.print(">> ");
            opt = scan.nextInt();
            switch (opt){
                case 1: System.out.println(antlr("lprog/InterviewModel/InterviewModel.txt")? "Syntax Correct":"Syntax Incorrect"); break;
                case 2: List<String> text = exportText();
                        for (String s: text) {System.out.println(s);}
                        break;
                case 3: evaluateInterview("lprog/InterviewModel/InterviewModel.txt");break;
                case 0: break;
                default: System.out.println("Invalid"); break;
            }
        }
    }
    public static boolean antlr(String file) throws IOException{
//        System.out.println(System.getProperty("user.dir"));
        InterviewLexer lexer = new InterviewLexer(CharStreams.fromFileName(file));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        InterviewParser parser = new InterviewParser(tokens);
        ParseTree tree = parser.start();

        return (parser.getNumberOfSyntaxErrors() == 0);
//        parseWithListener(tree);
//        if(parser.getNumberOfSyntaxErrors() != 0) {
//            throw new RuntimeException("There are issues with the file's syntax. Please correct them and then try to upload the file again.");
//        }

    }
    public static void parseWithListener(ParseTree tree) throws IOException {
        ParseTreeWalker walker = new ParseTreeWalker();
        InterviewMListener eListener = new InterviewMListener();
        walker.walk(eListener, tree);
        System.out.println("\nResult: ");
        System.out.println(eListener.getResult());
    }

    public static int evaluateInterview(String file) throws IOException{
//        System.out.println(System.getProperty("user.dir"));
        InterviewLexer lexer = new InterviewLexer(CharStreams.fromFileName(file));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        InterviewParser parser = new InterviewParser(tokens);
        ParseTree tree = parser.start();
        return parseWithVisitor(tree);
    }
    public static int parseWithVisitor(ParseTree tree) throws IOException {
        InterviewMVisitor eval = new InterviewMVisitor();
        Integer value = (Integer) eval.visit(tree);
        System.out.println("Result with Visitor : ");
        System.out.println(eval.feedback());
        return eval.grade();
    }


    public static List<String> exportText() throws IOException{
        List<String> text = new ArrayList<>();

        text.add("True/False: Do you have previous experience in project management?\n");
        text.add("Short Text Answer: What is your most relevant professional experience for this position? (Character limit: 200)\n");
        text.add("Choice, with Single-Answer: What is your proficiency level in Python?\n");
        text.add("Choice, with Multiple-Answer: What programming languages do you know?\n");
        text.add("Integer Number: How many years of experience do you have in sales?\n");
        text.add("Decimal Number: What is your level of fluency in English?\n");
        text.add("Date: What is the available start date to begin working?\n");
        text.add("Time: At what time of day do you feel most productive?\n");
        text.add("Numeric Scale: On a scale of 1 to 5, how skilled do you consider yourself in solving problems creatively?\n");


        return text;
    }
}