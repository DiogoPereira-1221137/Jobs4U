package lapr4.jobs4u.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.interview.domain.Interview;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.rank.domain.Rank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

public class InMemoryInterviewRepository extends InMemoryDomainRepository<Interview, Id>
        implements InterviewRepository {
    static {
        InMemoryInitializer.init();
    }


    @Override
    public Iterable<Interview> interviews() {
        return new ArrayList<>(data().values());
    }

    @Override
    public Iterable<Interview> findEqual(JobOpening jobOpening, Calendar date, Calendar hour) {
        return match(e -> e.jobOpening().equals(jobOpening) && e.interviewSchedule().date().equals(date) && e.interviewSchedule().hour().equals(hour));
    }

    @Override
    public Iterable<Interview> findInterviewsByJobApplication(Id jobApplication) {
        return match(e->e.jobApplicationId().equals(jobApplication));
    }
}
