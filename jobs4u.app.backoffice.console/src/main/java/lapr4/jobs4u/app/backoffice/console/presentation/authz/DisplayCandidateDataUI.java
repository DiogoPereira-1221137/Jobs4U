package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.application.DisplayCandidateDataController;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.domain.Pair;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The type Display candidate data ui.
 */
public class DisplayCandidateDataUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayCandidateDataUI.class);

    private final DisplayCandidateDataController controller = new DisplayCandidateDataController();
    @Override
    protected boolean doShow() {



        Iterable<Candidate> candidates = controller.allActiveCandidates();

        List<Candidate> candidatesList = new ArrayList<>();
        candidates.forEach(candidatesList::add);

        candidatesList.sort((c1, c2) -> c1.user().name().firstName().compareToIgnoreCase(c2.user().name().firstName()));
        int count=1;
        System.out.println("List of Candidates");
        for (Candidate candidate: candidatesList){
            System.out.println(count + ". " +candidate.user().name().firstName() + " " + candidate.user().name().lastName()+ " - " + candidate.getId());
            count++;

        }

        int choice;
        do {
            System.out.print("Enter the number of the candidate you want to view details for:\n ");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > candidatesList.size()) {
                    System.out.println("Invalid choice! Please select a number from the list!");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid input! Please enter a number!");
            }
        } while (true);

        Candidate selectedCandidate = candidatesList.get(choice-1);
        displayCandidatePersonalData(selectedCandidate);
        Iterable<JobApplication> jA = controller.getApplications(selectedCandidate);
        displayApplications(jA);


        return false;

    }

    private void displayApplications(Iterable<JobApplication> applications) {
        int index = 1;
        for (JobApplication s : applications) {
            System.out.printf("\n\033[1mJob Application %d:\033[0m\n%s\n", index, s.toString());
            Pair<Map<String, Integer>,Map<String, List<String>>> mapPair = controller.mostFrequently(s);

            if (mapPair != null) {
                List<Map.Entry<String, Integer>> sortedWordCounts = new ArrayList<>(mapPair.getLeft().entrySet());
                sortedWordCounts.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));


                System.out.println("Top 20 words:");
                for (int i = 0; i < Math.min(20, sortedWordCounts.size()); i++) {
                    Map.Entry<String, Integer> entry = sortedWordCounts.get(i);
                    System.out.print(entry.getKey() + ": " + entry.getValue() + " [ ");
                    for (String st: mapPair.getRight().get(entry.getKey())) {
                        System.out.print(st+" ");
                    }
                    System.out.println("]");
                }
            } else {
                System.out.println("No files associated with this application.");
            }
            index++;
        }
    }

    /**
     * Display candidate personal data.
     *
     * @param candidate the candidate
     */
    public void displayCandidatePersonalData(Candidate candidate) {
        System.out.println("\n======Selected Candidate Personal Data======");
        System.out.println("\033[1mFist Name:\033[0m " + candidate.user().name().firstName() + "\n\033[1mLast Name:\033[0m " + candidate.user().name().lastName()
                + "\n\033[1mEmail:\033[0m " + candidate.user().email() + "\n\033[1mPhone Number:\033[0m " + candidate.phoneNumber());
    }


    @Override
    public String headline() {
        return "Display Candidate Personal Data";
    }
}
