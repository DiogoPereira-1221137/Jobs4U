package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.interview.domain.EvaluateInterviewsController;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class EvaluateInterviewsUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluateInterviewsUI.class);
    private final EvaluateInterviewsController controller = new EvaluateInterviewsController();
    @Override
    protected boolean doShow() {
        JobOpening selectedJobOpening = selectJobOpening();

        if(selectedJobOpening!=null) {
            Iterable<JobApplication> jobApplications = controller.findAllAcceptedJobApplicationsWithAnswersFileByJobOpening(selectedJobOpening);
            String message = "";

            if(jobApplications.iterator().hasNext()) {
                List<JobApplication> evaluatedJobApplications = new ArrayList<>();

                for (JobApplication jobApplication : jobApplications) {

                    List<ApplicationFile> interviewFilePaths = jobApplication.filePathsInterview();

                    if (!interviewFilePaths.isEmpty()) {
                        System.out.println("\n\033[1mJob Application Id\033[0m: " + jobApplication.identity());
                        ApplicationFile interviewFile = jobApplication.filePathsInterview().get(interviewFilePaths.size() - 1);
                        String interviewFilePath = interviewFile.filePath();

                        try {
                            message = this.controller.evaluateInterview(selectedJobOpening, jobApplication, interviewFilePath);
                            System.out.println(message);

                            evaluatedJobApplications.add(jobApplication);
                        } catch (IntegrityViolationException | ConcurrencyException e) {
                            LOGGER.error("Error performing the operation", e);
                            System.out.println("Unfortunately, there was an unexpected error in the application. Please try again, and if the problem persists, contact your system administrator.");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                }

                System.out.println("\n\033[1mEvaluated Job Applications and their Grade:\033[0m ");
                for (JobApplication evaluatedJobApplication : evaluatedJobApplications) {
                    System.out.println("\n\033[1mJob Application Id:\033[0m " + evaluatedJobApplication.identity() + " \n\033[1mGrade:\033[0m " + evaluatedJobApplication.grade().grade());

                }
            }else{
                System.out.println("No Job Applications available.");
            }
        }
        return false;
    }

    private JobOpening selectJobOpening() {

        List<JobOpening> jobOpenings = controller.findAllActiveJobOpeningsWithInterview();
        if (!jobOpenings.isEmpty()) {
            return displayJobOpeningMenu(jobOpenings);
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



    @Override
    public String headline() {
        return "Evaluate Interview For a Job Opening";
    }
}
