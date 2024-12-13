package lapr4.jobs4u.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lapr4.jobs4u.Application;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobOpeningStatus;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;

import java.util.*;


public class JpaJobOpeningRepository extends JpaAutoTxRepository<JobOpening, JobReference, JobReference>
        implements JobOpeningRepository {

    // Map to store job openings in memory
    private final Map<JobReference, JobOpening> jobOpeningsMap = new HashMap<>();
    @PersistenceContext
    private EntityManager entityManager;


    public JpaJobOpeningRepository(final TransactionalContext autoTx) {
        super(autoTx, "jobReference");
    }

    public JpaJobOpeningRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "jobReference");
    }

    @Override
    public Optional<JobOpening> findByCustomer(Customer companyName) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", companyName);
        return matchOne("e.companyName=:name", params);
    }


    @Override
    public Iterable<JobOpening> findAllByCustomer(Customer companyName) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", companyName);
        return match("e.companyName=:name", params);
    }


    @Override
    public List<JobOpening> allActiveJobOpeningsByCustomerManager(SystemUser customerManager) {
        final Map<String, Object> params = new HashMap<>();
        params.put("customerM", customerManager);
        params.put("jobOpeningStatus", JobOpeningStatus.ACTIVE);
        return match("e.companyName.customerManager = :customerM AND e.jobOpeningStatus = :jobOpeningStatus", params);
    }

    @Override
    public List<JobOpening> allJobOpeningsByCustomerManager(SystemUser customerManager) {
        final Map<String, Object> params = new HashMap<>();
        params.put("customerM", customerManager);
        return match("e.companyName.customerManager = :customerM", params);
    }

    private void initializeMap() {
        List<JobOpening> jobOpenings = entityManager().createQuery("SELECT j FROM JobOpening j", JobOpening.class).getResultList();
        for (JobOpening jobOpening : jobOpenings) {
            jobOpeningsMap.put(jobOpening.jobReference(), jobOpening);
        }
    }

    @Override
    public Iterable<JobOpening> jobOpenings() {
        initializeMap();
        return jobOpeningsMap.values();
    }

    @Override
    public Iterable<JobOpening> findAllActiveJobOpeningsWithInterviewByCustomerManager(SystemUser customerManager) {
        final Map<String, Object> params = new HashMap<>();
        params.put("customerManager", customerManager);
        params.put("jobOpeningStatus", JobOpeningStatus.ACTIVE);
        params.put("currentPhase", PhaseNames.SCREENING);
        params.put("currentPhase2", PhaseNames.INTERVIEW);
        params.put("hasInterview", true);

        return match("e.companyName.customerManager =:customerManager AND e.jobOpeningStatus = :jobOpeningStatus AND (e.recruitmentProcess.currentPhase =:currentPhase OR e.recruitmentProcess.currentPhase =: currentPhase2) AND e.recruitmentProcess.hasInterview = :hasInterview", params);

    }

    @Override
    public Iterable<JobOpening> findAllActiveJobOpeningsInInterviewPhaseByCustomerManager(SystemUser customerManager){
        final Map<String, Object> params = new HashMap<>();
        params.put("customerManager", customerManager);
        params.put("jobOpeningStatus", JobOpeningStatus.ACTIVE);
        params.put("currentPhase", PhaseNames.INTERVIEW);
        params.put("hasInterview", true);

        return match("e.companyName.customerManager =:customerManager AND e.jobOpeningStatus = :jobOpeningStatus AND e.recruitmentProcess.currentPhase =:currentPhase AND e.recruitmentProcess.hasInterview = :hasInterview", params);

    }

}
