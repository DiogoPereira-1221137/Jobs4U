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
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.jobApplication.application.SetUpPhasesJobOpeningController;
import lapr4.jobs4u.jobopening.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class SetUpPhasesJobOpeningUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetUpPhasesJobOpeningUI.class);

    //private final AddUserController theController = new AddUserController();

    private final SetUpPhasesJobOpeningController theControllerSetUpPhases = new SetUpPhasesJobOpeningController();




    @Override
    protected boolean doShow() {

        Iterable<JobOpening> listJobOpenings = theControllerSetUpPhases.jobOpeningByCustomerManager();

        JobOpening jobOpening = selectJobOpening(listJobOpenings);

        boolean hasInterview = selectHaveInterview();

        List<Phase> dates = selectDates(hasInterview);

        try {

            JobOpening jobOpeningUpdated = theControllerSetUpPhases.setUpPhasesJobOpening(jobOpening, dates, hasInterview);

            System.out.println("\n==========Job Opening Registered Successfully!==========");
            System.out.println("\033[1mJob Reference:\033[0m " + jobOpeningUpdated.jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpeningUpdated.numberOfVacancies() + "\n\033[1mJob Function:\033[0m "+ jobOpeningUpdated.jobFunction() + "\n\033[1mMode:\033[0m " + jobOpeningUpdated.mode() + "\n\033[1mContract Type:\033[0m "
                    + jobOpeningUpdated.contractType() +  "\n" + jobOpeningUpdated.address() + "\n\033[1mCustomer (Company Name):\033[0m " + jobOpeningUpdated.companyName().user().email() + "\n\033[1mDescription:\033[0m" + jobOpeningUpdated.description()
                    + "\n\033[1mRegistration Date:\033[0m " + CurrentTimeCalendars.now().getTime() + "\n\033[0mRecrutment Processs:\033[0m" + jobOpeningUpdated.recruitmentProcess().toString());



        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }

        return true;
    }

    private boolean selectHaveInterview() {
        while (true) {
            String hasInterview = Console.readLine("The application has an interview (Y/N): ");
            switch (hasInterview) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Invalid choice. Please enter a Y or N.");
            }
        }
    }

    private List<Phase> selectDates(boolean hasInterview) {
        List<Phase> phasesDates = new ArrayList<>();

        String phase = "\033[0m====Application Phase====\033[0m";
        Calendar beginDate = null;
        List<Calendar> timePeriodApplication = new ArrayList<>();
        List<Calendar> list = new ArrayList<>();
        List<Calendar> temp = new ArrayList<>();

        do{
            temp.clear();
            temp.addAll(setTimePeriod(phase, beginDate, true));
        } while (checkTimePeriodBetweenPhase(temp,timePeriodApplication));
        list.addAll(temp);

        timePeriodApplication.add(list.get(0));
        timePeriodApplication.add(list.get(1));


        ApplicationPhase applicationPhase = new ApplicationPhase(timePeriodApplication.get(0), timePeriodApplication.get(1));

        phasesDates.add(applicationPhase);

        phase = "\033[0m====Screening Phase====\033[0m";

        do{
            temp.clear();
            temp.addAll(setTimePeriod(phase, timePeriodApplication.get(1), false));
        } while (checkTimePeriodBetweenPhase(temp,timePeriodApplication));
        list.addAll(temp);

        timePeriodApplication.add(list.get(2));
        timePeriodApplication.add(list.get(3));

        ScreeningPhase screeningPhase = new ScreeningPhase(timePeriodApplication.get(2), timePeriodApplication.get(3));
//        System.out.println(timePeriodApplication.get(2).getTime());
//        System.out.println(timePeriodApplication.get(3).getTime());

        phasesDates.add(screeningPhase);

        int adding = 0;

        if(hasInterview){
            adding = 2;
            phase = "\033[0m====Interview Phase====\033[0m";
            do{
                temp.clear();
                temp.addAll(setTimePeriod(phase,  timePeriodApplication.get(3), false));
            } while (checkTimePeriodBetweenPhase(temp,timePeriodApplication));
            list.addAll(temp);

            timePeriodApplication.add(list.get(4));
            timePeriodApplication.add(list.get(5));

            InterviewPhase interviewPhase = new InterviewPhase(timePeriodApplication.get(4), timePeriodApplication.get(5));
//            System.out.println(timePeriodApplication.get(4).getTime());
//            System.out.println(timePeriodApplication.get(5).getTime());


            phasesDates.add(interviewPhase);
        }

        phase = "\033[0m====Analysis Phase====\033[0m";
        do{
            temp.clear();
            temp.addAll(setTimePeriod(phase,  timePeriodApplication.get(3 + adding), false));
        } while (checkTimePeriodBetweenPhase(temp,timePeriodApplication));
        list.addAll(temp);

        timePeriodApplication.add(list.get(4+adding));
        timePeriodApplication.add(list.get(5+adding));

        AnalysisPhase analysisPhase = new AnalysisPhase(timePeriodApplication.get(4 + adding), timePeriodApplication.get(5 + adding));
//        System.out.println(timePeriodApplication.get(4+adding).getTime());
//        System.out.println(timePeriodApplication.get(5+adding).getTime());


        phasesDates.add(analysisPhase);

        phase = "\033[0m====Result Phase====\033[0m";
        do{
            temp.clear();
            temp.addAll(setTimePeriod(phase,  timePeriodApplication.get(5 + adding), false));
        } while (checkTimePeriodBetweenPhase(temp,timePeriodApplication));
        list.addAll(temp);

        timePeriodApplication.add(list.get(6+adding));
        timePeriodApplication.add(list.get(7+adding));

        ResultPhase resultPhase = new ResultPhase(timePeriodApplication.get(6 + adding), timePeriodApplication.get(7 + adding));
//        System.out.println(timePeriodApplication.get(6+adding).getTime());
//        System.out.println(timePeriodApplication.get(7+adding).getTime());


        phasesDates.add(resultPhase);

        return phasesDates;

    }

    private boolean checkTimePeriodBetweenPhase(List<Calendar> phases, List<Calendar> timePeriodApplication){
        if(timePeriodApplication.size() < 2){
            return false;
        }
        int max = phases.size()-1;
        int maxTime = timePeriodApplication.size()-1;

        if (timePeriodApplication.get(maxTime).before(phases.get(max-1)) || timePeriodApplication.get(maxTime).equals(phases.get(max-1)) ){
//            System.out.println(timePeriodApplication.get(maxTime).getTime());
//            System.out.println(phases.get(max-1).getTime());
            return false;
        }
        System.out.println("\nThe phases must be sequential!!");
        return true;

    }

    private List<Calendar> setTimePeriod(String message, Calendar beginDate, boolean isApplicationPhase) {
        System.out.println("\n" + message);

        Scanner scanner = new Scanner(System.in);
        List<Calendar> list = new ArrayList<>();
        Calendar endDate;

        while (true) {
            if (!isApplicationPhase) {
                System.out.println("Begin Date (automatically set to end date of previous phase): " + new SimpleDateFormat("yyyy-MM-dd").format(beginDate.getTime()));

            } else {
                beginDate = getDateFromUser("Begin Date (yyyy-MM-dd): ", scanner);
            }

            endDate = getDateFromUser("End Date (yyyy-MM-dd): ", scanner);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            if (beginDate.after(endDate)) {
                System.out.println("\nError: Begin date cannot be after end date.");
            } else if (beginDate.before(today) || endDate.before(today)) {
                System.out.println("\nError: Dates must be today or in the future.");
            } else {
                list.add(beginDate);
                list.add(endDate);
                return list;
            }
        }
    }

    private Calendar getDateFromUser(String prompt, Scanner scanner) {
        Calendar date = null;
        while (date == null) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(input);
                date = Calendar.getInstance();
                date.setTime(parsedDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
        return date;
    }


    private JobOpening selectJobOpening(Iterable<JobOpening> jobOpenings) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("\nChoose a job opening:");
        int index = 1;
        for (JobOpening jobOpening: jobOpenings) {
            System.out.println(index + ": Job Reference - " + jobOpening.jobReference().toString() + "  Customer email - " + jobOpening.companyName().user().email());
            index++;
        }


        int choice;
        do {
            System.out.print("Job Opening: ");
            choice = scanner.nextInt();
            if (choice < 1 || choice > index - 1) {
                System.out.println("Invalid option. Please choose a number within the valid range.");
            }
        } while (choice < 1 || choice > index - 1);


        index = 1;
        for (JobOpening jobOpening : jobOpenings) {
            if (index == choice) {
                return jobOpening;
            }
            index++;
        }

        return null;
    }



    @Override
    public String headline() {
        return "Set up phases for Job Opening";
    }
}
