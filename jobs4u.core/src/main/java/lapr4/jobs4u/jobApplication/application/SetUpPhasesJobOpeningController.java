/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.jobApplication.application;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.Phase;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.jobopening.domain.RecruitmentProcess;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;


/**
 * The type Set up phases job opening controller.
 */
public class SetUpPhasesJobOpeningController {

    private PasswordPolicy policy;
    private PasswordEncoder encoder;

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();


    private final TransactionalContext txCtx = PersistenceContext.repositories().newTransactionalContext();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    /**
     * Get existing RoleTypes available to the user.
     *
     * @return a list of RoleTypes
     */
    public Role[] getRoleTypes() {
        return Roles.nonUserValues();
    }

    /**
     * Sets up phases job opening.
     *
     * @param jobOpening   the job opening
     * @param dates        the dates
     * @param hasInterview the has interview
     * @return the up phases job opening
     */
    public JobOpening  setUpPhasesJobOpening(JobOpening jobOpening, List<Phase> dates, boolean hasInterview) {

         try{

            authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);

             RecruitmentProcess recruitmentProcess = new RecruitmentProcess(hasInterview, dates);


            jobOpening.setRecruitmentProcess(recruitmentProcess);

            jobOpeningRepository.save(jobOpening);

            return jobOpening;
        }catch (Exception e){
             System.out.println(e);
            System.out.println("An error occurred in the setting up the phases for the job opening.");
        }
        return null;
    }

    /**
     * Job opening by customer manager list.
     *
     * @return the list
     */
    public List<JobOpening> jobOpeningByCustomerManager() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();

        List<JobOpening> filteredJobOpenings = new ArrayList<>();

        Iterable<JobOpening> allJobOpenings = jobOpeningRepository.findAll();

        for (JobOpening jobOpening : allJobOpenings) {

            SystemUser customerManagerUser = jobOpening.companyName().customerManagerAssociated();
            if (customerManagerUser != null && customerManagerUser.equals(currentUser)) {
                filteredJobOpenings.add(jobOpening);
            }
        }
        return filteredJobOpenings;

    }

}
