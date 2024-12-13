package lapr4.jobs4u.rank.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.*;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobopening.domain.JobReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Code.
 */
@Entity
public class Rank implements ValueObject, AggregateRoot<JobReference>{

    @EmbeddedId
    private JobReference jobReference;
    @ElementCollection
    @CollectionTable(name = "rank_candidates", joinColumns = @JoinColumn(name = "job_reference"))

    private List<Id> candidatesRanking = new ArrayList<>();


    public Rank(JobReference jobReference, ArrayList<Id> candidatesRanking) {
        this.jobReference = jobReference;
        this.candidatesRanking = candidatesRanking;
    }

    public Rank() {}

    public JobReference jobReference() {
        return jobReference;
    }

    public void changeJobReference(JobReference jobReference) {
        this.jobReference = jobReference;
    }

    public List<Id> rankingList() {
        return candidatesRanking;
    }

    public void changeRankingList(ArrayList<Id> candidatesRanking) {
        this.candidatesRanking = candidatesRanking;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return Objects.equals(jobReference, rank.jobReference) && Objects.equals(candidatesRanking, rank.candidatesRanking);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobReference, candidatesRanking);
    }

    @Override
    public boolean sameAs(Object other) {
        return this.equals(other);
    }



    @Override
    public JobReference identity() {
        return jobReference;
    }

    @Override
    public int compareTo(JobReference other) {
        return AggregateRoot.super.compareTo(other);
    }




}
