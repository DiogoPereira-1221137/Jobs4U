/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.candidatemanagement.domain.PhoneNumber;
import lapr4.jobs4u.jobApplication.application.RegisterJobApplicationController;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class RegisterJobApplicationUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterJobApplicationUI.class);

    //private final AddUserController theController = new AddUserController();

    private final RegisterJobApplicationController theControllerJobApplication = new RegisterJobApplicationController();


    @Override
    protected boolean doShow() {

        try {
            List<String> FoldersName = theControllerJobApplication.getFolders();

            String folder = selectFolder(FoldersName);

            List<ApplicationFile> files;
            try {
                files = theControllerJobApplication.getFilesName(folder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String fileName = "candidate-data";
            Optional<String> filePathCandidateData = files.stream()
                    .map(ApplicationFile::filePath)
                    .filter(filePath -> filePath.contains(fileName))
                    .findFirst();

            if (filePathCandidateData.isPresent()){
                File file = new File(filePathCandidateData.get());

                List<String> candidateData = RegisterJobApplicationController.readFileContent(file);
                List<String> name = List.of(candidateData.get(2).split(" "));
                PhoneNumber candidatePhoneNumber = new PhoneNumber(candidateData.get(3));

                Iterable<JobOpening> listJobOpenings = theControllerJobApplication.findAllJobOpenings(EmailAddress.valueOf((candidateData.get(1))));

                if(!listJobOpenings.iterator().hasNext()) {

                    System.out.println("\nThe candidate has already been associated with all the job openings registered in the system.");

                }else{

                    JobOpening jobOpening = selectJobOpening(listJobOpenings);

                    List<ApplicationFile> filesName = new ArrayList<>(files);

                    if(jobOpening != null){
                        JobApplication jobApplication = theControllerJobApplication.registerJobApplication(EmailAddress.valueOf((candidateData.get(1))), name.get(0), name.get(1) ,candidatePhoneNumber, jobOpening, filesName);

                        if(jobApplication != null){
                            System.out.println("\n==========Job Application Registered Successfully!==========");
                            System.out.println("\033[1mCandidate email:\033[0m " + jobApplication.Candidate().getId() +
                                    "\n\033[1mCandidate name:\033[0m " + jobApplication.Candidate().user().name().toString() +
                                    "\n\033[1mStatus:\033[0m "+ jobApplication.status().toString() +
                                    "\n\033[1mFilesPaths Info:\033[0m " + jobApplication.filePathsInfo().toString());

                            if (jobApplication.filePathsInterview().size() == 0) System.out.println("\033[1mFilesPaths Interview Plugin:\033[0m" + " There are no associated files!");
                            else System.out.println("\033[1mFilesPaths Interview Plugin:\033[0m " + jobApplication.filePathsInterview().toString());

                            if (jobApplication.filePathsRequirements().size() == 0) System.out.println("\033[1mFilesPaths Requirement Plugin:\033[0m" + " There are no associated files!");
                            else System.out.println("\033[1mFilesPaths Requirement Plugin:\033[0m " + jobApplication.filePathsRequirements().toString());


                        }
                    }
                }
            }else {
                System.out.println("Candidate-data file is not present in the folder!!");
            }


        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }

        return true;
    }


    private String selectFolder(List<String> foldersName) {
        Scanner scanner = new Scanner(System.in);

        if(foldersName.isEmpty()){
            String message = "There are no folders!";
            System.out.println(message);

            return message;
        }

        System.out.println("\nChoose a folder:");
        int index = 1;
        for (String folder : foldersName) {
            System.out.println(index + " - " + folder);
            index++;
        }


        int choice;
        do {
            System.out.print("Folder Name: ");
            choice = scanner.nextInt();
            if(choice == 0) return null;
            if (choice < 1 || choice > index - 1) {
                System.out.println("Invalid option. Please choose a number within the valid range.");
            }
        } while (choice < 1 || choice > index - 1);

        index = 1;
        for (String FolderName : foldersName) {
            if (index == choice) {
                return FolderName;
            }
            index++;
        }

        return null;

    }

    private JobOpening selectJobOpening(Iterable<JobOpening> jobOpenings) {

        Scanner scanner = new Scanner(System.in);


        System.out.println("\nChoose a job opening:");
        int index = 1;
        for (JobOpening jobOpening : jobOpenings) {
            System.out.println(index + ": Job Reference - " + jobOpening.jobReference().toString() + "  Customer email - " + jobOpening.companyName().user().email());
            index++;
        }


        int choice;
        do {
            System.out.print("Job Opening: ");
            choice = scanner.nextInt();
            if(choice == 0) return null;
            if (choice < 1 || choice > index - 1) {
                System.out.println("Invalid option. Please choose a number within the valid range.");
            }
        } while (choice < 1 || choice > index - 1);


        index = 1;
        for (JobOpening jobOpening : jobOpenings) {
            if (index == choice) {
                return jobOpening;
            }
            index++;
        }

        return null;
    }



    @Override
    public String headline() {
        return "Register Application";
    }
}
