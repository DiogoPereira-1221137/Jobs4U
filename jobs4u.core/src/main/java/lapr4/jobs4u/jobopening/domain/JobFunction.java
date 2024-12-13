package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

/**
 * The type Job function.
 */
@Embeddable
public class JobFunction implements ValueObject, Comparable<JobFunction> {
    private String jobDescription;

    /**
     * Instantiates a new Job function.
     *
     * @param description the description
     */
    public JobFunction(String description) {
        if (description.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.jobDescription = description;
    }

    /**
     * Instantiates a new Job function.
     */
    protected JobFunction() {
    }

    /**
     * Value of job function.
     *
     * @param description the description
     * @return the job function
     */
    public static JobFunction valueOf(final String description) {
        return new JobFunction(description);
    }

    @Override
    public int compareTo(JobFunction o) {
        return 0;
    }

    public String toString() {
        return this.jobDescription;
    }

}
