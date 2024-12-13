package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.representations.dto.DTO;
import jakarta.persistence.*;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The type Job application.
 */
@DTO
@Data
//@AllArgsConstructor
public class JobApplicationDTO implements  Serializable {

    private Name candidateName;
    private EmailAddress candidateEmail;

    private Name companyName;

    private Id id;


    public JobApplicationDTO(Name candidateName, EmailAddress candidateEmail, Name companyName , Id id) {
        this.candidateName = candidateName;
        this.companyName = companyName;
        this.candidateEmail = candidateEmail;
        this.id = id;
    }


    public Name candidateName() {
        return candidateName;
    }

    public void changeCandidateName(Name candidateName) {
        this.candidateName = candidateName;
    }

    public Name companyName() {
        return companyName;
    }

    public void changeCompanyName(Name companyName) {
        this.companyName = companyName;
    }

    public Id identity() {
        return id;
    }

    public void changeId(Id id) {
        this.id = id;
    }

    public EmailAddress candidateEmail() {
        return candidateEmail;
    }

    public void changeCandidateEmail(EmailAddress candidateEmail) {
        this.candidateEmail = candidateEmail;
    }
}