package lapr4.jobs4u.jobopening.domain;


import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Job reference.
 */
@Embeddable
public class JobReference implements ValueObject, Comparable<JobReference>, Serializable, StringMixin {
    private static final long serialVersionUID = 1L;

    private Integer jobReference;

    /**
     * Instantiates a new Job reference.
     */
    protected JobReference() {
    }

    /**
     * Instantiates a new Job reference.
     *
     * @param jobReference the job reference
     */
    public JobReference(Integer jobReference) {
        this.jobReference = jobReference;
    }

    /**
     * Job reference integer.
     *
     * @return the integer
     */
    public Integer jobReference() {
        return jobReference;
    }

    @Override
    public String toString() {
        return String.valueOf(jobReference);
    }

    @Override
    public int compareTo(JobReference o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobReference that = (JobReference) o;
        return Objects.equals(jobReference, that.jobReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobReference);
    }
}
