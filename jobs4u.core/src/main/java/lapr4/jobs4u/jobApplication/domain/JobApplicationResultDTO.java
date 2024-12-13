package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;

import java.io.Serializable;

public class JobApplicationResultDTO implements Serializable {

    private Name candidateName;
    private Name companyName;
    private EmailAddress candidateEmail;

    private String jobFunction;

    private StatusType status;

    private Id id;

    public JobApplicationResultDTO(Name candidateName, EmailAddress candidateEmail, Name companyName , Id id, String jobFunction, StatusType status) {
        this.candidateName = candidateName;
        this.companyName = companyName;
        this.candidateEmail = candidateEmail;
        this.id = id;
        this.jobFunction=jobFunction;
        this.status=status;


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

    public StatusType status() {
        return status;
    }

    public void changeStatus(StatusType status) {
        this.status = status;
    }

    public String jobFunction() {
        return jobFunction;
    }

    public void changeJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }
}

