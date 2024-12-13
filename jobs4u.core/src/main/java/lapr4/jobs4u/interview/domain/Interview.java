package lapr4.jobs4u.interview.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;

import java.util.Calendar;

@Entity
public class Interview implements AggregateRoot<Id> {
    @Embedded
    private InterviewSchedule interviewSchedule;

    @ManyToOne
    private JobOpening jobOpening;

    @EmbeddedId
    private Id jobApplicationId;

    public Interview(Id jobApplicationId, JobOpening jobOpening, Calendar date, Calendar hour) {
        this.jobApplicationId=jobApplicationId;
        this.jobOpening=jobOpening;
        this.interviewSchedule= new InterviewSchedule(date, hour);
    }

    public Interview(){

    }

    public InterviewSchedule interviewSchedule(){
        return interviewSchedule;
    }

    public Id jobApplicationId() {
        return jobApplicationId;
    }

    public JobOpening jobOpening() {
        return jobOpening;
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }

    @Override
    public Id identity() {
        return jobApplicationId;
    }


}
