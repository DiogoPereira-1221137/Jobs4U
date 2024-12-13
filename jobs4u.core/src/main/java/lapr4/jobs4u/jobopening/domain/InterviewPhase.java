package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Calendar;

/**
 * The type Interview phase.
 */
@Entity
public class InterviewPhase extends Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Instantiates a new Interview phase.
     *
     * @param startDate the start date
     * @param endDate the end date
     */
    public InterviewPhase(Calendar startDate, Calendar endDate) {
        super(startDate, endDate, PhaseNames.INTERVIEW);
    }

    /**
     * Instantiates a new Interview phase.
     */
    protected InterviewPhase() {

    }

    @Override
    public int compareTo(Phase o) {
        return 0;
    }
}
