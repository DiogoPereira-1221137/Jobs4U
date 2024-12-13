package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import lapr4.jobs4u.jobopening.domain.JobReference;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Id implements ValueObject, Comparable<Id>, Serializable {
    private static final long serialVersionUID = 1L;

    private static Integer numberID = 0;

    private String id ;

    public Id(JobReference jobReference) {
        this.id = jobReference.toString() + "_" + String.valueOf(generateNumber());
    }

    protected Id() {

    }

    @Override
    public String toString() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    @Override
    public int compareTo(Id o) {
        return this.id.compareTo(o.id);
    }

    public static Integer generateNumber() {
        numberID++;
        return numberID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
