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
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplicationDTO;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.rank.application.PublishResultsController;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class PublishResultsUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishResultsUI.class);
    private final PublishResultsController theControllerPublish = new PublishResultsController();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    protected boolean doShow() {
        final List<JobOpening> list = new ArrayList<>();
        Iterable<JobOpening> listJobOpenings = theControllerPublish.listAllJobOpenings();

        int option;


        if (!listJobOpenings.iterator().hasNext()) {
            System.out.println("There is no JobOpening");
            return false;
        }

        while (true) {

            System.out.println("\nSelect a Job Opening");
            System.out.printf("%-6s%-20s%-10s%-18s%-30s%-20s%-20s%n", "Nº:", "Job Function", "Mode", "Contract Type", "Customer (Company Name)", "Job Reference", "Current State");

            int cont = 1;
            for (final JobOpening jobOpening : listJobOpenings) {
                list.add(jobOpening);

                System.out.printf("%-6s%-20s%-10s%-18s%-35s%-15s%-15s%n", cont, jobOpening.jobFunction().toString(), jobOpening.mode(), jobOpening.contractType()
                        , jobOpening.companyName().user().name(), jobOpening.jobReference().toString(), jobOpening.currentPhase());

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

        if (jobOpeningSelected.currentPhase() != PhaseNames.ANALYSIS){
            System.err.println("The current phase needs to be 'Analysis' ");
            return false;

        }

        Iterable<JobApplicationDTO> listJobApplicationsDTO = theControllerPublish.findCandidates(jobOpeningSelected);

        try {

        if (!listJobApplicationsDTO.iterator().hasNext()) {
            System.out.println("There is no JobApplication associated to this JobOpening");
            return false;
        }



            List<Id> ids = StreamSupport.stream(listJobApplicationsDTO.spliterator(), false)
                .map(JobApplicationDTO::identity)
                .collect(Collectors.toList());



        List<Id> rankSavedList;

        try{

        rankSavedList = (List<Id>) theControllerPublish.findRankByJobReference(jobOpeningSelected.jobReference());


        Set<Id> jobApplicationsSet = new HashSet<>(ids);

        Set<Id> ranksSet = new HashSet<>();
        rankSavedList.forEach(ranksSet::add);

        if (ranksSet.containsAll(jobApplicationsSet)) {
            System.out.println("All job applications are present in the ranks.");
        } else {
            System.out.println("Some job applications are missing in the ranks.");
            return false;
        }


        } catch (NoSuchElementException e) {
            System.out.println("There is no Rank associated to this JobOpening");
            return false;
        }


            List<JobApplicationDTO> publishResults = new ArrayList<>();

            for (int i = 0; i < rankSavedList.size(); i++) publishResults.add(null);

            for ( JobApplicationDTO job : listJobApplicationsDTO ) publishResults.set(rankSavedList.indexOf(job.identity()),job);

            boolean phase = false;

            Socket socket = null;
            DataOutputStream output = null;
            DataInputStream input = null;
            try{

            socket = new Socket("localhost", 12345);

            output = new DataOutputStream(socket.getOutputStream());

            input = new DataInputStream(socket.getInputStream());

            doAuth(input,output);





             phase = theControllerPublish.publishResults(output,input,publishResults,jobOpeningSelected.numberOfVacancies().vacancies(),jobOpeningSelected.companyName().user().email(),authz.session().get().authenticatedUser().email());

            } catch (Exception e) {

                System.out.println("Unable to connect to the server");

            }


            if (!phase){

                System.out.println("Something wasn't right in the server or with the data sent ");
                return false;
            }

            jobOpeningSelected.setPhase(PhaseNames.RESULT);
            jobOpeningSelected.closed();

            theControllerPublish.storeJobOpening(jobOpeningSelected);
            System.out.println("JobOpening updated to closed and notifications created");



            MessageUtil.writeMessage(output,1,new ArrayList<>());
            MessageUtil.readMessage(input);
            socket.close();

        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return true;
    }

    private void doAuth(DataInputStream input, DataOutputStream output) throws IOException {

        List<byte[]> bytesList = new ArrayList<>();

        bytesList.add(authz.session().get().authenticatedUser().email().toString().getBytes());
        bytesList.add(String.format("#Password1").getBytes());
        bytesList.add(Roles.CUSTOMER_MANAGER.toString().getBytes());

        MessageUtil.writeMessage(output,4,bytesList);

        MessageUtil.readMessage(input);

    }

    @Override
    public String headline() {
        return "Publish Results";
    }
}
