package lapr4.jobs4u.jobopening.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.application.JobApplicationService;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.JobApplicationDTO;
import lapr4.jobs4u.jobApplication.domain.JobApplicationResultDTO;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.apache.commons.lang3.SerializationUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ExecuteVerificationRequirementsJobOpeningController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobApplicationService service = new JobApplicationService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();


    public Iterable<JobOpening> allActiveJobOpeningsByCustomerManager() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();
        return this.jobOpeningRepository.allActiveJobOpeningsByCustomerManager(currentUser);
    }

//    public Iterable<JobOpening> allJobOpenings() {
//        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
//        return this.jobOpeningRepository.findAll();
//    }

    public Iterable<JobApplication> allWaitingApplicationsByJobOpening(JobOpening jobOpeningSelected) {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        return this.jobApplicationRepository.findWaitingApplicationsByJobOpening(jobOpeningSelected);
    }

    public List<ApplicationFile> filePathsRequirements(JobApplication jobApplication){
        return jobApplication.filePathsRequirements();
    }



    public Boolean verifyRequirements(String file, JobApplication jobApplication, JobOpening jobOpeningSelected) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        boolean message = false;

        Plugin requirementsPlugin = jobOpeningSelected.requirementsPlugin();
        File filePlugin = new File("plugins/"+requirementsPlugin.jarFileName().toString());
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass(requirementsPlugin.pluginMainClassName().name());
        Method method = jarFile.getMethod("verifyRequirements", String.class);

        try{
            if (!(boolean) method.invoke(null, file)){
                jobApplication.RejectApplication();
                jobApplicationRepository.save(jobApplication);
            }else{
                jobApplication.ApproveApplication();
                jobApplicationRepository.save(jobApplication);
                message = true;
            }
        } catch (InvocationTargetException ex) { ex.getCause(); }

        return message;

    }

    public String notifyCandidate(boolean message,EmailAddress candidateEmail, EmailAddress customerManagerEmail, JobOpening jobOpening) {
        String title = "";
        String notificationMessage = "";
        String subject = "Job Application State";
        if(message) {
            title = "Approved Application!!";
            notificationMessage = "Your application was approved for the job opening - " + jobOpening.description() + " reference - " + jobOpening.jobReference();
        } else {
            title = "Rejected Application!!";
            notificationMessage = "Your application was rejected for the job opening -" + jobOpening.description() + " reference - " + jobOpening.jobReference();
        }


        Notification notification = new Notification(customerManagerEmail, candidateEmail, title, subject , notificationMessage);

        notificationRepository.save(notification);

        return notificationMessage;
    }

    public Iterable<JobApplicationResultDTO> transformToDto(Iterable<JobApplication> jobApplicationList){
        return service.transformToResultDTO(jobApplicationList);
    }

    public boolean notifyResultByEmail(DataOutputStream output, DataInputStream input,  Iterable<JobApplicationResultDTO> jobApplications, EmailAddress publisherEmail) throws IOException {

        byte[] jobApplicationsData = SerializeUtil.serialize( jobApplications );
        byte[] publisherEmailData = SerializationUtils.serialize(publisherEmail);
        List<byte[]> bytesList = new ArrayList<>();

        bytesList.add(jobApplicationsData);
        bytesList.add(publisherEmailData);

        MessageUtil.writeMessage(output,19,bytesList);

        CustomMessage message = MessageUtil.readMessage(input);

        return (message.code == 20);
    }

    public JobOpening storeJobOpening(JobOpening jobOpeningSelected) {

        return jobOpeningRepository.save(jobOpeningSelected);
    }
}
