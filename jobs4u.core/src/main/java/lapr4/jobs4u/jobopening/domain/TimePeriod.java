package lapr4.jobs4u.jobopening.domain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The type Time period.
 */
@Embeddable
public class TimePeriod implements Serializable {
    private Calendar startDate= null;
    private Calendar endDate= null;


    /**
     * Instantiates a new Time period.
     *
     * @param startDate the start date
     * @param endDate   the end date
     */
    public TimePeriod(Calendar startDate, Calendar endDate) {
        this.startDate= startDate;
        this.endDate= endDate;
    }

    /**
     * Instantiates a new Time period.
     */
    protected TimePeriod() {

    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar startDate() {
        return startDate;
    }

    public Calendar endDate() {
        return endDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = startDate != null ? dateFormat.format(startDate.getTime()) : "null";
        String endDateString = endDate != null ? dateFormat.format(endDate.getTime()) : "null";
        return "begin date = " + startDateString +
                ", end date = " + endDateString;
    }
}

