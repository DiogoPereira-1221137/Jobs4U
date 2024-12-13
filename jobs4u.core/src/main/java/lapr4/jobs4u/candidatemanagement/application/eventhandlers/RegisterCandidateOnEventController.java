/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.candidatemanagement.application.eventhandlers;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import eapli.framework.functional.Functions;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import lapr4.jobs4u.candidatemanagement.domain.PhoneNumber;
import lapr4.jobs4u.candidatemanagement.domain.events.RegisterCandidateEvent;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;

import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;
import lapr4.jobs4u.usermanagement.domain.events.DeleteUserEvent;


/**
 * The type Register candidate on event controller.
 */
/* package */ class RegisterCandidateOnEventController {


    private final CandidateRepository candidateRepository = PersistenceContext
            .repositories().candidates();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    private final UserRepository repo = PersistenceContext.repositories().users();



    private final EventPublisher dispatcher = InProcessPubSub.publisher();


    /**
     * Register candidate candidate.
     *
     * @param event the event
     * @return the candidate
     */
    public Candidate registerCandidate(final RegisterCandidateEvent event) {

        SystemUser newUser = event.getTheRegisterRequest();

        try{
            PhoneNumber phoneNumberCandidate = event.PhoneNumber();

            Candidate candidate = new Candidate(newUser,phoneNumberCandidate);

            candidate.setId(EmailAddress.valueOf(event.email()));

            candidateRepository.save(candidate);

            return candidate;


        }catch(Exception ex){
            DeleteUserEvent error = new DeleteUserEvent(newUser);
            dispatcher.publish(error);
        }

    return null;
    }

//    @SuppressWarnings("squid:S1488")
//    private boolean findUser(final RegisterCandidateEvent event) {
//        final Optional<SystemUser> newUser = Functions
//                .retry(() -> repo.ofIdentity(event.getTheRegisterRequest().username()), 1000, 3);
//        return newUser.isPresent();
//    }

}
