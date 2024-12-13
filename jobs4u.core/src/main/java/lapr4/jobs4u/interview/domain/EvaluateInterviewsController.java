package lapr4.jobs4u.interview.domain;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.Grade;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EvaluateInterviewsController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext.repositories().jobApplications();

    public List<JobOpening> findAllActiveJobOpeningsWithInterview(){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        SystemUser currentUser= authz.session().get().authenticatedUser();
        Iterable<JobOpening> jobOpenings =jobOpeningRepository.findAllActiveJobOpeningsInInterviewPhaseByCustomerManager(currentUser);
        return new ArrayList<>((Collection<? extends JobOpening>) jobOpenings);
    }

    public List<JobApplication> findAllAcceptedJobApplicationsWithAnswersFileByJobOpening(JobOpening selectedJobOpening){
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);
        Iterable<JobApplication> jobApplications =jobApplicationRepository.findAcceptedApplicationsByJobOpening(selectedJobOpening);
        return new ArrayList<>((Collection<? extends JobApplication>) jobApplications);
    }

    public String evaluateInterview(JobOpening selectedJobOpening, JobApplication jobApplication, String interviewFilePath) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        Plugin interviewPlugin = selectedJobOpening.interviewPlugin();
        File filePlugin = new File("plugins/" + interviewPlugin.jarFileName().toString());
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass(interviewPlugin.pluginMainClassName().name());
        Method method = jarFile.getMethod("evaluateInterview", String.class);
        Integer grade = (Integer) method.invoke(null,interviewFilePath);
        System.out.println("grade: " +grade);
        String message;
        if (grade < 0){
            message = "Application was not graded!!";
        }else{
            Grade jobApplicationGrade = new Grade(grade);
            jobApplication.grade(jobApplicationGrade);
            jobApplicationRepository.save(jobApplication);
            message = "Application was graded!!";
        }

        return message;
    }

}
