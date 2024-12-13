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
package lapr4.jobs4u.app.customer.console.presentation;

import eapli.framework.actions.Action;
import eapli.framework.actions.Actions;
import eapli.framework.actions.ChainedAction;
import lapr4.jobs4u.app.customer.console.presentation.myuser.CommTestUI;
import eapli.framework.actions.menu.Menu;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import lapr4.jobs4u.app.customer.console.presentation.myuser.DisconnAction;
import lapr4.jobs4u.app.customer.console.presentation.myuser.NotifCustomerUI;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.candidatemanagement.domain.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Gandra Sousa
 */
class MainMenu extends CustomerBaseUI {
    private static final String RETURN_LABEL = "Return ";


    private static final int EXIT_OPTION = 0;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;

    private static final int APPLICATION_OPTION = 3;
    private static final int NOTIFICATION_OPTION = 2;

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private static Socket socket;

    public MainMenu(Socket socket) {
        MainMenu.socket = socket;
    }

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu;
        try {
            menu = buildMainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final MenuRenderer renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildMainMenu() throws IOException {
        final Menu mainMenu = new Menu();

        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());
        MessageUtil.writeMessage(output, FollowUpServer.REQ_UNREAD, new ArrayList<>());
        CustomMessage message = MessageUtil.readMessage(input);

        String unread = new String(message.data.get(0), StandardCharsets.UTF_8);
        unread = unread.equals("0")? "" : " \u001B[32m("+unread+" unread notifications)\u001B[0m";

        mainMenu.addItem(MY_USER_OPTION, "List all my job openings", new ListJobOpeningAppUI()::show);
        mainMenu.addItem(NOTIFICATION_OPTION, "Check my Notifications"+unread, new ChainedAction(new NotifCustomerUI(socket)::show, () -> {
            new MainMenu(socket).mainLoop();
            return true;
        }));
        mainMenu.addItem(APPLICATION_OPTION, "Test connection to the server", new ChainedAction(new CommTestUI(socket)::show, () -> {
            new MainMenu(socket).mainLoop();
            return true;
        }));
        mainMenu.addItem(EXIT_OPTION, "Exit", new DisconnAction(socket,"Bye, Bye"));

        return mainMenu;
    }

    private Menu buildMenu(List<Pair<String, Action>> menuBuilder, String title) {
        final var menu = new Menu(title);
        int index = 1;
        for (Pair<String, Action> item: menuBuilder) {
            menu.addItem(index, item.getLeft(), item.getRight());
            index++;
        }
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

}
