package lapr4.jobs4u.jobopening.domain;

import jakarta.persistence.*;
import lapr4.jobs4u.interview.domain.Interview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;



/**
 * The type Recruitment process.
 */
@Entity
public class RecruitmentProcess implements Serializable {
    @Column
    @Enumerated(EnumType.STRING)
    private PhaseNames currentPhase;
    @Column
    private boolean hasInterview = true;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Phase> phases=  new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Instantiates a new Recruitment process.
     *
     * @param hasInterview the has interview
     * @param phases       the phases
     */
    public RecruitmentProcess(boolean hasInterview, List<Phase> phases) {
        this.hasInterview = hasInterview;
        this.phases = phases;
    }

    /**
     * Instantiates a new Recruitment process.
     */
    protected RecruitmentProcess() {

    }


    public boolean hasInterview() {
        return hasInterview;
    }

    @Override
    public String toString() {
        String interview = "Has interview";
        if(!hasInterview) interview = "No interview";
        String s = "\n " + interview;
        List<String> list = new ArrayList<>();
        list.add("\n- Application Phase: ");
        list.add("\n- Screening Phase: ");
        if(hasInterview) list.add("\n- Interview Phase: ");
        list.add("\n- Analysis Phase: ");
        list.add("\n- Result Phase: ");
        int i = 0;
        for(Phase p : phases) {
           s += list.get(i) + p.toString();
           i++;
        }
    return s;
    }


    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long id() {
        return id;
    }

    public PhaseNames currentPhase() {
        return currentPhase;
    }

    public void currentPhase(PhaseNames currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void currentPhaseToNextPhase(PhaseNames currentPhase) {
        for (Iterator<Phase> it = phases.stream().iterator(); it.hasNext(); ) {
            Phase phase = it.next();
            if(phase.phaseName().equals(currentPhase)) {
                if(it.hasNext()){
                    phase = it.next();
                    currentPhase(phase.phaseName());
                }
            }

        }
    }

    public void currentPhaseToPreviousPhase(PhaseNames currentPhase) {
        Phase previousePhase = phases.get(0);
        for (Iterator<Phase> it = phases.stream().iterator(); it.hasNext(); ) {
            Phase phase = it.next();
            if(phase.phaseName().equals(currentPhase)) {
                currentPhase(previousePhase.phaseName());
            }
            previousePhase = phase;
        }
    }

    public boolean isFullySpecified(){
        if(hasInterview){
            return phases != null && phases.size() == 5;
        }else{
            return phases != null && phases.size() == 4;
        }
    }

    public Phase phase() {
        if (currentPhase != null && !phases.isEmpty()) {

            for (Phase phase : phases) {
                if (phase.phaseName() == currentPhase) {
                    return phase;
                }
            }
        }
        return null;
    }

    public void setPhasesEndDate(int index, Calendar calendar){
        this.phases.get(index).endDate(calendar);
    }

    public void setPhasesBeginDate(int index, Calendar calendar){
        this.phases.get(index).startDate(calendar);
    }


    public InterviewPhase getInterviewPhase(){
        for(Phase phase :phases){
            if(phase instanceof InterviewPhase){
                return (InterviewPhase) phase;
            }
        }
        return null;
    }

    public TimePeriod DateSpecificPhase(PhaseNames phaseWanted) {

        if (currentPhase != null && !this.phases.isEmpty()) {

            for (Phase phase : this.phases) {
                if (phase.phaseName() == phaseWanted) {
                    return phase.timePeriod();
                }
            }
        }

        return null;

    }
}
