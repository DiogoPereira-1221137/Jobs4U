package lapr4.jobs4u.jobopening.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type List job opening controller.
 */
@UseCaseController
public class ListJobOpeningController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    /**
     * All job openings iterable.
     *
     * @return the iterable
     */
    public Iterable<JobOpening> allJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        return this.jobOpeningRepository.jobOpenings();
    }

    /**
     * All job opening registration dates list.
     *
     * @return the list
     * @throws ParseException the parse exception
     */
    public List<Date> allJobOpeningRegistrationDates() throws ParseException {
        List<Date> allJobOpeningRegistrationDates = new ArrayList<>();
        Iterable<JobOpening> allJobOpenings = filterJobOpeningsBySystemUser();
        for (JobOpening jobOpening : allJobOpenings) {
            Date jobOpeningRegistrationDate = jobOpening.registrationDate().getTime();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date dateformated = dateFormat.parse(dateFormat.format(jobOpeningRegistrationDate));

            if (jobOpeningRegistrationDate != null && !allJobOpeningRegistrationDates.contains(dateformated)) {
                allJobOpeningRegistrationDates.add(dateformated);
            }
        }
        return allJobOpeningRegistrationDates;
    }

    /**
     * Filter job openings by system user list.
     *
     * @return the list
     */
    public List<JobOpening> filterJobOpeningsBySystemUser() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();

        List<JobOpening> filteredJobOpenings = new ArrayList<>();
        Iterable<JobOpening> allJobOpenings = jobOpeningRepository.findAll();
        for (JobOpening jobOpening : allJobOpenings) {
            Customer customer =jobOpening.companyName();
            SystemUser customerUser = customer.customerManagerAssociated();
            if (customerUser != null && customerUser.equals(currentUser)) {
                filteredJobOpenings.add(jobOpening);
            }
        }
        return filteredJobOpenings;

    }

    /**
     * Filter customers by system user list.
     *
     * @return the list
     */
    public List<Customer> filterCustomersBySystemUser() {
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
     * Filter job openings by customer list.
     *
     * @param customer the customer
     * @return the list
     */
    public List<JobOpening> filterJobOpeningsByCustomer(Customer customer) {

        List<JobOpening> filteredJobOpenings = new ArrayList<>();
        Iterable<JobOpening> allJobOpenings = filterJobOpeningsBySystemUser();
        for (JobOpening jobOpening : allJobOpenings) {


            Customer jobOpeningCustomer = jobOpening.companyName();
//            jobOpeningCustomer.getId().toString().equals(customer.getId().toString())

            if (jobOpeningCustomer != null && jobOpeningCustomer.equals(customer) ) {
                filteredJobOpenings.add(jobOpening);
            }
        }
        return filteredJobOpenings;
    }

    /**
     * Filter job openings by registration date list.
     *
     * @param registrationDate the registration date
     * @return the list
     * @throws ParseException the parse exception
     */
    public List<JobOpening> filterJobOpeningsByRegistrationDate(Date registrationDate) throws ParseException {
        List<JobOpening> filteredJobOpenings = new ArrayList<>();
        Iterable<JobOpening> allJobOpenings = filterJobOpeningsBySystemUser();
        for (JobOpening jobOpening : allJobOpenings) {


            Date jobOpeningRegistrationDate = jobOpening.registrationDate().getTime();

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date dateformated = dateFormat.parse(dateFormat.format(jobOpeningRegistrationDate));

            if (dateformated != null && dateformated.equals(registrationDate)) {
                filteredJobOpenings.add(jobOpening);
            }
        }
        return filteredJobOpenings;
    }


}
