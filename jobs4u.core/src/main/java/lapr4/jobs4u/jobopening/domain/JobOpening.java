package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lapr4.jobs4u.candidatemanagement.domain.*;
import lapr4.jobs4u.customer.domain.Customer;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 * The type Job opening.
 */
@Entity
public class JobOpening implements AggregateRoot<JobReference>, Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    private JobReference jobReference;
    @Embedded
    private NumberOfVacancies numberOfVacancies;

    @Embedded
    private JobFunction jobFunction;

    @Embedded
    private Mode mode;

    @Embedded
    private ContractType contractType;
    @Embedded
    private Address address;

    @ManyToOne
    private Customer companyName;

    @Embedded
    private Description description;

    @Column
    private Calendar registrationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private RecruitmentProcess recruitmentProcess;

    @ManyToOne
    private Plugin requirementsPlugin;
    @ManyToOne
    private Plugin interviewPlugin;

    @Column
    @Enumerated(EnumType.STRING)
    private JobOpeningStatus jobOpeningStatus = JobOpeningStatus.INACTIVE;

    /**
     * Instantiates a new Job opening.
     *
     * @param numberOfVacancies the number of vacancies
     * @param jobFunction       the job function
     * @param mode              the mode
     * @param contractType      the contract type
     * @param address           the address
     * @param companyName       the company name
     * @param description       the description
     * @param registrationDate  the registration date
     */
    public JobOpening(NumberOfVacancies numberOfVacancies,JobFunction jobFunction,Mode mode,ContractType contractType,Address address,Customer companyName, Description description,JobReference jobReference, Calendar registrationDate){
        if (numberOfVacancies == null || jobFunction == null || mode == null || contractType == null || address == null || companyName == null || description == null || registrationDate == null) {
            throw new IllegalArgumentException();
        }
        this.numberOfVacancies = numberOfVacancies;
        this.jobFunction = jobFunction;
        this.mode = mode;
        this.contractType = contractType;
        this.address = address;
        this.companyName=companyName;
        this.description = description;
        this.jobReference = jobReference;
        this.registrationDate = registrationDate;
        this.jobOpeningStatus = JobOpeningStatus.INACTIVE;
    }

    /**
     * Instantiates a new Job opening.
     */
    protected JobOpening() {

    }


    /**
     * Job reference.
     *
     * @return the job reference
     */
    public JobReference jobReference() {
        return jobReference;
    }

    /**
     * Number of vacancies number of vacancies.
     *
     * @return the number of vacancies
     */
    public NumberOfVacancies numberOfVacancies() {
        return numberOfVacancies;
    }

    /**
     * Job function job function.
     *
     * @return the job function
     */
    public JobFunction jobFunction() {
        return jobFunction;
    }

    /**
     * Mode.
     *
     * @return the mode
     */
    public Mode mode() {
        return mode;
    }

    /**
     * Contract type.
     *
     * @return the contract type
     */
    public ContractType contractType() {
        return contractType;
    }

    /**
     * Address.
     *
     * @return the address
     */
    public Address address() {
        return address;
    }

    /**
     * Description description.
     *
     * @return the description
     */
    public Description description() {
        return description;
    }

    /**
     * Registration date calendar.
     *
     * @return the calendar
     */
    public Calendar registrationDate(){
        return registrationDate;
    }

    /**
     * Company name customer.
     *
     * @return the customer
     */
    public Customer companyName() {
        return companyName;
    }

    /**
     * Recruitment process recruitment process.
     *
     * @return the recruitment process
     */
    public RecruitmentProcess recruitmentProcess() { return recruitmentProcess; }

    /**
     * Sets recruitment process.
     *
     * @param recruitmentProcess the recruitment process
     */
    public void setRecruitmentProcess(RecruitmentProcess recruitmentProcess) {
        this.recruitmentProcess = recruitmentProcess;
        updateState();
        active();
    }

    private void updateState(){
        if(jobOpeningStatus == JobOpeningStatus.INACTIVE || jobOpeningStatus == JobOpeningStatus.IN_SPECIFICATION ){
            if(recruitmentProcess!=null && recruitmentProcess.isFullySpecified() && hasRequirementPlugin() ||  (hasInterviewModel() && recruitmentProcess.hasInterview())){
                jobOpeningStatus = JobOpeningStatus.FULLY_SPECIFIED;
            }else{
                jobOpeningStatus = JobOpeningStatus.IN_SPECIFICATION;
            }
        }
    }

    public JobOpeningStatus jobOpeningStatus() {
        return jobOpeningStatus;
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public JobReference identity() {
        return jobReference;
    }

    /**
     * Sets id.
     *
     * @param jobReference the job reference
     */
    public void setId(JobReference jobReference) {
        this.jobReference = jobReference;
    }


    /**
     * Put requirements plugin.
     *
     * @param requirementsPlugin the requirements plugin
     */
    public void putRequirementsPlugin(Plugin requirementsPlugin) {
        this.requirementsPlugin = requirementsPlugin;
        updateState();
        active();
    }

    public boolean isActive(){
        return jobOpeningStatus.equals(JobOpeningStatus.ACTIVE);
    }

    public void active() {
        if (jobOpeningStatus == JobOpeningStatus.FULLY_SPECIFIED && PhaseNames.APPLICATION.equals(this.recruitmentProcess.currentPhase())) {
            jobOpeningStatus = JobOpeningStatus.ACTIVE;
        }
    }

    public void closed() {
        if (PhaseNames.RESULT.equals(this.recruitmentProcess.currentPhase())) {
            jobOpeningStatus = JobOpeningStatus.CLOSED;
        }
    }

    /**
     * Put interview plugin.
     *
     * @param interviewPlugin the interview plugin
     */
    public void putInterviewPlugin(Plugin interviewPlugin) {
        this.interviewPlugin = interviewPlugin;
        updateState();
        active();
    }

    /**
     * Requirements plugin plugin.
     *
     * @return the plugin
     */
    public Plugin requirementsPlugin() {
        return requirementsPlugin;
    }

    public void setPhase(PhaseNames phase) {
        this.recruitmentProcess.currentPhase(phase);
    }

    /**
     * Interview plugin plugin.
     *
     * @return the plugin
     */
    public Plugin interviewPlugin() {
        return interviewPlugin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOpening that = (JobOpening) o;
        return Objects.equals(version, that.version) && Objects.equals(jobReference, that.jobReference) && Objects.equals(numberOfVacancies, that.numberOfVacancies) && Objects.equals(jobFunction, that.jobFunction) && Objects.equals(mode, that.mode) && Objects.equals(contractType, that.contractType) && Objects.equals(address, that.address) && Objects.equals(companyName, that.companyName) && Objects.equals(description, that.description) && Objects.equals(registrationDate, that.registrationDate) && Objects.equals(recruitmentProcess, that.recruitmentProcess) && Objects.equals(requirementsPlugin, that.requirementsPlugin) && Objects.equals(interviewPlugin, that.interviewPlugin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, jobReference, numberOfVacancies, jobFunction, mode, contractType, address, companyName, description, registrationDate, recruitmentProcess, requirementsPlugin, interviewPlugin);
    }

    @Override
    public String toString() {
        return "JobOpening{" +
                "version=" + version +
                ", jobReference=" + jobReference +
                ", numberOfVacancies=" + numberOfVacancies +
                ", jobFunction=" + jobFunction +
                ", mode=" + mode +
                ", contractType=" + contractType +
                ", address=" + address +
                ", companyName=" + companyName +
                ", description=" + description +
                ", recruitmentProcess=" + recruitmentProcess +
                ", requirementsPlugin=" + requirementsPlugin +
                ", interviewPlugin=" + interviewPlugin +
                ", state=" + jobOpeningStatus +
                '}';
    }

    public boolean hasRequirementPlugin(){
        return requirementsPlugin != null;
    }

    public boolean hasInterviewModel(){
        return interviewPlugin != null;
    }

    public PhaseNames currentPhase() {
        return this.recruitmentProcess.currentPhase();
    }
    public void currentPhase(PhaseNames currentPhase) {
        this.recruitmentProcess.currentPhase(currentPhase);
        updateState();
        active();

    }

    public void currentPhaseToNextPhase(PhaseNames currentPhase) {
        this.recruitmentProcess.currentPhaseToNextPhase(currentPhase);
        updateState();
        active();
    }

    public void currentPhaseToPreviousPhase(PhaseNames currentPhase) {
        this.recruitmentProcess.currentPhaseToPreviousPhase(currentPhase);
    }



    public boolean updateDescription(String s) {
        if (this.recruitmentProcess() != null) {
            if (this.currentPhase() != null) {
                throw new IllegalArgumentException();
            }
        }
        this.description = new Description(s);
        return true;
    }

    public boolean updateJF(String s) {
        if (this.recruitmentProcess() != null) {
            if (this.currentPhase() != null) {
                throw new IllegalArgumentException();
            }
        }
        this.jobFunction = new JobFunction(s);
        return true;
    }

    public boolean updateAddress(Address address) {
        if (this.recruitmentProcess() != null) {
            if (this.currentPhase() != null) {
                throw new IllegalArgumentException();
            }
        }
        this.address = address;
        return true;
    }

    public boolean updateCType(String type) {
        if (this.recruitmentProcess() != null) {
            if (this.currentPhase() != null) {
                throw new IllegalArgumentException();
            }
        }
        this.contractType = ContractType.valueOf(type);
        return true;
    }

    public boolean updateMode(Mode mode) {
        if (this.recruitmentProcess() != null) {
            if (this.currentPhase() != null) {
                throw new IllegalArgumentException();
            }
        }
        this.mode = mode;
        return true;
    }

    public boolean updateVacancies(int numberOfVacancies) {
        if (this.recruitmentProcess() != null) {
            if (this.currentPhase() != null) {
                throw new IllegalArgumentException();
            }
        }
        this.numberOfVacancies = new NumberOfVacancies(numberOfVacancies);
        return true;
    }

    public void setJobOpeningStatus(JobOpeningStatus jobOpeningStatus) {
        this.jobOpeningStatus = jobOpeningStatus;
    }
}
