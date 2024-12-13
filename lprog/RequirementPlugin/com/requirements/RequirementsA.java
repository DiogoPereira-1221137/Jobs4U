package com.requirements;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequirementsA {
    public static void main(String[] args) throws IOException {
        int opt = -1;
        Scanner scan = new Scanner(System.in);
        while (opt != 0){
            System.out.println("\n#=====================#");
            System.out.println("Requirements Plugin");
            System.out.println("#=====================#");
            System.out.println("1> Validate File Syntax");
            System.out.println("2> Verify Requirements File");
            System.out.println("3> Export Template");
            System.out.println();
            System.out.println("0> Exit");
            System.out.println();
            System.out.print(">> ");
            opt = scan.nextInt();
            switch (opt){
                case 1: System.out.println(antlr("lprog/RequirementPlugin/text.txt")? "Syntax Correct":"Syntax Incorrect"); break;
                case 2: System.out.println(verifyRequirements("lprog/RequirementPlugin/text.txt") ? "Approved Application" : "Rejected Application"); break;
                case 3: List<String> text = exportText();
                        for (String s: text) {System.out.println(s);}
                        break;
                case 0: break;

                default: System.out.println("Invalid"); break;
            }
        }
    }
    public static boolean antlr(String file) throws IOException{
//        System.out.println(System.getProperty("user.dir"));
        RequirementsALexer lexer = new RequirementsALexer(CharStreams.fromFileName(file));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RequirementsAParser parser = new RequirementsAParser(tokens);
        ParseTree tree = parser.start();
//        parseWithListener(tree);
//        if(parser.getNumberOfSyntaxErrors() != 0) {
//            throw new RuntimeException("There are issues with the file's syntax. Please correct them and then try to upload the file again.");
//        }
        return (parser.getNumberOfSyntaxErrors() == 0);
    }

    public static boolean verifyRequirements(String file) throws IOException{
//        System.out.println(System.getProperty("user.dir"));
        RequirementsALexer lexer = new RequirementsALexer(CharStreams.fromFileName(file));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RequirementsAParser parser = new RequirementsAParser(tokens);
        ParseTree tree = parser.start();
       // parseWithListener(tree);
        return parseWithVisitor(tree);
    }

    public static void parseWithListener(ParseTree tree) throws IOException {
        ParseTreeWalker walker = new ParseTreeWalker();
        RequirementsListener eListener = new RequirementsListener();
        walker.walk(eListener, tree);
        System.out.println("\nResult: ");
        System.out.println(eListener.getResult());
    }

    public static boolean parseWithVisitor(ParseTree tree) throws IOException {
        RequirementsVisitor eval = new RequirementsVisitor();
        Integer value = (Integer) eval.visit(tree);
        System.out.println("Result with Visitor : ");
        System.out.println(eval.feedback());
        return !eval.hasErrors();
    }

    public static List<String> exportText() throws IOException{
        List<String> text = new ArrayList<>();

        text.add("# Enter the number of years of experience (integer)");
        text.add("Experience-years:");
        text.add("# Select one degree (None; Bachelor; Master; PhD)");
        text.add("Academic-degree:");
        text.add("# Select one or more programming languages you are proficient in (java; javascript; python)");
        text.add("Programming-languages: java, javascript");

        return text;
    }
}