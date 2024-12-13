package lapr4.jobs4u.jobApplication.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.Grade;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.*;

public class OrderCandidatesByGradeController {
    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();


    public List<JobOpening> allActiveJobOpeningsByCustomerManager(){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();
        Iterable<JobOpening> allJobOpenings =jobOpeningRepository.allActiveJobOpeningsByCustomerManager(currentUser);
        return new ArrayList<>((Collection<? extends JobOpening>) allJobOpenings);
    }


    public Map<Candidate, Grade> getCandidatesAndGradesByJobOpening(JobOpening jobOpening) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Map<Candidate, Grade> candidateGradeMap = new HashMap<>();
        Iterable<JobApplication> jobApplicationsByJobOpening =jobApplicationRepository.findByJobOpening(jobOpening);

        for(JobApplication jobApplication : jobApplicationsByJobOpening){
            if(jobApplication.grade() != null) {
                candidateGradeMap.put(jobApplication.candidate(), jobApplication.grade());
            }
        }

        return candidateGradeMap;

    }

    public Map<Candidate, Grade> OrderListCandidatesAndGrades(Map<Candidate, Grade> candidateGradeMap) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        List<Map.Entry<Candidate, Grade>> entries = new ArrayList<>(candidateGradeMap.entrySet());

        entries.sort(Map.Entry.<Candidate, Grade>comparingByValue().reversed());

        Map<Candidate, Grade> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Candidate, Grade> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }
}
