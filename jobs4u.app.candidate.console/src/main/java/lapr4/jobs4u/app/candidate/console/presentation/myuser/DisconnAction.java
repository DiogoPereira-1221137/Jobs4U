package lapr4.jobs4u.app.candidate.console.presentation.myuser;

import eapli.framework.actions.Action;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The type List job opening ui.
 */
public class DisconnAction implements Action {
    private final String message;

    private static Socket socket;

    public DisconnAction(Socket socket, String message) {
        DisconnAction.socket = socket;
        this.message = message;
    }

    public boolean execute() {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            MessageUtil.writeMessage(output, FollowUpServer.DISCONN, new ArrayList<>());
            CustomMessage message = MessageUtil.readMessage(input);
            if (message.code == FollowUpServer.ACK) {
                System.out.println(this.message);
                socket.close();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
