package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.representations.dto.DTO;
import lapr4.jobs4u.jobopening.domain.NumberOfVacancies;
import lombok.Data;

import java.io.Serializable;


/**
 * The type Job application.
 */
@DTO
@Data
//@AllArgsConstructor
public class JobApplicationStateDTO implements  Serializable {


    private StatusType status;

    private int numberCandidates;

    private Id id;

    public JobApplicationStateDTO(StatusType status, int numberCandidates, Id id) {
        this.status = status;
        this.numberCandidates = numberCandidates;
        this.id = id;
    }

    public StatusType status() {
        return status;
    }

    public void changeStatus(StatusType status) {
        this.status = status;
    }

    public int numberCandidates() {
        return numberCandidates;
    }

    public void changeNumberCandidates(int numberCandidates) {
        this.numberCandidates = numberCandidates;
    }

    public Id id() {
        return id;
    }

    public void changeId(Id id) {
        this.id = id;
    }
}