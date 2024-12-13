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
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class ListApplicationsByOpeningController {

    private final JobOpeningRepository jobOpeningRepo = PersistenceContext.repositories().jobOpenings();
    private final JobApplicationRepository jobApplicationRepo = PersistenceContext.repositories().jobApplications();

    /**
     * Display options.
     *
     * @param list the list
     */
    public void displayOptions(List<JobOpening> list) {
        int index = 1;
        for (JobOpening s: list){
            System.out.printf("%d - Job Opening:\n\n", index);
            System.out.println("\033[1mJob Reference:\033[0m " + s.jobReference().jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + s.numberOfVacancies().toString() + "\n\033[1mJob Function:\033[0m " + s.jobFunction().toString() +
                    "\n\033[1mMode:\033[0m " + s.mode() + "\n\033[1mContract Type:\033[0m " + s.contractType() +  "\n" + s.address() + "\n\033[1mCustomer (Company Name):\033[0m " + s.companyName().user().email() +
                    "\n\033[1mDescription:\033[0m " + s.description().toString() + "\n\033[1mRegistration Date:\033[0m " + s.registrationDate().getTime() + "\n\n\n" );
            index++;
        }
    }

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

    /**
     * Display applications.
     *
     * @param list the list
     */
    public void displayApplications(List<JobApplication> list) {
        if (!list.isEmpty()) {
            int index = 1;
            for (JobApplication s : list) {
                System.out.printf("%d - Job Application:\n %s\n\n", index, s.toString());
                index++;
            }
        }
    }
}
