package lapr4.jobs4u.jobopening.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.candidatemanagement.domain.PluginType;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The type Select interview model controller.
 */
@UseCaseController
public class SelectInterviewModelController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    private final PluginRepository repoPlugin = PersistenceContext.repositories().plugins();

    /**
     * All job openings iterable.
     *
     * @return the iterable
     */
    public Iterable<JobOpening> allJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        SystemUser currentUser= authz.session().get().authenticatedUser();

        List<JobOpening> filteredJobOpenings = new ArrayList<>();
        Iterable<JobOpening> allJobOpenings = this.repo.jobOpenings();
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
     * All interview plugin iterable.
     *
     * @return the iterable
     */
    public Iterable<Plugin> allInterviewPlugin() {

        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);



        return this.repoPlugin.findPluginByType(PluginType.INTERVIEW);


    }

    /**
     * Put interview model.
     *
     * @param jobOpening the job opening
     * @param plugin     the plugin
     */
    public void putInterviewModel(JobOpening jobOpening, Plugin plugin){

        jobOpening.putInterviewPlugin(plugin);
        this.repo.save(jobOpening);

    }

//    public List<JobOpening> filterJobOpeningsBySystemUser() {
//        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
//        SystemUser currentUser= authz.session().get().authenticatedUser();
//
//        List<JobOpening> filteredJobOpenings = new ArrayList<>();
//        Iterable<JobOpening> allJobOpenings = repo.findAll();
//        for (JobOpening jobOpening : allJobOpenings) {
//            Customer customer =jobOpening.companyName();
//            SystemUser customerUser = customer.customerManagerAssociated();
//            if (customerUser != null && customerUser.equals(currentUser)) {
//                filteredJobOpenings.add(jobOpening);
//            }
//        }
//        return filteredJobOpenings;
//
//
//    }

}
