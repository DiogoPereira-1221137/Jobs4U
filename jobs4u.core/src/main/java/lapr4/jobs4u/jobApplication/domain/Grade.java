package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Grade implements ValueObject, Serializable, Comparable<Grade> {
    private Integer grade;

    public Integer grade() {
        return grade;
    }


    public Grade(Integer grade) {
        this.grade = grade;
    }

    protected Grade( ) {

    }


    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public int compareTo(Grade o) {
        if (o == null || o.grade == null) {
            throw new IllegalArgumentException("The grade to compare cannot be null");
        }
        return this.grade.compareTo(o.grade);
    }
}
