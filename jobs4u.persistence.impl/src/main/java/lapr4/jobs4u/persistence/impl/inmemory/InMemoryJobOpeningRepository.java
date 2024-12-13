package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;

import java.util.*;

public class InMemoryJobOpeningRepository extends InMemoryDomainRepository<JobOpening, JobReference>
        implements JobOpeningRepository {
    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<JobOpening> findByCustomer(final Customer companyName) {
        return matchOne(e -> e.companyName().equals(companyName));
    }

    @Override
    public Iterable<JobOpening> findAllByCustomer(final Customer companyName) {
        return match(e -> e.companyName().equals(companyName));
    }


    @Override
    public Iterable<JobOpening> allActiveJobOpeningsByCustomerManager(SystemUser customerManager) {
        return match(e -> e.companyName().customerManagerAssociated().equals(customerManager) && e.isActive());
    }

    @Override
    public Iterable<JobOpening> allJobOpeningsByCustomerManager(SystemUser customerManager) {
        return match(e -> e.companyName().customerManagerAssociated().equals(customerManager));
    }

    @Override
    public Iterable<JobOpening> jobOpenings() {
        return new ArrayList<>(data().values());
    }

    @Override
    public Iterable<JobOpening> findAllActiveJobOpeningsWithInterviewByCustomerManager(SystemUser customerManager) {

        return match(e -> e.companyName().customerManagerAssociated().equals(customerManager) && e.isActive() && (e.recruitmentProcess().currentPhase().equals(PhaseNames.SCREENING) || e.recruitmentProcess().currentPhase().equals(PhaseNames.INTERVIEW)) && e.recruitmentProcess().hasInterview());
    }

    @Override
    public Iterable<JobOpening> findAllActiveJobOpeningsInInterviewPhaseByCustomerManager(SystemUser customerManager) {

        return match(e -> e.companyName().customerManagerAssociated().equals(customerManager) && e.isActive() && e.recruitmentProcess().currentPhase().equals(PhaseNames.INTERVIEW) && e.recruitmentProcess().hasInterview());
    }
}
