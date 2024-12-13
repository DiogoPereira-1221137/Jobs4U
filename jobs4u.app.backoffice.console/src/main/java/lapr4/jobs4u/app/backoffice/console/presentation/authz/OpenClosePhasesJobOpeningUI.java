package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.jobopening.application.OpenClosePhasesJobOpeningController;
import lapr4.jobs4u.jobopening.domain.*;
import org.aspectj.weaver.IEclipseSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

/**
 * The type Register job opening ui.
 */
public class OpenClosePhasesJobOpeningUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenClosePhasesJobOpeningUI.class);

    private final OpenClosePhasesJobOpeningController controller = new OpenClosePhasesJobOpeningController();




    @Override
    protected boolean doShow() {

        try{
            Iterable<JobOpening> listJobOpennings = controller.allJobOpeningsByCustomerManager();

            JobOpening jobOpening = selectJobOpening(listJobOpennings);

            if(jobOpening != null){
                PhaseNames phase = controller.currentPhase(jobOpening);

                if(phase == null){
                    System.out.println("\nThis job opening has not yet started the recruitment process!");

                    boolean answer = selectOpenApplication();
                    if(answer) {
                        JobOpening jobOpeningUpdated =controller.processOperation(1, jobOpening, PhaseNames.APPLICATION);

                        System.out.println("\n==========Job Opening Updated Successfully!==========");
                        System.out.println("\033[1mJob Reference:\033[0m " + jobOpeningUpdated.jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpeningUpdated.numberOfVacancies() + "\n\033[1mJob Function:\033[0m "+ jobOpeningUpdated.jobFunction() + "\n\033[1mMode:\033[0m " + jobOpeningUpdated.mode() + "\n\033[1mContract Type:\033[0m "
                                + jobOpeningUpdated.contractType() +  "\n" + jobOpeningUpdated.address() + "\n\033[1mCustomer (Company Name):\033[0m " + jobOpeningUpdated.companyName().user().email() + "\n\033[1mDescription:\033[0m" + jobOpeningUpdated.description()
                                + "\n\033[1mRegistration Date:\033[0m " + CurrentTimeCalendars.now().getTime() + "\n\033[0mRecrutment Processs:\033[0m" + jobOpeningUpdated.recruitmentProcess().toString() + "\n\033[1mCurrent Phase:\033[0m" + jobOpeningUpdated.currentPhase().toString());


                    }
                } else{
                    System.out.println("This job opening is currrently in the phase: " + phase.toString());

                    int choice = chooseCloseOpenPhase(phase);
                    JobOpening jobOpeningUpdated = controller.processOperation(choice, jobOpening, phase);

                    System.out.println("\n==========Job Opening Updated Successfully!==========");
                    System.out.println("\033[1mJob Reference:\033[0m " + jobOpeningUpdated.jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpeningUpdated.numberOfVacancies() + "\n\033[1mJob Function:\033[0m "+ jobOpeningUpdated.jobFunction() + "\n\033[1mMode:\033[0m " + jobOpeningUpdated.mode() + "\n\033[1mContract Type:\033[0m "
                            + jobOpeningUpdated.contractType() +  "\n" + jobOpeningUpdated.address() + "\n\033[1mCustomer (Company Name):\033[0m " + jobOpeningUpdated.companyName().user().email() + "\n\033[1mDescription:\033[0m" + jobOpeningUpdated.description()
                            + "\n\033[1mRegistration Date:\033[0m " + CurrentTimeCalendars.now().getTime() + "\n\033[0mRecrutment Processs:\033[0m" + jobOpeningUpdated.recruitmentProcess().toString() + "\n\033[1mCurrent Phase:\033[0m" + jobOpeningUpdated.currentPhase().toString());

                }

               // System.out.println("\n==========Job Opening Registered Successfully!==========");

            }


        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
    }

        return true;
    }

    private boolean selectOpenApplication() {
        while (true) {
            String hasInterview = Console.readLine("\nDo you wish to open the phase Applcaiton? (Y/N)");
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

    private int chooseCloseOpenPhase(PhaseNames phase) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nChose an option:");
        System.out.println("1. Open phase: "+ phase.toString());
        System.out.println("2. Close " +  phase.toString() + " phase and open next phase");
        if(!phase.equals(PhaseNames.APPLICATION)){
            System.out.println("3. Backtrack from " + phase.toString() + " phase ");
        }

        int choice;
        do{
            System.out.println("\nOption:");
            choice = scanner.nextInt();
        }while(choice < 1 || choice > 3);

        return choice;
    }


    private JobOpening selectJobOpening(Iterable<JobOpening> jobOpenings) {
        Scanner scanner = new Scanner(System.in);

        if(!jobOpenings.iterator().hasNext()){
            System.out.println("There aren't active job openings");
            return null;
        }


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
        return "Open or close Phase Job Opening";
    }
}
