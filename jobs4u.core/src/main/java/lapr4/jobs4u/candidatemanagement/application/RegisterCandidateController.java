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
package lapr4.jobs4u.candidatemanagement.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.time.util.CurrentTimeCalendars;

import lapr4.jobs4u.candidatemanagement.domain.events.RegisterCandidateEvent;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.usermanagement.domain.Roles;
import lapr4.jobs4u.usermanagement.domain.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Set;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class RegisterCandidateController {

    private PasswordPolicy policy;
    private PasswordEncoder encoder;

    private final UserRepository repo = PersistenceContext.repositories().users();
    private final CandidateRepository candidateRepository = PersistenceContext
            .repositories().candidates();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    private final EventPublisher dispatcher = InProcessPubSub.publisher();

    /**
     * Get existing RoleTypes available to the user.
     *
     * @return a list of RoleTypes
     */
    public Role[] getRoleTypes() {
        return Roles.nonUserValues();
    }

    /**
     * Register candidate system user.
     *
     * @param email       the email
     * @param password    the password
     * @param firstName   the first name
     * @param lastName    the last name
     * @param phoneNumber the phone number
     * @param roles       the roles
     * @return the system user
     */
    public SystemUser registerCandidate(final String email, final String password, final String firstName,
                                          final String lastName, final String phoneNumber, final Set<Role> roles) {
        SystemUser newUser = addUser(email, password, firstName, lastName,  roles, CurrentTimeCalendars.now());


        RegisterCandidateEvent event = new RegisterCandidateEvent(newUser, phoneNumber);
        dispatcher.publish(event);

        return newUser;

    }

    /**
     * Add user system user.
     *
     * @param email     the email
     * @param password  the password
     * @param firstName the first name
     * @param lastName  the last name
     * @param roles     the roles
     * @param createdOn the created on
     * @return the system user
     */
    public SystemUser addUser(final String email, final String password, final String firstName,
                              final String lastName, final Set<Role> roles, final Calendar createdOn) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.OPERATOR, Roles.ADMIN);


        return userSvc.registerNewUser(email, password, firstName, lastName, email, roles, createdOn);
    }


    /**
     * Generate password string.
     *
     * @return the string
     */
    public String generatePassword() {
        return PasswordGenerator.generatePassword();
    }
}
