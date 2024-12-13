package lapr4.jobs4u.jobopening.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.candidatemanagement.domain.*;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobopening.domain.*;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Register job opening controller.
 */
@UseCaseController
public class RegisterJobOpeningController {


    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    /**
     * Register job opening job opening.
     *
     * @param numberOfVacancies the number of vacancies
     * @param jobFunction       the job function
     * @param mode              the mode
     * @param contractType      the contract type
     * @param jobReference      the job reference
     * @param address           the address
     * @param companyName       the company name
     * @param description       the description
     * @return the job opening
     */
    public JobOpening registerJobOpening(final NumberOfVacancies numberOfVacancies, final JobFunction jobFunction, final Mode mode,
                                         final ContractType contractType, final JobReference jobReference, final Address address, final Customer companyName, final Description description) {

        JobOpening jobOpening = new JobOpening(numberOfVacancies,jobFunction, mode, contractType, address, companyName, description ,jobReference, CurrentTimeCalendars.now());


        return jobOpeningRepository.save(jobOpening);
    }

    /**
     * Filter customer by system user list.
     *
     * @return the list
     */
    public List<Customer> filterCustomerBySystemUser() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();


        List<Customer> filteredCustomers = new ArrayList<>();
        Iterable<Customer> allCustomers = customerRepository.findAll();
        for (Customer customer : allCustomers) {

            SystemUser customerUser = customer.customerManagerAssociated();
            if (customerUser != null && customerUser.equals(currentUser)) {
                filteredCustomers.add(customer);
            }
        }
        return filteredCustomers;


    }

    /**
     * Gets last job reference.
     *
     * @return the last job reference
     */
    public Integer getLastJobReference() {
        Iterable<JobOpening> jobOpenings = jobOpeningRepository.findAll();
        Integer maxJobReference=0;
        for (JobOpening jobOpening : jobOpenings) {
            maxJobReference=Math.max(maxJobReference, jobOpening.jobReference().jobReference());
        }
        return maxJobReference;
    }

    /**
     * Generate next job reference job reference.
     *
     * @param lastJobReference the last job reference
     * @return the job reference
     */
    public JobReference generateNextJobReference(Integer lastJobReference) {
        return new JobReference(lastJobReference + 1);
    }
}
