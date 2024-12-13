package lapr4.jobs4u.interview.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.interview.domain.Interview;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.rank.domain.Rank;

import java.util.Calendar;
import java.util.Optional;

public interface InterviewRepository extends DomainRepository<Id, Interview> {
    Iterable<Interview> interviews();

    Iterable<Interview> findEqual(JobOpening jobOpening, Calendar date, Calendar hour);

    Iterable<Interview> findInterviewsByJobApplication(Id jobApplication);

}
