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
package lapr4.jobs4u.candidatemanagement.application;

import eapli.framework.application.UseCaseController;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.Types;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class ImportRequirementsFileController {

    private final JobOpeningRepository jobOpeningRepo = PersistenceContext.repositories().jobOpenings();
    private final JobApplicationRepository jobApplicationRepo = PersistenceContext.repositories().jobApplications();


    /**
     * Gets job openings.
     *
     * @return the job openings
     */
    public List<JobOpening> getJobOpenings() {
        return new ArrayList<>((Collection<? extends JobOpening>) jobOpeningRepo.jobOpenings());
    }

    /**
     * Gets job applications by opening.
     *
     * @param opening the opening
     * @return the job applications by opening
     */
    public List<JobApplication> getJobApplicationsByOpening(JobOpening opening) {
        Iterable<JobApplication> list = jobApplicationRepo.findByJobOpening(opening);
        List<JobApplication> jobApplications = new ArrayList<>();
        jobApplications.addAll((Collection<? extends JobApplication>) list);
        return jobApplications;
    }

    public boolean validateAndAddFile(String file, JobApplication jobApplication, JobOpening jobOpening) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        boolean valid = false;
        Plugin requirementsPlugin = jobOpening.requirementsPlugin();
        File filePlugin = new File("plugins/"+requirementsPlugin.jarFileName().toString());
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass(requirementsPlugin.pluginMainClassName().name());
        Method method = jarFile.getMethod("antlr", String.class);
//        File fileTrue = new File(file);
        try{
        if (!(boolean) method.invoke(null, file)){
            return valid;
        }
    } catch (InvocationTargetException ex) { ex.getCause(); }

        valid = jobApplication.addFilePath(new ApplicationFile(Types.REQUIREMENTS, file, new File(file)));
        jobApplicationRepo.save(jobApplication);

        return valid;
    }
}
