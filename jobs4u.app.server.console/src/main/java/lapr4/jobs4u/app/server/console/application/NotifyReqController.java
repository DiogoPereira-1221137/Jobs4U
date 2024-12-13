package lapr4.jobs4u.app.server.console.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotifyReqController {

    private static NotificationRepository notificationRepository =  PersistenceContext
            .repositories().notifications();

    private AuthorizationService authz = AuthzRegistry.authorizationService();

    private final NotificationService svc = new NotificationService();

    private static Socket socket;


    public NotifyReqController(Socket socket) {
        NotifyReqController.socket = socket;
    }


    public List<NotificationDTO> nofityRep(EmailAddress email){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE, Roles.CUSTOMER);

       return svc.findByReceiverEmail(email);
    }



    public Notification maskAsReadRep(NotificationDTO notificationDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE, Roles.CUSTOMER);

        return svc.maskAsReadRep(notificationDTO);
    }
}
