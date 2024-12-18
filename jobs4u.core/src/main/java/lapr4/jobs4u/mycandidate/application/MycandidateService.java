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
package lapr4.jobs4u.mycandidate.application;

import java.util.Optional;

import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.usermanagement.domain.Roles;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * The type Mycandidate service.
 *
 * @author Paulo Gandra de Sousa
 */
public class MycandidateService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CandidateRepository repo = PersistenceContext.repositories().candidates();

    /**
     * Me candidate.
     *
     * @return the candidate
     */
    public Candidate me() {
        final UserSession s = authz.session().orElseThrow(IllegalStateException::new);
        final SystemUser myUser = s.authenticatedUser();
        // TODO cache the client user object
        final Optional<Candidate> me = repo.findByEmail(myUser.email());
        return me.orElseThrow(IllegalStateException::new);
    }

    /**
     * My user candidate.
     *
     * @return the candidate
     */
    public Candidate myUser() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE);
        final UserSession s = authz.session().orElseThrow(IllegalStateException::new);
        final SystemUser me = s.authenticatedUser();
        return repo.findByEmail(me.email()).orElseThrow(IllegalStateException::new);
    }

}
