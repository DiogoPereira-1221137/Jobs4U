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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.infrastructure.bootstrapers;

import java.io.File;
import java.io.IOException;
import java.util.*;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.candidatemanagement.application.RegisterCandidateController;
import lapr4.jobs4u.candidatemanagement.domain.*;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;
import lapr4.jobs4u.customer.application.RegisterCustomerController;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.interview.repositories.InterviewRepository;
import lapr4.jobs4u.jobApplication.domain.*;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.application.RegisterJobOpeningController;
import lapr4.jobs4u.jobopening.domain.*;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.rank.domain.Rank;
import lapr4.jobs4u.rank.repositories.RankRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 * @author Paulo Gandra Sousa
 */
public class MasterUsersBootstrapper extends AbstractUserBootstrapper implements Action {

    private final RegisterCandidateController theControllerCandidate = new RegisterCandidateController();
    private final CandidateRepository candidateRepository = PersistenceContext
            .repositories().candidates();


    private final RegisterCustomerController theControllerCustomer = new RegisterCustomerController();

    private final CustomerRepository customerRepository = PersistenceContext
            .repositories().customers();


    private final RegisterJobOpeningController theControllerJobOpening = new RegisterJobOpeningController();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext
            .repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext
            .repositories().jobApplications();

    private final PluginRepository pluginRepository = PersistenceContext
            .repositories().plugins();

    private final RankRepository rankRepository = PersistenceContext.repositories().ranks();
    private final InterviewRepository interviewRepository = PersistenceContext.repositories().interviews();

    private final NotificationRepository notificationsRepository = PersistenceContext.repositories().notifications();

    private final UserRepository repo = PersistenceContext.repositories().users();

    private Candidate candidateDefault;

    private Customer customerDefault;

    private JobOpening jobOpeningDefault;

    private JobApplication jobApplicationDefault;
    private Plugin pluginInterviewDefault;
    private Plugin pluginRequirementsDefault;

    private Notification notificationDefault;


