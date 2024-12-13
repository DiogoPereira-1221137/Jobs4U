package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.infrastructure.authz.domain.model.Role;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * The type Mode.
 */
@Embeddable
public class Mode implements ValueObject, Serializable {
    private  String modeDescription;

    /**
     * Instantiates a new Mode.
     *
     * @param description the description
     */
    public Mode(String description) {
        if (description.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.modeDescription = description;
    }

    /**
     * Instantiates a new Mode.
     */
    protected Mode() {

    }

    /**
     * Mode description string.
     *
     * @return the string
     */
    public String modeDescription() {
        return modeDescription;
    }

    public String toString() {
        return this.modeDescription;
    }

    /**
     * Value of mode.
     *
     * @param description the description
     * @return the mode
     */
    public static Mode valueOf(final String description) {
        return new Mode(description);
    }
}
