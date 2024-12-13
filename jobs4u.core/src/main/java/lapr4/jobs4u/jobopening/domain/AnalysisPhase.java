package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Calendar;

/**
 * The type Analysis phase.
 */
@Entity
public class AnalysisPhase extends Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Instantiates a new Analysis phase.
     *
     * @param startDate the start date
     * @param endDate   the end date
     */
    public AnalysisPhase(Calendar startDate, Calendar endDate) {
        super(startDate, endDate,PhaseNames.ANALYSIS);
    }

    /**
     * Instantiates a new Analysis phase.
     */
    protected AnalysisPhase() {

    }

    @Override
    public int compareTo(Phase o) {
        return 0;
    }


}
