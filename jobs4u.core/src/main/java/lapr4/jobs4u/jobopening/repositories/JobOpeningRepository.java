package lapr4.jobs4u.jobopening.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Optional;

/**
 * The interface Job opening repository.
 */
public interface JobOpeningRepository extends DomainRepository<JobReference, JobOpening> {

    /**
     * Find by customer optional.
     *
     * @param companyName the company name
     * @return the optional
     */
    Optional<JobOpening> findByCustomer(Customer companyName);
    Iterable<JobOpening> findAllByCustomer(Customer companyName);


    /**
     * Find by customer manager.
     *
     * @param customerManager the customer manager
     * @return the optional
     */
    Iterable<JobOpening> allActiveJobOpeningsByCustomerManager(SystemUser customerManager);

    /**
     * Find by customer manager.
     *
     * @param customerManager the customer manager
     * @return the optional
     */
    Iterable<JobOpening> allJobOpeningsByCustomerManager(SystemUser customerManager);


    /**
     * Job openings iterable.
     *
     * @return the iterable
     */
    public Iterable<JobOpening> jobOpenings();

    Iterable<JobOpening> findAllActiveJobOpeningsWithInterviewByCustomerManager(SystemUser customerManager);

    Iterable<JobOpening> findAllActiveJobOpeningsInInterviewPhaseByCustomerManager(SystemUser customerManager);

}
