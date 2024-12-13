package lapr4.jobs4u.app.server.console.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.application.JobApplicationService;
import lapr4.jobs4u.jobApplication.domain.JobApplicationStateDTO;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.net.Socket;
import java.util.List;

public class ListAllApplicationsReqController {

    private static NotificationRepository notificationRepository =  PersistenceContext
            .repositories().notifications();

    private AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobApplicationService svc = new JobApplicationService();

    private static Socket socket;


    public ListAllApplicationsReqController(Socket socket) {
        ListAllApplicationsReqController.socket = socket;
    }


    public List<JobApplicationStateDTO> listAllApplications(EmailAddress email){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE);

       return svc.findJobApplicationsStateAndApplicants(email);
    }

}
