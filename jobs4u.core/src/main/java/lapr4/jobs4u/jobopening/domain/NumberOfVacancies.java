package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;
import lapr4.jobs4u.candidatemanagement.domain.PhoneNumber;

/**
 * The type Number of vacancies.
 */
@Embeddable
public class NumberOfVacancies implements ValueObject, Comparable<NumberOfVacancies>{
    private Integer number;

    /**
     * Instantiates a new Number of vacancies.
     *
     * @param number the number
     */
    public NumberOfVacancies(Integer number) {
        if (number <= 0){
            throw new IllegalArgumentException();
        }
        this.number = number;
    }

    /**
     * Instantiates a new Number of vacancies.
     */
    protected NumberOfVacancies() {

    }


    @Override
    public int compareTo(NumberOfVacancies o) {
        return 0;
    }

    public String toString() {
        return String.valueOf(this.number);
    }

    public Integer vacancies() {
        return number;
    }
}
