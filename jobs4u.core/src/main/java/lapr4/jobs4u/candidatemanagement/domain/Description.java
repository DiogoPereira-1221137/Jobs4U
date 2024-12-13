package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;

/**
 * The type Description.
 */
@Embeddable
public class Description implements ValueObject, Comparable<Description> {
    @Lob
    private String description;

    /**
     * Instantiates a new Description.
     *
     * @param description the description
     */
    public Description(String description) {
        if (description.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.description = description;
    }

    /**
     * Instantiates a new Description.
     */
    protected Description() {

    }

    @Override
    public int compareTo(Description o) {
        return 0;
    }

    public String toString() {
        return this.description;
    }
}
