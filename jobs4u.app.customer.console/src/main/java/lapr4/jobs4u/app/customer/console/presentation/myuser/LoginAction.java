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
package lapr4.jobs4u.app.customer.console.presentation.myuser;

import eapli.framework.actions.Action;
import eapli.framework.io.util.Console;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
public class LoginAction implements Action {
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static Socket socket;

    public LoginAction(Socket socket) {
        LoginAction.socket = socket;
    }

    @Override
    public boolean execute() {
        var attempt = 1;
        int maxAttempts = DEFAULT_MAX_ATTEMPTS;
        while (attempt <= maxAttempts) {
            final String email = Console.readNonEmptyLine("Email:", "Please provide a email");
            final String password = Console.readLine("Password:");

            try {

                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                List<byte[]> data = new ArrayList<>();
                data.add(email.getBytes());
                data.add(password.getBytes());
                data.add(String.valueOf(Roles.CUSTOMER).getBytes());
                MessageUtil.writeMessage(output, FollowUpServer.AUTH, data);

                CustomMessage message = MessageUtil.readMessage(input);

                if (message.code == FollowUpServer.ACK) {
                    System.out.println("LOG IN SUCCESSFUL");
                    return true;
                } else {
                    System.out.printf(new String(message.data.get(0), StandardCharsets.UTF_8) + "\nYou have %d attempts left.%n%n»»»»»»»»»%n",
                            maxAttempts - attempt);
                }
//							System.out.println("Socket closed");

            } catch (Exception e) {
                e.printStackTrace();
            }

            attempt++;
        }
        System.out.println("Sorry, we are unable to authenticate you. Please contact your system administrator.");
        return false;
    }
}
