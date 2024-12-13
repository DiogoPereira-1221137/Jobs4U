package lapr4.jobs4u.app.server.console.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The type Notification service.
 */
public class NotificationService {

    private static NotificationRepository notificationRepository =  PersistenceContext
            .repositories().notifications();

    private AuthorizationService authz = AuthzRegistry.authorizationService();


    private Notification transformFromDTO(final NotificationDTO notificationDTO){
        return notificationDTO.toObject();
    }

    private Iterable<NotificationDTO> transformToDTO(final Iterable<Notification> types) {
        return StreamSupport.stream(types.spliterator(), true)
                .map(Notification::toDTO)
                .collect(Collectors.toUnmodifiableList());
    }


    /**
     * Find by receiver email list.
     *
     * @param email the email
     * @return the list
     */
    public List<NotificationDTO> findByReceiverEmail(EmailAddress email){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE, Roles.CUSTOMER);

        return (List<NotificationDTO>) transformToDTO(notificationRepository.findByReceiverEmail(email));

    }


    /**
     * Mask as read rep notification.
     *
     * @param notificationDTO the notification dto
     * @return the notification
     */
    public Notification maskAsReadRep(NotificationDTO notificationDTO){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE, Roles.CUSTOMER);

        return transformFromDTO(notificationDTO);
    }
}
