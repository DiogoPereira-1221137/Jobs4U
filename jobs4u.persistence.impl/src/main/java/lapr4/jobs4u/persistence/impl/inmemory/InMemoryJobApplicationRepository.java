package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.StatusType;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryJobApplicationRepository extends InMemoryDomainRepository<JobApplication, Id>
        implements JobApplicationRepository {
    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<JobApplication> findByCandidate(EmailAddress email) {
        return match(e -> e.candidate().getId().equals(email));
    }
    @Override
    public Iterable<JobApplication> findByJobOpening(JobOpening opening) {
        return match(e -> e.jobOpening().equals(opening));
    }

    @Override
    public Iterable<JobApplication> findAcceptedApplicationsByJobOpening(JobOpening jobOpening) {
        return match(e -> e.status().equals(StatusType.APPROVED) && e.jobOpening().equals(jobOpening));
    }

    @Override
    public Iterable<JobApplication> findWaitingApplicationsByJobOpening(JobOpening jobOpening) {
        return match(e -> e.status().equals(StatusType.WAITING) && e.jobOpening().equals(jobOpening));
    }

    @Override
    public Iterable<JobApplication> findAcceptedApplicationsWithAnswersFileByJobOpening(JobOpening jobOpening) {
        return match(e -> e.status().equals(StatusType.APPROVED) && e.hasInterviewFile() && e.jobOpening().equals(jobOpening));
    }
    @Override
    public boolean findByEmailAndJobReference(EmailAddress candidateEmail, JobOpening opening) {
        return match(e -> e.candidate().getId().equals(candidateEmail) && e.jobOpening().equals(opening)).iterator().hasNext();
    }

}
