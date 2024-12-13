package lapr4.jobs4u.app.customer.console.presentation;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.customer.console.application.ListJobOpeningAppController;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.PhaseNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.util.*;

/**
 * The type List job opening ui.
 */
public class ListJobOpeningAppUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListJobOpeningAppUI.class);

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListJobOpeningAppController thisController = new ListJobOpeningAppController();


    @Override
    protected boolean doShow() {

        try {
            Socket socket = new Socket("localhost", 12345);

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());




            // Receber a resposta do servidor
            CustomMessage message = thisController.fetchJobOpenings(input,output);


                if (message.code == FollowUpServer.RESP_JOBOP) {
                    List<JobOpening> jobOpenings = SerializeUtil.deserializeList(message.data.get(0));

                    if (!jobOpenings.iterator().hasNext()) {
                        System.out.println("There is no JobOpening");
                        return false;
                    }

                    List<JobOpening> jobList = new ArrayList<>(jobOpenings);

                    jobList.sort(Comparator.comparingInt(o -> o.identity().jobReference()));



                    for (JobOpening jobOpening : jobList) {

                        byte[] jobOpeningData = SerializeUtil.serialize(jobOpening);

                        // Enviar a mensagem no formato especificado

                        // Receber a resposta do servidor
                        CustomMessage message2 = thisController.howMuchJobApplications(input,output,jobOpeningData);;

                        if (message2.code == FollowUpServer.RESP_HMUCHJOBAPP) {


                            try{


                            Integer numberOfApplication = SerializeUtil.deserializeObject(message2.data.get(0));


                            System.out.println("\033[1mJob Reference:\033[0m " + jobOpening.jobReference().jobReference() +
                                "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpening.numberOfVacancies() +
                                "\n\033[1mJob Function:\033[0m " + jobOpening.jobFunction() +
                                "\n\033[1mMode:\033[0m " + jobOpening.mode() +
                                "\n\033[1mContract Type:\033[0m " + jobOpening.contractType() +
                                "\n" + jobOpening.address() +
                                "\n\033[1mCustomer (Company Name):\033[0m " + jobOpening.companyName().user().email() +
                                "\n\033[1mDescription:\033[0m " + jobOpening.description() +
                                "\n\033[1mRegistration Date:\033[0m " + jobOpening.registrationDate().getTime() +
                                "\n\033[1mNumber of Applications:\033[0m " + numberOfApplication +
                                "\n\033[1mCurrent State:\033[0m " + jobOpening.jobOpeningStatus() +
                                "\n\033[1mActive Since:\033[0m " + ( !jobOpening.isActive()  ? "N/A":
                                (jobOpening.recruitmentProcess().DateSpecificPhase(PhaseNames.APPLICATION) != null ?
                                        jobOpening.recruitmentProcess().DateSpecificPhase(PhaseNames.APPLICATION).startDate().getTime() : "N/A") ) +
                                "\n");

                                } catch (Exception e){
                                e.printStackTrace();
                                System.out.println("This jobOpening is missing some essential attribute");
                                return false;
                            }

                        } else {
                            System.out.println("Received unknown command response from server");
                        }
                    }
                } else {
                    System.out.println("Received unknown command response from server");
                }

            socket.close();
        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Unable to connect to the server");
            return false;
        }

        return false;
    }

    @Override
    public String headline() {
        return "List Job Openings";
    }


}
