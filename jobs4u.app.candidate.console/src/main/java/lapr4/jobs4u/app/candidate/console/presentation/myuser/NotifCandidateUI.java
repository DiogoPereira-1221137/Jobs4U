package lapr4.jobs4u.app.candidate.console.presentation.myuser;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.candidate.console.application.NotifCandidateController;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The type List job opening ui.
 */
public class NotifCandidateUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifCandidateUI.class);

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final CustomerRepository customerRepository = PersistenceContext
            .repositories().customers();

    private final NotifCandidateController controller;


    private static Socket socket;

    public NotifCandidateUI(Socket socket) throws IOException {
        NotifCandidateUI.socket = socket;
        controller = new NotifCandidateController(socket);
    }



    @Override
    protected boolean doShow() {

        try {
            CustomMessage message = controller.notifyCandidateReq();

            //MessageUtil.writeMessage(output, FollowUpServer.REQ_NOTFLIST, new ArrayList<>());

           // CustomMessage message = MessageUtil.readMessage(input);

            List<NotificationDTO> myNotifications = SerializeUtil.deserializeList(message.data.get(0));
            int count = 1;
            if(myNotifications.isEmpty()) System.out.println("\nYou don't have any notifications!");
            else{
                for (NotificationDTO n: myNotifications) {
                    String read = n.status()? "": " \u001B[32m[NEW]\u001B[0m";
                    System.out.println(count+"> Subject: "+n.subject()+"> Body: "+n.body()+read);
                    count++;
                }
                System.out.println("\n\n0> Exit");
                int select = -1;
                Scanner scanner = new Scanner(System.in);
                while (select < 0 || select > myNotifications.size()) {
                    System.out.print("\nSelect a Notification:");
                    try {
                        select = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                    if(select < 0 || select > myNotifications.size()){
                        System.out.print("Invalid Option\n");
                    }
                }
                if (select != 0) {
                    NotificationDTO selected = myNotifications.get(select-1);
                    if (selected.status()) {
                        System.out.println("Already marked as Read");
                    } else {
//                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//                    DataInputStream input = new DataInputStream(socket.getInputStream());


                        //MessageUtil.writeMessage(output, FollowUpServer.REQ_MARKREAD, new ArrayList<>(Collections.singleton(SerializeUtil.serialize(selected))));

                        message = controller.markNotification(selected);


                        //  message =  MessageUtil.readMessage(input);

                        if (message.code == FollowUpServer.ERR) {
                            System.out.println("Could not mark notification as Read");
                        } else {
                            System.out.println("Notification marked as Read");
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public String headline() {
        return "Notifications";
    }


}
