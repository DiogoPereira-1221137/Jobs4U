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

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.backoffice.console.ImportFolderReader;
import lapr4.jobs4u.candidatemanagement.application.ImportInterviewFileController;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;

import java.util.List;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class ImportInterviewFileUI extends AbstractUI {


//    private final ListApplicationsByOpeningController controller = new ListApplicationsByOpeningController();
    private final ImportInterviewFileController controller = new ImportInterviewFileController();
    private final ImportFolderReader folder = new ImportFolderReader();

    //    public static void main(final String[] args) {
//        ListApplicationsByOpeningUI a = new ListApplicationsByOpeningUI();
//        a.doShow();
//    }
    public void displayJO(List<JobOpening> list) {
        int index = 1;
        for (JobOpening s: list){
            System.out.printf("%d - Job Opening:\n\n", index);
            System.out.println("\033[1mJob Reference:\033[0m " + s.jobReference().jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + s.numberOfVacancies().toString() + "\n\033[1mJob Function:\033[0m " + s.jobFunction().toString() +
                    "\n\033[1mMode:\033[0m " + s.mode() + "\n\033[1mContract Type:\033[0m " + s.contractType() +  "\n" + s.address() + "\n\033[1mCustomer (Company Name):\033[0m " + s.companyName().user().email() +
                    "\n\033[1mDescription:\033[0m " + s.description().toString() + "\n\033[1mRegistration Date:\033[0m " + s.registrationDate().getTime() + "\n\n\n" );
            index++;
        }
    }
    public void displayJA(List<JobApplication> list) {
        int index = 1;
        for (JobApplication s : list) {
            System.out.printf("%d - Job Application:\n %s\n\n", index, s.toString());
            index++;
        }
    }
    @Override
    protected boolean doShow() {
        List<String> interviewFiles = folder.getFilesFromImportFolder();
        if (interviewFiles.isEmpty()) {
            System.out.println(
                    "There are no Files to Import.");
            return false;
        }

        List<JobOpening> openingList = controller.getJobOpenings();
        int opt = -1;
        if (openingList.isEmpty()) {
            System.out.println(
                    "There are no Job Openings.");
            return false;
        }
        while (opt <= 0 | opt > openingList.size()){
            displayJO(openingList);
            opt = Console.readInteger("Job Opening >");
            if (opt <= 0 | opt > openingList.size()) {
                System.out.println("Invalid option");
            }
        }
        JobOpening opening = openingList.get(opt-1);
        List<JobApplication> list = controller.getJobApplicationsByOpening(opening);
        if (list.isEmpty()) {
            System.out.println(
                    "There are no Job Applications for this Job Opening.");
            return false;
        }
        opt = -1;
        while (opt <= 0 | opt > list.size()){
            displayJA(list);
            opt = Console.readInteger("Job Application >");
            if (opt <= 0 | opt > list.size()) {
                System.out.println("Invalid option");
            }
        }
        JobApplication application = list.get(opt-1);

        opt = -1;
        while (opt <= 0 | opt > interviewFiles.size()){
            displayFile(interviewFiles);
            opt = Console.readInteger("Interviews File >");
            if (opt <= 0 | opt > list.size()) {
                System.out.println("Invalid option");
            }
        }
        String file = interviewFiles.get(opt-1);

        try {
            if (controller.validateAndAddFile("import/"+file, application, opening)) {
                System.out.println("File syntax is correct. File was added to Application.");
                return true;
            }
            System.out.println("File syntax is incorrect or file could not added to Application.");
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void displayFile(List<String> list) {
        int index = 1;
        for (String s : list) {
            System.out.printf("%d - File:\n %s\n\n", index, s);
            index++;
        }
    }

    @Override
    public String headline() {
        return "Upload Interview File";
    }
}
