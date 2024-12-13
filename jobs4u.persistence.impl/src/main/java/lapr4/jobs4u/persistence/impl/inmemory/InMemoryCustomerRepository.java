package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.customer.repositories.CustomerRepository;

import java.util.Optional;

public class InMemoryCustomerRepository
        extends InMemoryDomainRepository<Customer, Code>
        implements CustomerRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Customer> findByCode(final Code code) {
        return matchOne(e -> e.getId().equals(code));
    }

    @Override
    public Optional<Customer> findByEmail(final EmailAddress email) {
        return matchOne(e -> e.user().email().equals(email));
    }



    @Override
    public Iterable<Customer> findAllActive() {
        return match(e -> e.user().isActive());
    }

//    @Override
//    public Iterable<Customer> findByCustomerManager(final ) {
//        return matchOne(e -> e.getId().equals(code));
//    }
}