    @Override
    public boolean execute() {


        registerAdmin("admin@gmail.com", TestDataConstants.PASSWORD1, "Jane", "Doe");

        customerDefault = registerCustomer("customer@gmail.com", TestDataConstants.PASSWORD1, "Ana", "Pereira") ;


        registerLanguageEngineer(  "languageEngineer@gmail.com", TestDataConstants.PASSWORD1, "João", "Sousa");

        candidateDefault = registerCandidate("candidate@gmail.com", "#Password1", "Pedro", "Silva", "968652872");

        registerOperator("operator@gmail.com", TestDataConstants.PASSWORD1, "Mário", "Oliveira");

        pluginInterviewDefault = registerPluginInterview();

        pluginRequirementsDefault = registerPluginRequirements();

        jobOpeningDefault = registerJobOpening();

        try {
            jobApplicationDefault = registerJobApplication();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        rankTest();

        notifcation();

        return true;
    }

    private void notifcation() {

        Notification notification = new Notification("candidate@gmail.com", "customer@gmail.com", "test");

        EmailAddress a = EmailAddress.valueOf("candidate@gmail.com");

        EmailAddress b = EmailAddress.valueOf("customer@gmail.com");


        Notification notification2 = new Notification(b, a, "Test", "Test", "test");

        Notification notification3 = new Notification(a, b, "Test", "Test", "test");
        Notification notification4 = new Notification(a, b, "Test", "Notification System Test", "test");
        Notification notification5 = new Notification(a, b, "Test", "Notification System Test", "test");
        Notification notification6 = new Notification(a, b, "Test", "Notification System Test", "test");
        Notification notification7 = new Notification(a, b, "Test", "Notification System Test", "test");
        Notification notification8 = new Notification(a, b, "Test", "Notification System Test", "test");
        notification4.markAsRead();
        notification5.markAsRead();
        notification7.markAsRead();

        notificationsRepository.save(notification);
        notificationsRepository.save(notification2);
        notificationsRepository.save(notification3);
        notificationsRepository.save(notification4);
        notificationsRepository.save(notification5);
        notificationsRepository.save(notification6);
        notificationsRepository.save(notification7);
        notificationsRepository.save(notification8);
    }

    private void rankTest() {

        JobReference job = new JobReference(10);

        ArrayList<Id> test = new ArrayList<>();
        test.add(new Id(job));
        test.add(new Id(job));
        Rank rank = new Rank(job,test);
        rankRepository.save(rank);


    }


    private Plugin registerPluginInterview() {

        PluginType pluginType = PluginType.INTERVIEW;
        Description description = new Description("Interview Model Plugin");
        PluginMainClassName mainClassName = new PluginMainClassName("com.interview.Interview");
        JarFileName jarFileName = new JarFileName("interviewPlugin.jar");

        Plugin plugin = new Plugin(pluginType, description, mainClassName, jarFileName);


        return pluginRepository.save(plugin);

    }

    private Plugin registerPluginRequirements() {

        PluginType pluginType = PluginType.REQUIREMENTS;
        Description description = new Description("Requirements Plugin");
        PluginMainClassName mainClassName = new PluginMainClassName("com.requirements.RequirementsA");
        JarFileName jarFileName = new JarFileName("testRequirementsPlugin.jar");

        Plugin plugin = new Plugin(pluginType, description, mainClassName, jarFileName);


        return pluginRepository.save(plugin);

    }

    private JobOpening registerJobOpening() {
        NumberOfVacancies numberOfVacancies = new NumberOfVacancies(2);
        JobFunction jobFunction = new JobFunction("teste");
        Mode mode = Modes.REMOTE;
        ContractType contractType = new ContractType(ContractTypes.FULL_TIME);

        Integer lastJobOpeningReference = theControllerJobOpening.getLastJobReference();
        JobReference jobReference = theControllerJobOpening.generateNextJobReference(lastJobOpeningReference);

        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");
        Customer companyName =  customerRepository.findByCode(new Code("ABC111")).get();
        Description description = new Description("teste");

        JobOpening jobOpening = new JobOpening(numberOfVacancies,jobFunction, mode, contractType, address, companyName, description , jobReference,CurrentTimeCalendars.now());

        boolean hasInterview = false;

        List<Phase> dates = new ArrayList<>();
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar endDateScreening = Calendar.getInstance();
        Calendar endDateInterview = Calendar.getInstance();
        Calendar endDateAnalysis = Calendar.getInstance();
        Calendar endDateResult = Calendar.getInstance();

        beginDate.set(2024, Calendar.SEPTEMBER, 7);
        endDate.set(2024, Calendar.SEPTEMBER, 8);
        endDateScreening.set(2024, Calendar.SEPTEMBER, 10);
        endDateInterview.set(2024, Calendar.SEPTEMBER, 12);
        endDateAnalysis.set(2024, Calendar.SEPTEMBER, 14);
        endDateResult.set(2024, Calendar.SEPTEMBER, 15);

        ApplicationPhase applicationPhase = new ApplicationPhase(beginDate, endDate);
        ScreeningPhase screeningPhase = new ScreeningPhase(endDate, endDateScreening);
//        InterviewPhase interviewPhase = new InterviewPhase(endDateScreening, endDateInterview);
        AnalysisPhase analysisPhase = new AnalysisPhase(endDateInterview, endDateAnalysis);
        ResultPhase resultPhase = new ResultPhase(endDateAnalysis, endDateResult);

        dates.add(applicationPhase);
        dates.add(screeningPhase);
//        dates.add(interviewPhase);
        dates.add(analysisPhase);
        dates.add(resultPhase);

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(hasInterview, dates);

        jobOpening.setRecruitmentProcess(recruitmentProcess);

        jobOpening.currentPhase(PhaseNames.ANALYSIS);
        jobOpening.setJobOpeningStatus(JobOpeningStatus.ACTIVE);

        return jobOpeningRepository.save(jobOpening);
    }


    private JobApplication registerJobApplication() throws IOException {

        Customer companyName =  customerRepository.findByCode(new Code("ABC111")).get();

        Candidate candidate = candidateRepository.findByEmail(EmailAddress.valueOf("candidate@gmail.com")).get();

        Candidate c = candidateRepository.findByEmail(EmailAddress.valueOf("teste@gmail.com")).get();

        JobOpening jobOpening = jobOpeningRepository.findByCustomer(companyName).get();
        JobApplication jobApplication = new JobApplication(candidate,jobOpening);
        JobApplication jobApplication2 = new JobApplication(c,jobOpening);

        List<ApplicationFile> s = new ArrayList<>();
        s.add(new ApplicationFile(Types.INFO,"import/bootstrap/2-candidate-data.txt", new File("import/bootstrap/2-candidate-data.txt")));
        jobApplication2.FilePaths(s);
        s.add(new ApplicationFile(Types.INFO,"import/bootstrap/2-big-file1.txt", new File("import/bootstrap/2-big-file1.txt")));


        List<ApplicationFile> s2 = new ArrayList<>();
        s2.add(new ApplicationFile(Types.INTERVIEW,"import/bootstrap/2-big-file1.txt", new File("import/bootstrap/2-big-file1.txt")));
        s2.add(new ApplicationFile(Types.INTERVIEW,"import/bootstrap/2-candidate-data.txt", new File("import/bootstrap/2-candidate-data.txt")));
        jobApplication.FilePaths(s);
        jobApplication.FilePaths(s2);

//        jobApplication2.grade(new Grade(40));

        jobApplicationRepository.save(jobApplication2);
        //jobApplication.ApproveApplication();
        //jobApplication2.ApproveApplication();

//        jobApplication.grade(new Grade(30));

        return jobApplicationRepository.save(jobApplication);
    }

    private void registerOperator(final String email, final String password, final String firstName,
                                   final String lastName) {
        final Set<Role> roles = new HashSet<>();
        roles.add(Roles.OPERATOR);

        registerUser(email, password, firstName, lastName,  roles);
    }

    private Candidate registerCandidate(final String email, final String password, final String firstName,
                                          final String lastName, final String phoneNumber) {
        final Set<Role> roles = new HashSet<>();
        roles.add(Roles.CANDIDATE);

        PhoneNumber phoneNumberCandidate = new PhoneNumber(phoneNumber);

        SystemUser s = registerUser(email, password, firstName, lastName,  roles);

        SystemUser systemUser = registerUser("teste@gmail.com", "#Password1", "Mário", "Oliveira", roles);
        Candidate c = new Candidate(systemUser, new PhoneNumber("968652872"));
        c.setId(EmailAddress.valueOf(systemUser.email().toString()));

        Candidate candidate = new Candidate(s, phoneNumberCandidate);

        candidate.setId(EmailAddress.valueOf(email));

        candidateRepository.save(candidate);
        candidateRepository.save(c);

        return candidate;

    }

    private void registerLanguageEngineer(final String email, final String password, final String firstName,
                                  final String lastName) {
        final Set<Role> roles = new HashSet<>();
        roles.add(Roles.LANGUAGE_ENGINEER);

        registerUser(email, password, firstName, lastName,roles);
    }

    private Customer registerCustomer(final String email, final String password, final String firstName,
                               final String lastName) {
        final Set<Role> roles = new HashSet<>();
        roles.add(Roles.CUSTOMER);
        Code code = new Code("ABC111");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        SystemUser customerUser = registerUser(email, password, firstName, lastName,roles);

        SystemUser customerManager = null;

        for (SystemUser systemUser : repo.findByActive(true)) {
            if(systemUser.roleTypes().contains(Roles.CUSTOMER_MANAGER)){
                customerManager = systemUser;
            }
        }

        Customer customer = new Customer(customerUser,code, address, customerManager);

        customer.setId(code);
        customer.hasIdentity(code);

        customerRepository.save(customer);

        return customer;
    }

    /**
     * register Admin
     */
    private void registerAdmin(final String email, final String password, final String firstName,
            final String lastName) {
        final Set<Role> roles = new HashSet<>();
        roles.add(Roles.ADMIN);

        registerUser(email, password, firstName, lastName,roles);
    }
}
