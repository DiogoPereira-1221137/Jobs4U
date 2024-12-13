package lapr4.jobs4u.app.customer.console.presentation.myuser;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The type List job opening ui.
 */
public class CommTestUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommTestUI.class);

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final CustomerRepository customerRepository = PersistenceContext
            .repositories().customers();
    private static Socket socket;

    public CommTestUI(Socket socket) {
        CommTestUI.socket = socket;
    }
@Override
    protected boolean doShow() {

        try {

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            MessageUtil.writeMessage(output, FollowUpServer.COMMTEST, new ArrayList<>());

            CustomMessage message = MessageUtil.readMessage(input);

                if (message.code == FollowUpServer.ACK) {
                    System.out.println("Connection Stable.");

                } else {
                    System.out.println("Received unknown command response from server");
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public String headline() {
        return "Test Connection to Server";
    }


}
