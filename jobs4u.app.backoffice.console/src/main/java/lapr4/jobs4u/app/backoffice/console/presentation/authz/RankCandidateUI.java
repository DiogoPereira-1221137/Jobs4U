/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.rank.application.RankCandidateController;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class RankCandidateUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankCandidateUI.class);

    //private final AddUserController theController = new AddUserController();

    private final RankCandidateController theControllerRank = new RankCandidateController();

    @Override
    protected boolean doShow() {
        final List<JobOpening> list = new ArrayList<>();
        List<JobApplication> candidatesByJobOpening = new ArrayList<>();
        Iterable<JobOpening> listJobOpenings = theControllerRank.listAllJobOpenings();
        List<JobApplication> ranked = new ArrayList<>();



        int option;


        if (!listJobOpenings.iterator().hasNext()) {
            System.out.println("There is no JobOpening");
            return false;
        }

        while (true) {

            System.out.println("\nSelect a Job Opening");
            System.out.printf("%-6s%-20s%-10s%-18s%-30s%-20s%-20s%n", "Nº:", "Job Function", "Mode", "Contract Type", "Customer (Company Name)", "Job Reference", "Current State");

            int cont = 1;
            for (final JobOpening jobOpening : listJobOpenings) {
                list.add(jobOpening);

                System.out.printf("%-6s%-20s%-10s%-18s%-35s%-15s%-15s%n", cont, jobOpening.jobFunction().toString(), jobOpening.mode(), jobOpening.contractType()
                        , jobOpening.companyName().user().name(), jobOpening.jobReference().toString(), jobOpening.currentPhase());

                cont++;
            }

            option = Console.readInteger("Enter JobOpening nº to select or 0 to finish ");
            if (option == 0) {
                System.out.println("No JobOpening selected");
                return false;
            } else if (option < 0 || option > list.size()) {
                System.out.println("Invalid option! Please enter a number within the range.");
            } else {
                break;
            }

        }

        JobOpening jobOpeningSelected = list.get(option - 1);

        try {
            if (jobOpeningSelected.currentPhase() != PhaseNames.ANALYSIS) {
                System.err.println("The current phase needs to be 'Analysis' ");
                return false;

            }

        }catch (NullPointerException e){
            System.out.println("The recruitment process of that jobOpening is null ");
            return false;
        }


        Iterable<JobApplication> jobApplications = theControllerRank.findCandidates(jobOpeningSelected);



        try {

        if (!jobApplications.iterator().hasNext()) {
            System.out.println("There is no JobApplication associated to this JobOpening");
            return false;
        }


        candidatesByJobOpening = (List<JobApplication>) jobApplications;

        try{

            List<Id> rankSaveList = (List<Id>) theControllerRank.findRankByJobReference(jobOpeningSelected.jobReference());

            if (rankSaveList.iterator().hasNext()) transferRankingData(ranked,rankSaveList,candidatesByJobOpening);



        } catch (NoSuchElementException e) {
            System.out.println("\033[32m\n====== First Time Ranking ======\033[0m");
        }




        while (true) {

            System.out.println("\nAll Job Applications");
            System.out.printf("%-6s%-20s%-10s%-10s%n", "Nº:", "Candidate Name", "Grades", "Ranked");

            int cont = 1;

            for (final JobApplication jobApplication : candidatesByJobOpening) {

                boolean rank = false;

                if (ranked.contains(jobApplication)) rank = true;

                System.out.printf("%-6s%-20s%-10s%-10s%n",
                        cont,
                        jobApplication.candidate().user().name(),
                        (jobApplication.grade() != null && jobApplication.grade().grade() != null) ? jobApplication.grade().grade() : "No grade",
                        rank ? "Yes" : "No"
                );
                cont++;
            }
            int pag = 1;


            do{
            System.out.println("----------------------\n");

            System.out.println(pag + ". Rank the candidates");
            System.out.println((pag + 1) + ". Terminate Ranking");


            option = Console.readInteger("Enter option nº to select or 0 to finish ");
            if (option == 0) {
                System.out.println("No Option selected");
                return false;
            } else if (option == pag + 1) {
                //Terminate ranking
                break;
            } else if (option < 0 || option > pag) {
                System.out.println("Invalid option! Please enter a number within the range.");
            }

        }while(option < 0 || option > pag);


            if (option==pag+1) break;

            String candidates = Console.readLine("Enter the candidates you want to rank (you can choose more than one) [EX: 3,7,11 ]");

            String ranks = Console.readLine("Enter the ranks to the chosen candidates respectively [EX: 1,3,9 ]");



            int[] candidatesSplit = splitter(candidates);
            int[] ranksSplit = splitter(ranks);


            if (candidatesSplit.length != ranksSplit.length) System.out.println("The chosen candidates and ranks don't have the same size ");
            else if (!repeat(candidatesSplit)) System.out.println("You have a candidate chosen more than one time");
            else if (!repeat(ranksSplit)) System.out.println("You have a rank chosen more than one time");
            else{

                List<JobApplication> candidatesJobSplit = new ArrayList<>(candidatesByJobOpening);

                for (int i = 0; i < candidatesSplit.length; i++) {
                    if (candidatesSplit[i] > candidatesByJobOpening.size() || ranksSplit[i] > candidatesByJobOpening.size() || candidatesSplit[i]<1 || ranksSplit[i]<1 ) {
                        System.out.println("You choose a candidate or a rank that don't is possible in this list");
                        break;
                    }


                    moveToIndex(candidatesByJobOpening,ranked,candidatesJobSplit.get(candidatesSplit[i]-1),ranksSplit[i]-1);
                }
            }


        }



        do {
            System.out.println("----------------------\n");
            System.out.println("1. Save the ranks");
            System.out.println("2. Exit");
            option = Console.readInteger("You want to save or just exit ? ");
        }while (option > 2 || option<1 );

            if (option == 2) return false;

            ArrayList<Id> candidateFinalRanks = new ArrayList<>();

            Id idZero = new Id(new JobReference(0));

            for (JobApplication job: candidatesByJobOpening) {

                if (ranked.contains(job)) candidateFinalRanks.add(job.identity());
                else candidateFinalRanks.add(idZero);

            }

            theControllerRank.storeRank(jobOpeningSelected.jobReference(),candidateFinalRanks);


        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }



        return true;
    }


    public int[] splitter(String string){

        String[] split = string.split(",");
        int[] stringSplit = Arrays.stream(split)
                .mapToInt(Integer::parseInt)
                .toArray();

        return stringSplit;
    }

    public boolean repeat(int[] array){

        for ( int num : array ){
            int count=0;
            for ( int num2 : array ){
                if(num==num2) count++;

            }
            if (count>1) return false;
        }


        return true;
    }
    
    
    private void transferRankingData(List<JobApplication> ranked, List<Id> rankSaveList, List<JobApplication> candidatesByJobOpening){

//        int position=0;
        
        List<JobApplication> listAppSave = new ArrayList<>(candidatesByJobOpening);


        for (JobApplication job : listAppSave) {

            Id idJob = job.identity();

            if(rankSaveList.contains(idJob)){
                int index = rankSaveList.indexOf(idJob);
                int position = candidatesByJobOpening.indexOf(job);

                if(index!=position){

//                    swapIndexes(candidatesByJobOpening,ranked,position,index);
                    moveToIndex(candidatesByJobOpening,ranked,position,index);

                }

                else{
                    ranked.add(job);
                }

            }

//            position++;
        }
        
    }


    private <E> void swapIndexes(List<E> candidates, List<E> rankeds,int index1, int index2){
        E elementA = candidates.get(index1);
        E elementB = candidates.get(index2);

        candidates.set(index1, elementB);
        candidates.set(index2, elementA);

        if(!(rankeds.contains(elementA))){
            rankeds.add(elementA);
        }
//        if(!(rankeds.contains(elementB))) rankeds.add(elementB);
    }

    private <E> void swapIndexes(List<E> candidates, List<E> rankeds, E elementA, E elementB){
        int index1 = candidates.indexOf(elementA);
        int index2 = candidates.indexOf(elementB);

        candidates.set(index1, elementB);
        candidates.set(index2, elementA);


        if(!(rankeds.contains(elementA))) rankeds.add(elementA);
//        if(!(rankeds.contains(elementB))) rankeds.add(elementB);

    }

    private <E> void moveToIndex(List<E> candidates, List<E> rankeds, E element, int newIndex){
        int oldIndex = candidates.indexOf(element);

        candidates.remove(oldIndex);

        candidates.add(newIndex, element);

        if(!(rankeds.contains(element))){

            rankeds.add(element);
        }


    }

    private <E> void moveToIndex(List<E> candidates, List<E> rankeds, int oldIndex, int newIndex){
        E element = candidates.get(oldIndex);

        candidates.remove(oldIndex);

        candidates.add(newIndex, element);



        if(!(rankeds.contains(element))){
//            System.out.println(rankeds);

            rankeds.add(element);
        }

    }

    @Override
    public String headline() {
        return "Rank Candidates";
    }
}
