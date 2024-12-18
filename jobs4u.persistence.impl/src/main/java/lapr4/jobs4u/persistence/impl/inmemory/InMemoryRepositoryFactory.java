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
package lapr4.jobs4u.persistence.impl.inmemory;

import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.bootstrapers.ExemploBootstrapper;
import lapr4.jobs4u.infrastructure.persistence.RepositoryFactory;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.inmemory.InMemoryUserRepository;
import lapr4.jobs4u.rank.repositories.RankRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new ExemploBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public CandidateRepository candidates(final TransactionalContext tx) {

        return new InMemoryCandidateRepository();
    }

    @Override
    public CandidateRepository candidates() {
        return candidates(null);
    }

    @Override
    public CustomerRepository customers(final TransactionalContext tx) {

        return new InMemoryCustomerRepository();
    }

    @Override
    public CustomerRepository customers() {
        return customers(null);
    }

    @Override
    public RankRepository ranks(final TransactionalContext tx) {

        return new InMemoryRankRepository();
    }

    @Override
    public RankRepository ranks() {
        return ranks(null);
    }


    @Override
    public JobOpeningRepository jobOpenings() {
        return jobOpenings(null);
    }

    @Override
    public JobOpeningRepository jobOpenings(final TransactionalContext tx) {

        return new InMemoryJobOpeningRepository();
    }

    @Override
    public NotificationRepository notifications() {
        return notifications(null);
    }

    @Override
    public NotificationRepository notifications(final TransactionalContext tx) {

        return new InMemoryNotificationRepository();
    }

    @Override
    public PluginRepository plugins(final TransactionalContext tx) {

        return new InMemoryPluginRepository();
    }

    @Override
    public PluginRepository plugins() {
        return plugins(null);
    }

    @Override
    public JobApplicationRepository jobApplications(TransactionalContext tx) {
        return new InMemoryJobApplicationRepository();
    }

    @Override
    public JobApplicationRepository jobApplications() {
        return jobApplications(null);
    }



    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }


    @Override
    public InterviewRepository interviews() {
        return interviews(null);
    }

    @Override
    public InterviewRepository interviews(final TransactionalContext tx) {

        return new InMemoryInterviewRepository();
    }

}
