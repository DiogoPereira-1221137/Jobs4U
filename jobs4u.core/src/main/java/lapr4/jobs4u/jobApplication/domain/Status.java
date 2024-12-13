package lapr4.jobs4u.jobApplication.domain;


import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;


import java.io.Serializable;

/**
 * The type Status.
 */
@Embeddable
public class Status implements ValueObject, Serializable {

    private  String StatusDescription;

    /**
     * Instantiates a new Status.
     *
     * @param description the description
     */
    public Status(String description) {
        if (description.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.StatusDescription = description;
    }

    /**
     * Instantiates a new Status.
     */
    protected Status() {

    }

    public String toString() {
        return this.StatusDescription;
    }

    /**
     * Value of status.
     *
     * @param description the description
     * @return the status
     */
    public static Status valueOf(final String description) {
        return new Status(description);
    }
}
