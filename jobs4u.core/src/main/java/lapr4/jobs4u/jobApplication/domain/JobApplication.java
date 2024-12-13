package lapr4.jobs4u.jobApplication.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.*;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.notification.domain.Notification;
import lombok.Getter;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


/**
 * The type Job application.
 */
@Entity
public class JobApplication implements AggregateRoot<Id>, Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    private Candidate candidate;

    @ManyToOne
    private JobOpening jobOpening;

    @Column
    private Grade grade;


    @ElementCollection
    @CollectionTable(name = "file_paths", joinColumns = @JoinColumn(name = "job_application_id"))
    @MapKeyColumn(name = "file_type")
    @Fetch(FetchMode.JOIN)
//    private Map<Type, ApplicationFile> filePaths = new HashMap<>();
    private List<ApplicationFile> filePaths = new ArrayList<>();
    @Column
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @EmbeddedId
    private Id id;

    /**
     * Instantiates a new Job application.
     *
     * @param candidate  the candidate
     * @param jobOpening the job opening
     */
    public JobApplication(Candidate candidate, JobOpening jobOpening) {
        if (candidate == null || jobOpening == null) {
            throw new IllegalArgumentException();
        }
        this.candidate = candidate;
        this.jobOpening = jobOpening;
        this.status = StatusType.WAITING;
        this.id = new Id(this.jobOpening.jobReference());
    }


    /**
     * Candidate candidate.
     *
     * @return the candidate
     */
    public Candidate candidate() {
        return candidate;
    }

    /**
     * Job opening.
     *
     * @return the job opening
     */
    public JobOpening jobOpening() {
        return jobOpening;
    }

    public void FilePaths(List<ApplicationFile> filePaths) {
        this.filePaths.addAll(filePaths);
    }
    public boolean addFilePath(ApplicationFile filePath) {
        return this.filePaths.add(filePath);
    }

    /**
     * File paths list.
     *
     * @return the list
     */
    public List<ApplicationFile> filePathsInfo() {
        List<ApplicationFile> infoPaths = new ArrayList<>();
        for (ApplicationFile file: this.filePaths) {
            if (file.type().equals(Types.INFO)) infoPaths.add(file);
        }
        return infoPaths;
    }

    /**
     * File paths list.
     *
     * @return the list
     */
    public List<ApplicationFile> filePathsInterview() {
        List<ApplicationFile> intPaths = new ArrayList<>();
        for (ApplicationFile file: this.filePaths) {
            if (file.type().equals(Types.INTERVIEW)) intPaths.add(file);
        }
        return intPaths;
    }


    /**
     * File paths list.
     *
     * @return the list
     */
    public List<ApplicationFile> filePathsRequirements() {
        List<ApplicationFile> reqPaths = new ArrayList<>();
        for (ApplicationFile file: this.filePaths) {
            if (file.type().equals(Types.REQUIREMENTS)) reqPaths.add(file);
        }
        return reqPaths;
    }



    /**
     * Status.
     *
     * @return the status
     */
    public StatusType status() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void StatusType(StatusType status) {
        this.status = status;
    }

    /**
     * Instantiates a new Job application.
     */
    protected JobApplication() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplication that = (JobApplication) o;
        return Objects.equals(candidate, that.candidate) && Objects.equals(jobOpening, that.jobOpening) && Objects.equals(filePaths, that.filePaths) && Objects.equals(status, that.status) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidate, jobOpening, filePaths, status, id);
    }

    @Override
    public String toString() {
        return "\033[1mIdentifier: \033[0m" + this.id.getId()+ "\n\033[1mJob Opening: \033[0m" + this.jobOpening.jobReference() +"\n\033[1mCandidate:\033[0m " + candidate.getId() + "\n\033[1mStatus: \033[0m" + this.status() ;
    }

    /**
     * Approve application.
     */
    public void ApproveApplication(){
        this.status = StatusType.APPROVED;
    }

    /**
     * Reject application.
     */
    public void RejectApplication(){
        this.status = StatusType.REJECTED;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public Id identity() {
        return id;
    }

    public Grade grade() {
        return grade;
    }

    public void grade(Grade grade) {
        this.grade = grade;
    }

    /**
     * Get job opening
     *
     * @return the job opening
     */
    public JobOpening JobOpening(){
        return this.jobOpening;
    }

    /**
     * Get candidate candidate.
     *
     * @return the candidate
     */
    public Candidate Candidate(){
        return this.candidate;
    }

    public boolean hasInterviewFile(){
        return !filePathsInterview().isEmpty();
    }

    public JobApplicationDTO toDTO() {
        return new JobApplicationDTO(this.candidate.user().name(),this.candidate.user().email(),this.jobOpening.companyName().user().name(),this.id);
    }

    public JobApplicationStateDTO toStateDTO(int number) {
        return new JobApplicationStateDTO(this.status, number, this.id);
    }

    public JobApplicationResultDTO toResultDTO() {
        return new JobApplicationResultDTO(this.candidate.user().name(), this.candidate.user().email(),this.jobOpening.companyName().user().name(), this.id, this.jobOpening.jobFunction().toString(), this.status);
    }
}
