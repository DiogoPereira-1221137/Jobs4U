package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.rank.domain.Rank;
import lapr4.jobs4u.rank.repositories.RankRepository;

import java.util.Optional;

public class InMemoryRankRepository
        extends InMemoryDomainRepository<Rank, JobReference>
        implements RankRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Rank> findByJobReference(final JobReference jobReference) {
        return matchOne(e -> e.jobReference().equals(jobReference));
    }


}
