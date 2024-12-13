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
package lapr4.jobs4u.jobApplication.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;

import java.util.List;
import java.util.Optional;


/**
 * The interface Job application repository.
 */
public interface JobApplicationRepository
        extends DomainRepository<Id, JobApplication>{

    /**
     * Find by candidate iterable.
     *
     * @param email the email
     * @return the iterable
     */
    Iterable<JobApplication> findByCandidate(EmailAddress email);

    /**
     * Find by job opening iterable.
     *
     * @param opening the opening
     * @return the iterable
     */
    Iterable<JobApplication> findByJobOpening(JobOpening opening);

    Iterable<JobApplication> findAcceptedApplicationsByJobOpening(JobOpening jobOpening);

    Iterable<JobApplication> findWaitingApplicationsByJobOpening(JobOpening jobOpening);

    Iterable<JobApplication> findAcceptedApplicationsWithAnswersFileByJobOpening(JobOpening jobOpening);


    boolean findByEmailAndJobReference(EmailAddress candidateEmail, JobOpening opening);

}
