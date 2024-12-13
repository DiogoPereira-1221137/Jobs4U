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
import lapr4.jobs4u.candidatemanagement.application.ListApplicationsByOpeningController;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.jobApplication.application.OrderCandidatesByGradeController;
import lapr4.jobs4u.jobApplication.domain.Grade;
import lapr4.jobs4u.jobopening.domain.JobOpening;

import java.util.List;
import java.util.Map;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class OrderCandidatesByGradeUI extends AbstractUI {


    private final OrderCandidatesByGradeController controller = new OrderCandidatesByGradeController();

    @Override
    protected boolean doShow() {
        List<JobOpening> openingList = controller.allActiveJobOpeningsByCustomerManager();
        int opt = -1;
        if (openingList.isEmpty()) {
            System.out.println(
                    "There are no Job Openings.");
            return false;
        }
        while (opt <= 0 | opt > openingList.size()){
            int index = 1;
            for (JobOpening s: openingList){
                System.out.printf("%d - Job Opening:\n\n", index);
                System.out.println("\033[1mJob Reference:\033[0m " + s.jobReference().jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + s.numberOfVacancies().toString() + "\n\033[1mJob Function:\033[0m " + s.jobFunction().toString() +
                        "\n\033[1mMode:\033[0m " + s.mode() + "\n\033[1mContract Type:\033[0m " + s.contractType() +  "\n" + s.address() + "\n\033[1mCustomer (Company Name):\033[0m " + s.companyName().user().email() +
                        "\n\033[1mDescription:\033[0m " + s.description().toString() + "\n\033[1mRegistration Date:\033[0m " + s.registrationDate().getTime() + "\n\n\n" );
                index++;
            }
            opt = Console.readInteger("Job Opening >");
            if (opt <= 0 | opt > openingList.size()) {
                System.out.println("Invalid option");
            }
        }
        Map<Candidate, Grade> candidateGradeMap = controller.getCandidatesAndGradesByJobOpening(openingList.get(opt-1));
        System.out.printf("Job Opening: %s\n\n", openingList.get(opt-1).jobReference());
        if(!candidateGradeMap.isEmpty()){
            Map<Candidate, Grade> candidateGradeMapOrdered = controller.OrderListCandidatesAndGrades(candidateGradeMap);
            displayCandidateGradeMap(candidateGradeMapOrdered);
        } else{
            System.out.println("Error!! The candidates' grades are not defined yet!");
        }
        return true;
    }

    public void displayCandidateGradeMap(Map<Candidate, Grade> candidateGradeMapOrdered){
        System.out.printf("%-20s%-30s%-10s%n%n", "Name", "Email", "Grade");

        for (Map.Entry<Candidate, Grade> entry : candidateGradeMapOrdered.entrySet()) {
            System.out.printf("%-20s%-30s%-10s%n", entry.getKey().user().name(), entry.getKey().user().email(), entry.getValue().grade());
        }
    }

    @Override
    public String headline() {
        return "List Ordered Candidates and grade by Opening";
    }
}
