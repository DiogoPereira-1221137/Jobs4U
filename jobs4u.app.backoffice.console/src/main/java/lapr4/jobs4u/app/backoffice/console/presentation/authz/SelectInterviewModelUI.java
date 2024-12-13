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

import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.application.SelectInterviewModelController;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The type Select interview model ui.
 *
 * @author Fernando
 */
@SuppressWarnings("squid:S106")
public class SelectInterviewModelUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectInterviewModelUI.class);

    private final SelectInterviewModelController controller = new SelectInterviewModelController();

    @Override
    protected boolean doShow() {
        final List<JobOpening> list = new ArrayList<>();
        final Iterable<JobOpening> jobOpenings = this.controller.allJobOpenings();


        if (!jobOpenings.iterator().hasNext()) {
            System.out.println("There is no JobOpening");
            return false;
        }

        int option;
        while (true) {

            System.out.println("\nSelect a Job Opening");
            System.out.printf("%-6s%-20s%-10s%-18s%-30s%-20s%-40s%n", "Nº:", "Job Function", "Mode", "Contract Type", "Customer (Company Name)", "Job Reference", "Interview Model");

            int cont = 1;
            for (final JobOpening jobOpening : jobOpenings) {
                list.add(jobOpening);

                String interviewModel;
                if (jobOpening.hasInterviewModel()) {
                    interviewModel = jobOpening.interviewPlugin().pluginMainClassName().name();
                } else {
                    interviewModel = " ---------- ";
                }

                System.out.printf("%-6s%-20s%-10s%-18s%-35s%-15s%-40s%n", cont, jobOpening.jobFunction().toString(), jobOpening.mode(), jobOpening.contractType()
                        , jobOpening.companyName().user().name(), jobOpening.jobReference().toString(), interviewModel);

                cont++;
            }

            option = Console.readInteger("Enter JobOpening nº to select or 0 to finish ");
            if (option == 0) {
                System.out.println("No JobOpening selected");
                return false;
            } else if (option < 0 || option > list.size()) {
                System.out.println("Invalid option! Please enter a number within the range.");
            } else {
                break;
            }

        }

        JobOpening jobOpeningSelected = list.get(option - 1);

        return definePlugin(jobOpeningSelected);

    }

    public boolean definePlugin(JobOpening jobOpeningSelected) {
        final Iterable<Plugin> plugins = this.controller.allInterviewPlugin();
        final List<Plugin> listPlugin = new ArrayList<>();
        int option;
        if (!plugins.iterator().hasNext()) {
            System.out.println("There is no Interview Model Plugin");
            return false;
        }

        while (true) {

            System.out.println("\nSelect the Interview Model Plugin");
            System.out.printf("%-6s%-35s%-35s%-35s%n", "Nº:", "Plugin Class Name", "Jar File Name", "Description");

            int cont = 1;
            for (final Plugin plugin : plugins) {
                listPlugin.add(plugin);

                System.out.printf("%-6s%-35s%-35s%-35s%n", cont, plugin.pluginMainClassName().name(), plugin.jarFileName().toString(), plugin.description().toString());

                cont++;
            }

            option = Console.readInteger("Enter Interview Model nº to select or 0 to finish ");

            if (option == 0) {
                System.out.println("No Interview Model selected");
                return false;
            } else if (option < 0 || option > listPlugin.size()) {
                System.out.println("Invalid option! Please enter a number within the range.");
            } else {
                break;
            }
        }
        Plugin pluginSelected = listPlugin.get(option - 1);

        try {
            this.controller.putInterviewModel(jobOpeningSelected, pluginSelected);

            System.out.printf("%-35s%-35s%-35s%-35s%n", "Job Function", "Customer (Company Name)", "Plugin Class Name", "Jar File Name");
            System.out.printf("%-35s%-35s%-35s%-35s%n", jobOpeningSelected.jobFunction().toString(), jobOpeningSelected.companyName().user().name(), pluginSelected.pluginMainClassName().name(), pluginSelected.jarFileName().toString());

        } catch (IntegrityViolationException | ConcurrencyException ex) {
            LOGGER.error("Error performing the operation", ex);
            System.out.println("Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }


        return true;
    }


    @Override
    public String headline() {
        return "Select Interview Model";
    }


}
