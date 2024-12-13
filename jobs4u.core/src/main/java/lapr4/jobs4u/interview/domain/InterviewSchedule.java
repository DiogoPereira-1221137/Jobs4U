package lapr4.jobs4u.interview.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lapr4.jobs4u.jobApplication.domain.Id;

import java.util.Calendar;

@Embeddable
public class InterviewSchedule  {
    @Column(name = "interview_date")
    private Calendar date;

    @Column(name = "interview_hour")
    private Calendar hour;


    public InterviewSchedule(Calendar date, Calendar hour){
        this.date=date;
        this.hour=hour;

    }

    protected InterviewSchedule() {

    }
    public Calendar date() {
        return date;
    }

    public Calendar hour() {
        return hour;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setHour(Calendar hour) {
        this.hour = hour;
    }
}
