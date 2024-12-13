package lapr4.jobs4u.candidatemanagement.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.domain.Pair;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Display candidate data controller.
 */
@UseCaseController
public class DisplayCandidateDataController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final CandidateRepository candidateRepository = PersistenceContext.repositories().candidates();
    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    /**
     * All active candidates iterable.
     *
     * @return the iterable
     */
    public Iterable<Candidate> allActiveCandidates() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        return this.candidateRepository.findAllActive();
    }

    /**
     * Gets applications.
     *
     * @param selectedCandidate the selected candidate
     * @return the applications
     */
    public Iterable<JobApplication> getApplications(Candidate selectedCandidate) {
        return this.jobApplicationRepository.findByCandidate(selectedCandidate.getId());
    }

    public Pair<Map<String, Integer>, Map<String, List<String>>> mostFrequently(JobApplication jA){

        File folder = new File("import/bootstrap");

//        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        List<ApplicationFile> files = jA.filePathsInfo();


        if (files == null || files.isEmpty()) {
            return null;
        }

        List<Thread> threads = new ArrayList<>();
        List<WordCountTask> tasks = new ArrayList<>();
        Map<String, Integer> counter = new HashMap<>();
        Map<String, List<String>> fileAppearances = new HashMap<>();
        for (ApplicationFile file : files) {
            WordCountTask task = new WordCountTask(file, counter, fileAppearances);
            tasks.add(task);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        Map<String, Integer> consolidatedWordCounts = new HashMap<>();
//        for (WordCountTask task : tasks) {
//            Map<String, Integer> wordCounts = task.getWordCounts();
//            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
//                consolidatedWordCounts.put(entry.getKey(), consolidatedWordCounts.getOrDefault(entry.getKey(), 0) + entry.getValue());
//            }
//        }

//        List<Map.Entry<String, Integer>> sortedWordCounts = new ArrayList<>(consolidatedWordCounts.entrySet());
        return new Pair<>(counter, fileAppearances);



    }




}


