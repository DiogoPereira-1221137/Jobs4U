package lapr4.jobs4u.jobApplication.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DisplayJobApplicationDataController {
    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();


    public List<JobOpening> findAllJobOpenings(){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();
        Iterable<JobOpening> allJobOpenings =jobOpeningRepository.allActiveJobOpeningsByCustomerManager(currentUser);
        return new ArrayList<>((Collection<? extends JobOpening>) allJobOpenings);
    }

    public List<JobApplication> findJobApplicationsByJobOpening(JobOpening selectedJobOpening){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Iterable<JobApplication> jobApplicationsByJobOpening =jobApplicationRepository.findByJobOpening(selectedJobOpening);
        return new ArrayList<>((Collection<? extends JobApplication>) jobApplicationsByJobOpening);
    }


}
