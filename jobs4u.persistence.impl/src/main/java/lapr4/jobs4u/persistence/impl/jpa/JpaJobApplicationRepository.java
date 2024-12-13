package lapr4.jobs4u.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import lapr4.jobs4u.Application;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.StatusType;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaJobApplicationRepository extends JpaAutoTxRepository<JobApplication, Id, Id>
        implements JobApplicationRepository {

    public JpaJobApplicationRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaJobApplicationRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "id");
    }

    @Override
    public Iterable<JobApplication> findByCandidate(EmailAddress email) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", email);
        return match("e.candidate.id=:name", params);
    }

    @Override
    public Iterable<JobApplication> findByJobOpening(JobOpening opening) {
        final Map<String, Object> params = new HashMap<>();
        params.put("opening", opening);
        return match("e.jobOpening=:opening", params);
    }

    @Override
    public Iterable<JobApplication> findAcceptedApplicationsByJobOpening(JobOpening jobOpening) {
        final Map<String, Object> params = new HashMap<>();
        params.put("status", StatusType.APPROVED);
        params.put("opening", jobOpening);
        return match("e.status=:status AND e.jobOpening=:opening", params);
    }
    @Override
    public Iterable<JobApplication> findWaitingApplicationsByJobOpening(JobOpening jobOpening) {
        final Map<String, Object> params = new HashMap<>();
        params.put("status", StatusType.WAITING);
        params.put("opening", jobOpening);
        return match("e.status=:status AND e.jobOpening=:opening", params);
    }

    @Override
    public Iterable<JobApplication> findAcceptedApplicationsWithAnswersFileByJobOpening(JobOpening jobOpening) {
        final Map<String, Object> params = new HashMap<>();
        params.put("status", StatusType.APPROVED);
        params.put("hasInterviewFile", true);
        params.put("opening", jobOpening);
        return match("e.status=:status AND e.jobOpening=:opening AND e.hasInterviewFile=true" , params);
    }
    @Override
    public boolean findByEmailAndJobReference(EmailAddress candidateEmail, JobOpening jobOpening) {
        final Map<String, Object> params = new HashMap<>();
        params.put("email", candidateEmail);
        params.put("opening", jobOpening);
        return !match("e.candidate.id=:email AND e.jobOpening=:opening", params).isEmpty();
    }


}
