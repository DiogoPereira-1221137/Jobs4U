package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.application.ListAllCandidatesController;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Comparator;

/**
 * The type List all candidates ui.
 */
public class ListAllCandidatesUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListAllCandidatesUI.class);

    private final ListAllCandidatesController controller = new ListAllCandidatesController();

    @Override
    protected boolean doShow() {
        Iterable<Candidate> candidates = controller.allActiveCandidates();

        List<Candidate> candidatesList = new ArrayList<>();
        candidates.forEach(candidatesList::add);

        candidatesList.sort((c1, c2) -> c1.user().name().firstName().compareToIgnoreCase(c2.user().name().firstName()));

        int count=1;
        System.out.println("List of Candidates");
        if(candidatesList.isEmpty()){
            System.out.println("No active candidates registered in the system at moment!");
        }
        for (Candidate candidate: controller.allActiveCandidates()){
            System.out.println(count + " - " +candidate.user().name().firstName() + " " + candidate.user().name().lastName()+ " - " + candidate.getId());
            count++;
        }
        return false;
    }

    @Override
    public String headline() {
        return "List All Candidates";
    }
}
