package lapr4.jobs4u.app.candidate.console.presentation.myuser;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.candidate.console.application.DisplayStateJobApplicationController;
import lapr4.jobs4u.app.candidate.console.application.NotifCandidateController;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.JobApplication;

import lapr4.jobs4u.jobApplication.domain.JobApplicationStateDTO;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * The type List job opening ui.
 */
public class DisplayStateJobApplicationUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayStateJobApplicationUI.class);

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final CandidateRepository candidateRepository = PersistenceContext
            .repositories().candidates();

    private final DisplayStateJobApplicationController controller;
    private static Socket socket;

    public DisplayStateJobApplicationUI(Socket socket) throws IOException {
        DisplayStateJobApplicationUI.socket = socket;
        controller = new DisplayStateJobApplicationController(socket);
    }


    @Override
    protected boolean doShow() {
        Scanner scanner = new Scanner(System.in);

        try {
          /// Optional<Candidate> candidate = candidateRepository.findByEmail(authz.session().get().authenticatedUser().email());

            CustomMessage message =  controller.listAllApplications();

            List<JobApplicationStateDTO> jobApplications = SerializeUtil.deserializeList(message.data.get(0));

            for (JobApplicationStateDTO jobApplication : jobApplications) {
                System.out.println("\n\033[1m --> ID\033[0m "+ jobApplication.id());
                System.out.println("\033[1mStatus:\033[0m "+ jobApplication.status());
                System.out.println("\033[1mCandidates number:\033[0m " +  jobApplication.getNumberCandidates());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    @Override
    public String headline() {
        return "List Job Applications";
    }
}
