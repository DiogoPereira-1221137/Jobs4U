package lapr4.jobs4u.candidatemanagement.application;

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
import java.util.List;
import java.util.Optional;

/**
 * The type Select requirements controller.
 */
public class SelectRequirementsController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final PluginRepository pluginRepository = PersistenceContext.repositories().plugins();


    /**
     * All job openings iterable.
     *
     * @return the iterable
     */
    public Iterable<JobOpening> allJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        SystemUser currentUser= authz.session().get().authenticatedUser();

        List<JobOpening> filteredJobOpenings = new ArrayList<>();
        Iterable<JobOpening> allJobOpenings = this.jobOpeningRepository.jobOpenings();
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
     * All requirements iterable.
     *
     * @return the iterable
     */
    public Iterable<Plugin> allRequirements() {

        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        return this.pluginRepository.findPluginByType(PluginType.REQUIREMENTS);


    }

    /**
     * Put requirement.
     *
     * @param jobOpening the job opening
     * @param plugin     the plugin
     */
    public void putRequirement(JobOpening jobOpening, Plugin plugin){

        jobOpening.putRequirementsPlugin(plugin);
        this.jobOpeningRepository.save(jobOpening);

    }

}
