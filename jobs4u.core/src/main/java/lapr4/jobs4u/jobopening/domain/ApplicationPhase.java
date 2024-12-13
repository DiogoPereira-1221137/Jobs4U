package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Calendar;

/**
 * The type Application phase.
 */
@Entity
public class ApplicationPhase extends Phase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Instantiates a new Application phase.
     *
     * @param startDate the start date
     * @param endDate the end date
     */
    public ApplicationPhase(Calendar startDate, Calendar endDate) {
        super(startDate, endDate, PhaseNames.APPLICATION);
    }

    /**
     * Instantiates a new Application phase.
     */
    protected ApplicationPhase() {

    }


    @Override
    public int compareTo(Phase o) {
        return 0;
    }
}
