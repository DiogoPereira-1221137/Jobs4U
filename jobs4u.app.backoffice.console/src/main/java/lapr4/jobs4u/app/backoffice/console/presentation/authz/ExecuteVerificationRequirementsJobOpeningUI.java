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

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.jobApplication.domain.*;
import lapr4.jobs4u.jobopening.application.ExecuteVerificationRequirementsJobOpeningController;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Generate requirement specification ui.
 *
 * @author Fernando
 */
@SuppressWarnings("squid:S106")
public class ExecuteVerificationRequirementsJobOpeningUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteVerificationRequirementsJobOpeningUI.class);

    private final ExecuteVerificationRequirementsJobOpeningController theController = new ExecuteVerificationRequirementsJobOpeningController();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();


    @Override
    protected boolean doShow() {
        final List<JobOpening> list = new ArrayList<>();
        //final Iterable<JobOpening> iterable = this.theController.allActiveJobOpeningsByCustomerManager();

        final Iterable<JobOpening> jobOpeningList = this.theController.allActiveJobOpeningsByCustomerManager();



        if (!jobOpeningList.iterator().hasNext()) {
            System.out.println("There is no active JobOpening!");
        } else {
            int cont = 1;
            System.out.println("SELECT JobOpening \n");
            System.out.printf("\n%-6s%-20s%-10s%-18s%-30s%-20s%-20s", "Nº:", "Job Function", "Mode", "Contract Type","Customer (Company Name)", "Job Reference", "Current Phase ");

            for (final JobOpening jobOpening : jobOpeningList) {
                if(jobOpening.currentPhase().equals(PhaseNames.SCREENING)) {

                    list.add(jobOpening);

                    System.out.printf("\n%-6s%-20s%-10s%-18s%-30s%-20s%-20s", cont, jobOpening.jobFunction().toString(), jobOpening.mode(), jobOpening.contractType()
                            , jobOpening.companyName().user().name(), jobOpening.jobReference().toString(), jobOpening.currentPhase());

                    cont++;
                }
            }
            if(list.isEmpty()){
                System.out.println("\nNone of the job opening are in the screening phase!");
            } else {

                int option = Console.readInteger("\nEnter JobOpening nº to select or 0 to finish ");
                if (option == 0) {
                    System.out.println("No JobOpening selected");
                } else {
                    try {

                        JobOpening jobOpeningSelected = list.get(option - 1);

                        final Iterable<JobApplication> jobApplicationsList = this.theController.allWaitingApplicationsByJobOpening(jobOpeningSelected);

                        if(!jobApplicationsList.iterator().hasNext()) System.out.println("\nThere aren´t applications to be verified!");
                        else {
                            int counter = 1;
                            for (final JobApplication jobApplication : jobApplicationsList) {
                                if(jobApplication.filePathsRequirements().isEmpty()) continue;

                                if(jobApplication.status().equals(StatusType.APPROVED)) System.out.println("Application wsa already verified: candidate %s" +jobApplication.Candidate().user().name().toString() + "; status: %s" + jobApplication.status().toString());
                                else{

                                    System.out.println("\n" + counter + " > Job Application");
                                    System.out.println("\033[1mCandidate email:\033[0m " + jobApplication.Candidate().getId() +
                                            "\n\033[1mCandidate name:\033[0m " + jobApplication.Candidate().user().name().toString() +
                                            "\n\033[1mStatus:\033[0m " + jobApplication.status().toString() +
                                            "\n\033[1mFilesPaths Info:\033[0m " + jobApplication.filePathsInfo().toString());


                                    List<ApplicationFile> files = this.theController.filePathsRequirements(jobApplication);


                                    boolean message = false;

                                    if (files.size() != 0) {
                                            String file = files.get(files.size()-1).filePath();

                                            try {
                                                message = this.theController.verifyRequirements(file, jobApplication, jobOpeningSelected);
                                            } catch (Exception e) {
                                                throw new RuntimeException(e);
                                            }


                                        EmailAddress customerManagerEmail = jobApplication.JobOpening().companyName().customerManagerAssociated().email();

                                        EmailAddress candidateEmail = jobApplication.Candidate().getId();

                                        String notificationMessage = theController.notifyCandidate(message,candidateEmail, customerManagerEmail, jobOpeningSelected);

                                        System.out.println("\nNotification message: " + notificationMessage);

                                        System.out.println("\n\033[1mStatus:\033[0m " + jobApplication.status().toString());


                                    }

                                    counter++;

                                }
                            }

                            Iterable<JobApplicationResultDTO> listJobApplicationsDTO =theController.transformToDto(jobApplicationsList);
                            boolean phase = false;

                            Socket socket = null;
                            DataOutputStream output = null;
                            DataInputStream input = null;
                            try{

                                socket = new Socket("localhost", 12345);

                                output = new DataOutputStream(socket.getOutputStream());

                                input = new DataInputStream(socket.getInputStream());

                                doAuth(input,output);

                                phase = theController.notifyResultByEmail(output, input ,listJobApplicationsDTO, authz.session().get().authenticatedUser().email());
                                System.out.println("Email Notifications were sent with success");
                            } catch (Exception e) {
                                System.out.println("Unable to connect to the server");
                            }
                            if (!phase){
                                System.out.println("Something wasn't right in the server or with the data sent ");
                                return false;
                            }
                            MessageUtil.writeMessage(output,1,new ArrayList<>());
                            MessageUtil.readMessage(input);
                            socket.close();

                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
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
        return "Generate Requirement Specification";
    }
}
