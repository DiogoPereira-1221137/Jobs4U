package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.repository.NotificationRepository;

import java.util.Calendar;
import java.util.Optional;

public class InMemoryNotificationRepository
        extends InMemoryDomainRepository<Notification, Long>
        implements NotificationRepository {

    static {
        InMemoryInitializer.init();
    }


    @Override
    public Optional<Notification> findBySendEmail(final EmailAddress email) {
        return matchOne(e -> e.senderEmail().equals(email));
    }


    @Override
    public Iterable<Notification> findByReceiverEmail(final EmailAddress email) {
//        return matchOne(e -> e.receiverEmail().equals(email));
        return match(e -> e.receiverEmail().equals(email));
    }
    @Override
    public Integer unReadCountByReceiverEmaill(final EmailAddress email) {
        int count = 0;
        for (Notification n : match(e -> e.receiverEmail().equals(email) && !e.status())) {
            count++;
        }
        return count;
    }


}
