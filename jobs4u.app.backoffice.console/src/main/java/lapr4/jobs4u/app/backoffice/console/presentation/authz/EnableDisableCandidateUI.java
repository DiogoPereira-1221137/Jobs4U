package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.application.EnableDisableCandidateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

/**
 * The type Display candidate data ui.
 */
public class EnableDisableCandidateUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableCandidateUI.class);

    private final EnableDisableCandidateController controller = new EnableDisableCandidateController();
    @Override
    protected boolean doShow() {
        boolean status = true;
        int choice = -1;
        System.out.println("1 - Enable Candidate Access");
        System.out.println("2 - Disable Candidate Access");
        System.out.println("0 - Exit");
        while (choice < 0 || choice > 2) {
            System.out.print("Option: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            switch (choice) {
                case 1: status = enableCandidateUI(); break;
                case 2: status = disableCandidateUI(); break;
                case 0: break;
                default: System.out.println("Invalid Option"); break;
            }
        }
        return status;

    }

    private boolean disableCandidateUI() {
        Iterable<SystemUser> candidates = controller.allActiveCandidates();
        if (((List<SystemUser>) candidates).isEmpty()){
            System.out.println("There are no enabled Candidates");
            return true;
        }
        SystemUser user = displayAndSelectCandidate(candidates, "disable");
        boolean oldStatus = user.isActive();
        boolean success = controller.deactivateUser(user).isActive() !=oldStatus;
        if (success) {
            System.out.println("Candidate Access Disabled!");
        } else {
            System.out.println("Candidate Access could not be disabled...");
        }
        return success;
    }
    private boolean enableCandidateUI(){
        Iterable<SystemUser> candidates = controller.allNotActiveCandidates();
        if (((List<SystemUser>) candidates).isEmpty()){
            System.out.println("There are no disabled Candidates");
            return true;
        }
        SystemUser user = displayAndSelectCandidate(candidates, "enable");
        boolean oldStatus = user.isActive();
        boolean success = controller.activateUser(user).isActive() != oldStatus;
        if (success) {
            System.out.println("Candidate Access Enabled!");
        } else {
            System.out.println("Candidate Access could not be enabled...");
        }
        return success;
    }

    @Override
    public String headline() {
        return "Enable/Disable Candidate Access";
    }

    private static SystemUser displayAndSelectCandidate(Iterable<SystemUser> candidates, String text) {
        int choice = -1;
        int count = 1;
        System.out.println(text.equals("enable")?"List of Inactive Candidates":"List of Active Candidates");
        for (SystemUser candidate : candidates) {
            System.out.println(count + ". " + candidate.email());
            count++;

        }

        while (choice < 1 || choice > ((List<SystemUser>) candidates).size()) {
                System.out.print("Enter the number of the candidate you want to "+ text +":\n ");
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > ((List<SystemUser>) candidates).size()) {
                        System.out.println("Invalid choice! Please select a number from the list!");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number!");
                }
        }
        SystemUser user = ((List<SystemUser>) candidates).get(choice - 1);
        return user;
    }

}
