package lapr4.jobs4u.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import lapr4.jobs4u.Application;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.interview.domain.Interview;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobOpeningStatus;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.rank.domain.Rank;

import java.util.*;
import java.util.function.Consumer;

public class JpaInterviewRepository extends JpaAutoTxRepository<Interview, Id, Id>
        implements InterviewRepository {

    private final Map<Id, Interview> interviewMap = new HashMap<>();
    public JpaInterviewRepository(final TransactionalContext autoTx) {
        super(autoTx, "jobApplicationId");
    }

    public JpaInterviewRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                "jobApplicationId");
    }


    private void initializeMap() {
        List<Interview> interviews = entityManager().createQuery("SELECT j FROM Interview j", Interview.class).getResultList();
        for (Interview interview : interviews) {
            interviewMap.put(interview.jobApplicationId(), interview);
        }
    }
    @Override
    public Iterable<Interview> interviews() {
        initializeMap();
        return interviewMap.values();
    }

    @Override
    public Iterable<Interview> findEqual(JobOpening jobOpening, Calendar date, Calendar hour) {
        final Map<String, Object> params = new HashMap<>();
        params.put("jobOpening", jobOpening);
        params.put("date", date);
        params.put("hour", hour);
        return match("e.jobOpening = :jobOpening AND e.interviewSchedule.date = :date AND e.interviewSchedule.hour = :hour", params);
    }

    @Override
    public Iterable<Interview> findInterviewsByJobApplication(Id jobApplicationId) {
        final Map<String, Object> params = new HashMap<>();
        params.put("jobApplicationId", jobApplicationId);
        return match("e.jobApplicationId = :jobApplicationId", params);
    }
}
