package lapr4.jobs4u.notification.repository;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import lapr4.jobs4u.notification.domain.Notification;

import java.util.Calendar;
import java.util.Optional;

public interface NotificationRepository extends DomainRepository<Long, Notification> {


    Optional<Notification> findBySendEmail(EmailAddress email);
    Iterable<Notification> findByReceiverEmail(EmailAddress email);
    Integer unReadCountByReceiverEmaill(EmailAddress email);


    }
