package lapr4.jobs4u.persistence.impl.inmemory;

import java.util.Optional;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryCandidateRepository
        extends InMemoryDomainRepository<Candidate, EmailAddress>
        implements CandidateRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Candidate> findByEmail(final EmailAddress email) {
        return matchOne(e -> e.user().email().equals(email));
    }



    @Override
    public Iterable<Candidate> findAllActive() {
        return match(e -> e.user().isActive());
    }
    public Iterable<Candidate> findAllNotActive() {
        return match(e -> !e.user().isActive());
    }
}
