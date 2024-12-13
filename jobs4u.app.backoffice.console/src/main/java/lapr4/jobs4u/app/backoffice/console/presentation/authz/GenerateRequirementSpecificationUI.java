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
package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.jobopening.application.GenerateInterviewModelController;
import lapr4.jobs4u.jobopening.application.GenerateRequirementSpecificationController;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Generate requirement specification ui.
 *
 * @author Fernando
 */
@SuppressWarnings("squid:S106")
public class GenerateRequirementSpecificationUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateRequirementSpecificationUI.class);

    private final GenerateRequirementSpecificationController theController = new GenerateRequirementSpecificationController();

    @Override
    protected boolean doShow() {
        final List<JobOpening> list = new ArrayList<>();
        final Iterable<JobOpening> iterable = this.theController.allJobOpenings();



        if (!iterable.iterator().hasNext()) {
            System.out.println("There is no JobOpening");
        } else {
            int cont = 1;
            System.out.println("SELECT JobOpening \n");
            System.out.printf("%-6s%-20s%-10s%-18s%-30s%-20s%-20s%-40s%n", "Nº:", "Job Function", "Mode", "Contract Type","Customer (Company Name)", "Job Reference", "Interview Model", "Requirement Specification");

            for (final JobOpening jobOpening : iterable) {
                list.add(jobOpening);

                String requirement;
                if (jobOpening.hasRequirementPlugin()) {
                    requirement = jobOpening.requirementsPlugin().pluginMainClassName().name();
                } else {
                    requirement = " ---------- ";
                }

                String interviewModel;
                if (jobOpening.hasInterviewModel()) {
                    interviewModel = jobOpening.interviewPlugin().pluginMainClassName().name();
                } else {
                    interviewModel = " ---------- ";
                }


                System.out.printf("%-6s%-20s%-10s%-18s%-30s%-20s%-20s%-40s%n", cont , jobOpening.jobFunction().toString() , jobOpening.mode(), jobOpening.contractType()
                        , jobOpening.companyName().user().name() , jobOpening.jobReference().toString(), interviewModel, requirement  );

                cont++;
            }

            int option = Console.readInteger("Enter JobOpening nº to select or 0 to finish ");
            if (option == 0) {
                System.out.println("No JobOpening selected");
            } else {
                try {

                    JobOpening jobOpeningSelected = list.get(option - 1);


                    this.theController.generateRequirementSpecificationTemplate(jobOpeningSelected);


                }  catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return true;
    }


    @Override
    public String headline() {
        return "Genereate Requirement Specification";
    }
}
