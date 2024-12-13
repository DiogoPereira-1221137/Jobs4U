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
package lapr4.jobs4u.infrastructure.persistence;

import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.rank.repositories.RankRepository;

/**
 * The interface Repository factory.
 *
 * @author Paulo Gandra Sousa
 */
public interface RepositoryFactory {


    /**
     * factory method to create a transactional context to use in the repositories
     *
     * @return transactional context
     */
    TransactionalContext newTransactionalContext();

    /**
     * Users user repository.
     *
     * @param autoTx the transactional context to enrol
     * @return user repository
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return user repository
     */
    UserRepository users();

    /**
     * Candidates candidate repository.
     *
     * @param autoTx the transactional context to enroll
     * @return candidate repository
     */
    CandidateRepository candidates(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return candidate repository
     */
    CandidateRepository candidates();

    /**
     * Customers customer repository.
     *
     * @param autoTx the transactional context to enroll
     * @return customer repository
     */
    CustomerRepository customers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return customer repository
     */
    CustomerRepository customers();

    /**
     * Ranks rank repository.
     *
     * @param autoTx the transactional context to enroll
     * @return rank repository
     */
    RankRepository ranks(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return rank repository
     */
    RankRepository ranks();

    /**
     * notifications notification repository.
     *
     * @param autoTx the transactional context to enroll
     * @return notification repository
     */
    NotificationRepository notifications(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return notification repository
     */
    NotificationRepository notifications();

    /**
     * Plugins plugin repository.
     *
     * @param autoTx the auto tx
     * @return the plugin repository
     */
    PluginRepository plugins(TransactionalContext autoTx);

    /**
     * Plugins plugin repository.
     *
     * @return the plugin repository
     */
    PluginRepository plugins();

    /**
     * Job openings job opening repository.
     *
     * @param autoTx the auto tx
     * @return the job opening repository
     */
    JobOpeningRepository jobOpenings(TransactionalContext autoTx);

    /**
     * Job openings job opening repository.
     *
     * @return the job opening repository
     */
    JobOpeningRepository jobOpenings();

    /**
     * Job applications job application repository.
     *
     * @param autoTx the auto tx
     * @return the job application repository
     */
    JobApplicationRepository jobApplications(TransactionalContext autoTx);


    /**
     * Job applications job application repository.
     *
     * @return the job application repository
     */
    JobApplicationRepository jobApplications();


    /**
     * Ranks rank repository.
     *
     * @param autoTx the transactional context to enroll
     * @return rank repository
     */
    InterviewRepository interviews(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return rank repository
     */
    InterviewRepository interviews();

}
