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
package lapr4.jobs4u.candidatemanagement.repositories;

import java.util.Optional;

import eapli.framework.general.domain.model.EmailAddress;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;


/**
 * The interface Candidate repository.
 */
public interface CandidateRepository
        extends DomainRepository<EmailAddress, Candidate>{

    /**
     * returns the client user (candidate) whose username is given
     *
     * @param email the username to search for
     * @return optional
     */
    Optional<Candidate> findByEmail(EmailAddress email);

//    /**
//     * returns the client user (candidate) with the given mecanographic number
//     *
//     * @param number
//     * @return
//     */
//    default Optional<candidate> findByMecanographicNumber(final MecanographicNumber number) {
//        return ofIdentity(number);
//    }

    /**
     * Find all active iterable.
     *
     * @return the iterable
     */
    Iterable<Candidate> findAllActive();
    Iterable<Candidate> findAllNotActive();
}
