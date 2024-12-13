package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.jobApplication.application.DisplayJobApplicationDataController;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.application.RegisterJobOpeningController;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class DisplayJobApplicationDataUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayJobApplicationDataUI.class);

    private final DisplayJobApplicationDataController controller = new DisplayJobApplicationDataController();

    @Override
    protected boolean doShow() {
        JobOpening selectedJobOpening = selectJobOpening();
        if(selectedJobOpening!=null) {
            JobApplication selectedJobApplication = selectJobApplication(selectedJobOpening);
            if(selectedJobApplication!=null) {
                displayJobApplicationData(selectedJobOpening, selectedJobApplication);
            }
        }
        return false;
    }

    private JobOpening selectJobOpening() {

        List<JobOpening> jobOpeningsWithInterview = controller.findAllJobOpenings();
        if (!jobOpeningsWithInterview.isEmpty()) {
            return displayJobOpeningMenu(jobOpeningsWithInterview);
        } else {
            System.out.println("No Job Openings available.");
            return null;
        }
    }

    private JobOpening displayJobOpeningMenu(List<JobOpening> jobOpenings) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a Job Opening:");

            for (int i = 0; i < jobOpenings.size(); i++) {
                System.out.println((i + 1) + ". " + "\033[1mJob Reference:\033[0m " + jobOpenings.get(i).jobReference().jobReference() + "\n   \033[1mNumber Of Vacancies:\033[0m " + jobOpenings.get(i).numberOfVacancies().toString() + "\n   \033[1mJob Function:\033[0m " + jobOpenings.get(i).jobFunction().toString() +
                        "\n   \033[1mMode:\033[0m " + jobOpenings.get(i).mode() + "\n   \033[1mContract Type:\033[0m " + jobOpenings.get(i).contractType() +  "\n   " + jobOpenings.get(i).address() + "\n   \033[1mCustomer (Company Name):\033[0m " + jobOpenings.get(i).companyName().user().email() +
                        "\n   \033[1mDescription:\033[0m " + jobOpenings.get(i).description().toString() + "\n   \033[1mRegistration Date:\033[0m " + jobOpenings.get(i).registrationDate().getTime() + "\n   \033[1mCurrent State:\033[0m " + jobOpenings.get(i).jobOpeningStatus() + "\n"  );
            }

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= jobOpenings.size()) {
                    return jobOpenings.get(choice - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }

    private JobApplication selectJobApplication(JobOpening selectedJobOpening) {

        List<JobApplication> jobApplications = controller.findJobApplicationsByJobOpening(selectedJobOpening);
        if (!jobApplications.isEmpty()) {
            return displayCandidateMenu(jobApplications);
        } else {
            System.out.println("No Job Applications available.");
            return null;
        }
    }

    private JobApplication displayCandidateMenu(List<JobApplication> jobApplications) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a Job Application:");

            for (int i = 0; i < jobApplications.size(); i++) {
                System.out.println((i + 1) + ". " + "\033[1mName:\033[0m " + jobApplications.get(i).candidate().user().name() + " \033[1mEmail:\033[0m " + jobApplications.get(i).candidate().user().email() );
            }

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= jobApplications.size()) {
                    return jobApplications.get(choice - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }


    public void displayJobApplicationData(JobOpening jobOpening,JobApplication jobApplication) {
        System.out.println("\n======JobApplication Data======");

        System.out.println("\n\033[1mId:\033[0m"+ jobApplication.identity().getId() +
                "\n\033[1mJob Opening:\033[0m\n" +  "\033[1mJob Reference:\033[0m " + jobOpening.jobReference().jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpening.numberOfVacancies().toString() + "\n\033[1mJob Function:\033[0m " + jobOpening.jobFunction().toString() +
                "\n\033[1mMode:\033[0m " + jobOpening.mode() + "\n\033[1mContract Type:\033[0m " + jobOpening.contractType() +  "\n" + jobOpening.address() + "\n\033[1mCustomer (Company Name):\033[0m " + jobOpening.companyName().user().email() +
                "\n\033[1mDescription:\033[0m " + jobOpening.description().toString() + "\n\033[1mRegistration Date:\033[0m " + jobOpening.registrationDate().getTime() + "\n\033[1mCurrent State:\033[0m " + jobOpening.jobOpeningStatus() + "\n\033[1mCurrent Phase:\033[0m"+ jobOpening.recruitmentProcess().currentPhase() +
                "\n\n\033[1mCandidate:\033[0m\n" +"\033[1mName:\033[0m "+ jobApplication.Candidate().user().name() + "\n\033[1mEmail:\033[0m " + jobApplication.Candidate().user().email() +
                "\n\n\033[1mFiles:\033[0m\n" + "\033[1mInfo File Paths:\033[0m"+ jobApplication.filePathsInfo() + "\n\033[1mRequirements File Paths:\033[0m"+ jobApplication.filePathsRequirements() + "\n\033[1mInterview File Paths:\033[0m" + jobApplication.filePathsInterview() +
                "\n\n\033[1mStatus:\033[0m"+ jobApplication.status()
        );


        if (jobApplication.grade() != null && jobApplication.grade().grade() != null) {
            System.out.println("\n\033[1mGrade:\033[0m" + jobApplication.grade().grade());
        } else {
            System.out.println("\n\033[1mGrade:\033[0m not evaluated");
        }
    }


    @Override
    public String headline() {
        return "Display the Data of a Job Application";
    }
}
