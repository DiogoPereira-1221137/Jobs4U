package lapr4.jobs4u.jobApplication.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.JobApplicationDTO;
import lapr4.jobs4u.jobApplication.domain.JobApplicationResultDTO;
import lapr4.jobs4u.jobApplication.domain.JobApplicationStateDTO;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext
            .repositories().jobApplications();

    private AuthorizationService authz = AuthzRegistry.authorizationService();

    private Iterable<JobApplicationDTO> transformToDTO(final Iterable<JobApplication> types) {
        return StreamSupport.stream(types.spliterator(), true)
                .map(JobApplication::toDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    private List<JobApplicationStateDTO> transformToDTO(final Map<JobApplication, Integer> types) {
        return types.entrySet().parallelStream()
                .map(entry -> entry.getKey().toStateDTO(entry.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<JobApplicationResultDTO> transformToResultDTO(final Iterable<JobApplication> jobApplication) {
        return StreamSupport.stream(jobApplication.spliterator(), true)
                .map(JobApplication::toResultDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    public  List<JobApplicationDTO> findByJobOpening(JobOpening jobOpeningSelected){

        return (List<JobApplicationDTO>) transformToDTO(jobApplicationRepository.findByJobOpening(jobOpeningSelected));

    }

    public List<JobApplicationStateDTO> findJobApplicationsStateAndApplicants(EmailAddress email){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CANDIDATE);

        Map<JobApplication, Integer> jobApplicationNumberCandidates = new HashMap<>();
        Iterable<JobApplication> jobApplicationList = jobApplicationRepository.findByCandidate(email);

        for(JobApplication ja : jobApplicationList){
            List<JobApplication> jobApplications = (List<JobApplication>) jobApplicationRepository.findByJobOpening(ja.jobOpening());
            jobApplicationNumberCandidates.put(ja, jobApplications.size());
        }

        return transformToDTO(jobApplicationNumberCandidates);
    }
}
