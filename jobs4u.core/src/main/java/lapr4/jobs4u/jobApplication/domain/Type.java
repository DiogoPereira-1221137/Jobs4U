package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class Type implements ValueObject, Serializable {
    private static final long serialVersionUID = 1L;

    private String typeDescription;

    /**
     * Instantiates a new Type.
     *
     * @param description the description
     */
    public Type(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.typeDescription = description;
    }

    /**
     * Instantiates a new Type.
     * Protected constructor for ORM.
     */
    protected Type() {
        // ORM-only constructor
    }

    /**
     * Creates a new Type instance from a description.
     *
     * @param typeDescription the type description
     * @return the Type
     */
    public static Type valueOf(String typeDescription) {
        return new Type(typeDescription);
    }

    /**
     * Gets the type description.
     *
     * @return the type description
     */
    public String typeDescription() {
        return typeDescription;
    }

    @Override
    public String toString() {
        return this.typeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(typeDescription, type.typeDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeDescription);
    }
}
