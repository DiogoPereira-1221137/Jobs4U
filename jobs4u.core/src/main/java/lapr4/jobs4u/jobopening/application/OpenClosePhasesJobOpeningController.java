package lapr4.jobs4u.jobopening.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.interview.domain.Interview;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.StatusType;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.Phase;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.rank.domain.Rank;
import lapr4.jobs4u.rank.repositories.RankRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type List job opening controller.
 */
@UseCaseController
public class OpenClosePhasesJobOpeningController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();
    private final InterviewRepository interviewRepository = PersistenceContext.repositories().interviews();

    private final RankRepository rankRepository = PersistenceContext
            .repositories().ranks();


//    /**
//     * All job openings iterable.
//     *
//     * @return the iterable
//     */
//    public Iterable<JobOpening> allActiveJobOpeningsByCustomerManager() {
//        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
//        SystemUser currentUser= authz.session().get().authenticatedUser();
//        return this.jobOpeningRepository.allActiveJobOpeningsByCustomerManager(currentUser);
//    }

    public Iterable<JobOpening> allJobOpeningsByCustomerManager() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();
        return this.jobOpeningRepository.allJobOpeningsByCustomerManager(currentUser);
    }


    public PhaseNames currentPhase(JobOpening jobOpening) {
        try {
            authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
            return jobOpening.currentPhase();
        }catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
       return null;
    }

    public JobOpening processOperation(int choice, JobOpening jobOpening, PhaseNames phase){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        int index = 0;


        if(choice == 1){
            index = getIndex(jobOpening, index);
            jobOpening.recruitmentProcess().setPhasesBeginDate(index, today);
            jobOpening.currentPhase(phase);

            jobOpeningRepository.save(jobOpening);

        }

        if(choice == 2){
            index = getIndex(jobOpening, index);
            jobOpening.recruitmentProcess().setPhasesEndDate(index, today);
            jobOpening.recruitmentProcess().setPhasesBeginDate(index+1, today);
            jobOpening.currentPhaseToNextPhase(phase);

            jobOpeningRepository.save(jobOpening);

        }

        if(choice == 3){
            if(canBacktrack(phase, jobOpening)){
                index = getIndex(jobOpening, index);
                jobOpening.recruitmentProcess().setPhasesEndDate(index-1, today);
                // jobOpening.recruitmentProcess().setPhasesBeginDate(index, today);
                jobOpening.currentPhaseToPreviousPhase(phase);

                jobOpeningRepository.save(jobOpening);

            }else{
                System.out.println("Can't backtrack because this phases actions already started!!");
            }

        }


        return jobOpening;
    }

    private boolean canBacktrack(PhaseNames phase, JobOpening jobOpening) {
        boolean flag = false;
        if(phase.equals(PhaseNames.SCREENING)){
            Iterable<JobApplication> jobApplications = jobApplicationRepository.findByJobOpening(jobOpening);
            for (JobApplication ja : jobApplications) {
                flag = ja.status().equals(StatusType.WAITING);
                if(!flag) return flag;
            }

        } else if(phase.equals(PhaseNames.INTERVIEW)){
            Iterable<Interview> interviews= interviewRepository.interviews();
            if (!interviews.iterator().hasNext()) {
                flag = true;
            }

        }else if(phase.equals(PhaseNames.ANALYSIS)){
            if (rankRepository.findByJobReference(jobOpening.jobReference()).isEmpty()){
                flag = true;
            }

        }else if(phase.equals(PhaseNames.RESULT)){

            flag= true;

        }

        return flag;
    }

    private int getIndex(JobOpening jobOpening, int index) {
        if(jobOpening.currentPhase() == null){
            return index;
        }
        switch(jobOpening.currentPhase()){
            case APPLICATION:
                jobOpening.active();
                break;
            case SCREENING:
                jobOpening.active();
                index = 1;
                break;

            case INTERVIEW:
                jobOpening.active();
                index = 2;
                break;
            case ANALYSIS:
                if(!jobOpening.recruitmentProcess().hasInterview()){
                    jobOpening.active();
                    index = 2;
                    break;
                }
                jobOpening.active();
                index = 3;
                break;
            case RESULT:
                if(!jobOpening.recruitmentProcess().hasInterview()){
                    jobOpening.active();
                    index = 3;
                    break;
                }
                jobOpening.closed();
                index = 4;
                break;
        }
        return index;
    }
}
