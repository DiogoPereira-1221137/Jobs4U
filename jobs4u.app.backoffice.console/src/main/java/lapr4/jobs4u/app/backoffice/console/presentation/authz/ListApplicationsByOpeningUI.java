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
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.application.ConfigurePluginController;
import lapr4.jobs4u.candidatemanagement.application.ListApplicationsByOpeningController;
import lapr4.jobs4u.candidatemanagement.domain.Description;
import lapr4.jobs4u.candidatemanagement.domain.JarFileName;
import lapr4.jobs4u.candidatemanagement.domain.PluginType;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.domain.JobOpening;

import java.io.IOException;
import java.util.List;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class ListApplicationsByOpeningUI extends AbstractUI {


    private final ListApplicationsByOpeningController controller = new ListApplicationsByOpeningController();
//    public static void main(final String[] args) {
//        ListApplicationsByOpeningUI a = new ListApplicationsByOpeningUI();
//        a.doShow();
//    }
    @Override
    protected boolean doShow() {
        List<JobOpening> openingList = controller.getJobOpenings();
        int opt = -1;
        if (openingList.isEmpty()) {
            System.out.println(
                    "There are no Job Openings.");
            return false;
        }
        while (opt <= 0 | opt > openingList.size()){
            controller.displayOptions(openingList);
            opt = Console.readInteger("Job Opening >");
            if (opt <= 0 | opt > openingList.size()) {
                System.out.println("Invalid option");
            }
        }
        controller.displayApplications(controller.getJobApplicationsByOpening(openingList.get(opt-1)));
        return true;
    }

    @Override
    public String headline() {
        return "List Applications by Opening";
    }
}
