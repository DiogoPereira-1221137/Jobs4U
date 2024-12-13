/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.jobApplication.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.domain.PhoneNumber;
import lapr4.jobs4u.candidatemanagement.domain.events.RegisterCandidateEvent;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.*;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.PasswordGenerator;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import eapli.framework.infrastructure.pubsub.EventPublisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * The type Register job application controller.
 */
@UseCaseController
public class RegisterJobApplicationController {

    private PasswordPolicy policy;
    private PasswordEncoder encoder;


    private final UserRepository repo = PersistenceContext.repositories().users();
    private final JobApplicationRepository jobApplicationRepository = PersistenceContext
            .repositories().jobApplications();

    private final CandidateRepository candidateRepository = PersistenceContext
            .repositories().candidates();

    private final JobOpeningRepository JobOpeningRepository = PersistenceContext.repositories().jobOpenings();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    private final EventPublisher dispatcher = InProcessPubSub.publisher();

    /**
     * Get existing RoleTypes available to the user.
     *
     * @return a list of RoleTypes
     */
    public Role[] getRoleTypes() {
        return Roles.nonUserValues();
    }

    /**
     * Register job application job application.
     *
     * @param candidateEmail       the candidate email
     * @param candidateFirstName   the candidate first name
     * @param candidateLastName    the candidate last name
     * @param candidatePhoneNumber the candidate phone number
     * @param jobOpening           the job opening
     * @param filesData            the files data
     * @return the job application
     */
    public JobApplication registerJobApplication(EmailAddress candidateEmail, String candidateFirstName,String candidateLastName,  PhoneNumber candidatePhoneNumber, JobOpening jobOpening, List<ApplicationFile> filesData) {

        try{
            authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN, Roles.OPERATOR);

            Optional<Candidate> candidate = candidateRepository.findByEmail(candidateEmail);

            JobApplication jobApplication;

            Type type = Types.INFO;
            if(candidate.isPresent()){
                jobApplication = new JobApplication(candidate.get(),jobOpening);
                jobApplication.FilePaths(filesData);

            }else{
                Set<Role> roles = new HashSet<>();
                roles.add(Roles.CANDIDATE);

                String password = PasswordGenerator.generatePassword();
                SystemUser systemUser = addUser(candidateEmail.toString(), password, candidateFirstName, candidateLastName,  roles, CurrentTimeCalendars.now());

                RegisterCandidateEvent event = new RegisterCandidateEvent(systemUser, candidatePhoneNumber.toString());
                dispatcher.publish(event);

                Thread.sleep(18000);


                Optional<Candidate> newCandidate = candidateRepository.findByEmail(candidateEmail);

                Candidate c = newCandidate.get();

                jobApplication = new JobApplication(c,jobOpening);
                jobApplication.FilePaths(filesData);
            }

            jobApplicationRepository.save(jobApplication);

            return jobApplication;
        }catch (Exception e){
            System.out.println("An error occurred in the registration of a job application.");

        }
        return null;
    }

    /**
     * Add user system user.
     *
     * @param email     the email
     * @param password  the password
     * @param firstName the first name
     * @param lastName  the last name
     * @param roles     the roles
     * @param createdOn the created on
     * @return the system user
     */
    public SystemUser addUser(final String email, final String password, final String firstName,
                              final String lastName, final Set<Role> roles, final Calendar createdOn) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.OPERATOR, Roles.ADMIN);


        return userSvc.registerNewUser(email, password, firstName, lastName, email, roles, createdOn);
    }


    /**
     * Find all job openings iterable.
     *
     * @return the iterable
     */
    public Iterable<JobOpening> findAllJobOpenings(EmailAddress candidateEmail) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN, Roles.OPERATOR);
        List<JobOpening> jobOpenings =  new ArrayList<>();

        for (JobOpening jobOpening : this.JobOpeningRepository.jobOpenings()){
            if(!jobApplicationRepository.findByEmailAndJobReference(candidateEmail, jobOpening)){ //&& jobOpening.currentPhase().equals(PhaseNames.APPLICATION)){
                jobOpenings.add(jobOpening);
            }
        }
        return jobOpenings;
    }

    /**
     * Gets folders.
     *
     * @return the folders
     */
    public List<String> getFolders() {
        try{
            List<String> FoldersName = new ArrayList<>();
            File folder = new File("import");
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles != null) {
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isDirectory()){
                        FoldersName.add(listOfFiles[i].getName());
                    }
                }
            }
            return FoldersName;
        } catch (Exception e){
            System.out.println("There are no folders!");
        }

        return null;
    }

    /**
     * Gets files name.
     *
     * @param folder the folder
     * @return the files name
     * @throws IOException the io exception
     */
    public List<ApplicationFile> getFilesName(String folder) throws IOException {
        List<ApplicationFile> FilesNames = new ArrayList<>();

        File folderName = new File("import/"+folder);
        File[] listOfFiles = folderName.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                    FilesNames.add(new ApplicationFile(Types.INFO,listOfFiles[i].getCanonicalPath(), listOfFiles[i]));
            }

        } else {
            System.out.println("The directory is empty or does not exist.");
        }
        return FilesNames;

    }


    /**
     * Read file content list.
     *
     * @param file the file
     * @return the list
     */
    public static List<String> readFileContent(File file) {
        List<String> candidateData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                candidateData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return candidateData;
    }
}
