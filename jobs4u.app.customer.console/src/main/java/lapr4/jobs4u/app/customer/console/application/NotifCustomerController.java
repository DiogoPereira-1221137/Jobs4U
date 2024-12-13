package lapr4.jobs4u.app.customer.console.application;

import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.notification.domain.NotificationDTO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotifCustomerController {

    private static Socket socket;

    private final DataOutputStream output;

    private final DataInputStream input;


    public NotifCustomerController(Socket socket) throws IOException {
        NotifCustomerController.socket = socket;
        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(socket.getInputStream());
    }



    public List<NotificationDTO> notifyReq() throws IOException {
        MessageUtil.writeMessage(output, FollowUpServer.REQ_NOTFLIST, new ArrayList<>());
        CustomMessage message =MessageUtil.readMessage(input);
        return SerializeUtil.deserializeObject(message.data.get(0));

    }


    public CustomMessage maskAsReadRep(NotificationDTO selected) throws IOException {
        MessageUtil.writeMessage(output, FollowUpServer.REQ_MARKREAD, new ArrayList<>(Collections.singleton(SerializeUtil.serialize(selected))));

        return MessageUtil.readMessage(input);
    }
}
