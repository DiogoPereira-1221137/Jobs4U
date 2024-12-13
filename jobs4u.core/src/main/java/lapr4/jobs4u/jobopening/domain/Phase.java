package lapr4.jobs4u.jobopening.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Phase.
 */
@Entity
public abstract class Phase implements Comparable<Phase>, Serializable {

    @Embedded
    private TimePeriod timePeriod;

    @Column
    @Enumerated(EnumType.STRING)
    private PhaseNames phaseName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**
     * Instantiates a new Phase.
     *
     * @param startDate the beginning date
     * @param endDate  the end date
     */
    public Phase(Calendar startDate, Calendar endDate, PhaseNames phaseName) {
        this.timePeriod = new TimePeriod(startDate, endDate);
        this.phaseName=phaseName;
    }


    /**
     * Instantiates a new Phase.
     */
    protected Phase() {

    }

    public void timePeriod(Calendar beginDate, Calendar endDate) {
        this.timePeriod.setStartDate(beginDate);
        this.timePeriod.setEndDate(endDate);
    }

    public void endDate(Calendar endDate) {
        this.timePeriod.setEndDate(endDate);
    }


    public void startDate(Calendar beginDate) {
        this.timePeriod.setStartDate(beginDate);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long id() {
        return id;
    }

    public PhaseNames phaseName() {
        return phaseName;
    }

    public TimePeriod timePeriod() {
        return timePeriod;
    }

    @Override
    public String toString() {
        return timePeriod.toString();
    }
}
