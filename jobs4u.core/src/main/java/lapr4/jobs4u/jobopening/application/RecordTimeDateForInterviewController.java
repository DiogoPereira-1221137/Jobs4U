package lapr4.jobs4u.jobopening.application;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.interview.domain.Interview;
import lapr4.jobs4u.interview.domain.InterviewSchedule;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.*;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.*;

public class RecordTimeDateForInterviewController {
    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    private final InterviewRepository interviewRepository = PersistenceContext.repositories().interviews();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<JobOpening> findAllJobOpeningsWithInterview(){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();
        Iterable<JobOpening> jobOpenings =jobOpeningRepository.findAllActiveJobOpeningsWithInterviewByCustomerManager(currentUser);
        return new ArrayList<>((Collection<? extends JobOpening>) jobOpenings);
    }


    public List<JobApplication> getAcceptedJobApplicationsByJobOpening(JobOpening jobOpening) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Iterable<JobApplication> jobApplications = jobApplicationRepository.findAcceptedApplicationsByJobOpening(jobOpening);
        return new ArrayList<>((Collection<? extends JobApplication>) jobApplications);
    }

    public List<Interview> findEqualInterview(final JobOpening jobOpening, final Calendar date, final Calendar hour) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Iterable<Interview> interviews = interviewRepository.findEqual(jobOpening, date, hour);
        return new ArrayList<>((Collection<? extends Interview>) interviews);
    }

    public Interview registerInterview( final JobOpening jobOpening, final JobApplication jobApplication, final Calendar date, final Calendar hour) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Id jobApplicationId = jobApplication.identity();
        Interview interview = new Interview( jobApplicationId, jobOpening, date, hour);
        interviewRepository.save(interview);
        return interview;
    }

    public Interview updateInterview(Interview existingInterview, Calendar date, Calendar hour) throws ConcurrencyException {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        if (existingInterview == null) {
            throw new IllegalArgumentException("Interview does not exist.");
        }

        existingInterview.interviewSchedule().setDate(date);
        existingInterview.interviewSchedule().setHour(hour);

        try {
            interviewRepository.save(existingInterview);
            return existingInterview;
        } catch (ConcurrencyException e) {
            throw new ConcurrencyException("Concurrency error while updating the interview.", e);
        }
    }

    public List<Interview> findInterviewsByJobApplication(JobApplication jobApplication){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Id jobApplicationId = jobApplication.identity();
        Iterable<Interview> existingInterview = interviewRepository.findInterviewsByJobApplication(jobApplicationId);
        return new ArrayList<>((Collection<? extends Interview>) existingInterview);
    }

}
