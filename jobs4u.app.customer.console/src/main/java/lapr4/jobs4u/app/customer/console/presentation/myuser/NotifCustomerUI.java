package lapr4.jobs4u.app.customer.console.presentation.myuser;

import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.customer.console.application.NotifCustomerController;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * The type List job opening ui.
 */
public class NotifCustomerUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifCustomerUI.class);

//    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final NotifCustomerController controller;
    private static Socket socket;

    public NotifCustomerUI(Socket socket) throws IOException {
        NotifCustomerUI.socket = socket;
        controller = new NotifCustomerController(socket);
    }

    @Override
    protected boolean doShow() {

        try {

//            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
//            DataInputStream input = new DataInputStream(socket.getInputStream());
//            MessageUtil.writeMessage(output, FollowUpServer.REQ_NOTFLIST, new ArrayList<>());

            List<NotificationDTO> myNotifications =  controller.notifyReq();
            int count = 1;
            if(myNotifications.isEmpty()) System.out.println("\nYou don't have any notifications!");
            else {
                for (NotificationDTO n : myNotifications) {
                    String read = n.status() ? "" : " \u001B[32m[NEW]\u001B[0m";
                    System.out.println(count + "> Subject: " + n.subject() + "> Body: " + n.body() + read);
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
                    if (select < 0 || select > myNotifications.size()) {
                        System.out.print("Invalid Option\n");
                    }
                }
                if (select != 0) {
                    NotificationDTO selected = myNotifications.get(select - 1);
                    if (selected.status()) {
                        System.out.println("Already marked as Read");
                    } else {
                        CustomMessage message = controller.maskAsReadRep(selected);
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
        return "My Notifications";
    }


}
