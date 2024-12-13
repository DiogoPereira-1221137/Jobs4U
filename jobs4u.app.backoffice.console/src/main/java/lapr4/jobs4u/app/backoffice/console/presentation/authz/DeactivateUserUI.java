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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lapr4.jobs4u.usermanagement.application.DeactivateUserController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

/**
 * The type Deactivate user ui.
 *
 * @author Fernando
 */
@SuppressWarnings("squid:S106")
public class DeactivateUserUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeactivateUserUI.class);

    private final DeactivateUserController theController = new DeactivateUserController();

    @Override
    protected boolean doShow() {
        final List<SystemUser> list = new ArrayList<>();
        final Iterable<SystemUser> activeUsers = this.theController.activeUsers();
        final Iterable<SystemUser> deactivedUsers = this.theController.deactiveUsers();

        System.out.println("1. Disable");
        System.out.println("2. Enable");
        int option = Console.readInteger("You want to enable or disable? ");



        if(option == 1) {
            if (!activeUsers.iterator().hasNext()) {
                System.out.println("There is no registered User");
            } else {
                int cont = 1;
                System.out.println("SELECT User to deactivate\n");
                System.out.printf("%-6s%-30s%-40s%-10s%n", "Nº:", "Email", "Firstname", "Lastname");

                for (final SystemUser user : activeUsers) {
                    list.add(user);
                    System.out.printf("%-6d%-30s%-40s%-10s%n", cont, user.username(), user.name().firstName(),
                            user.name().lastName());
                    cont++;
                }
                option = Console.readInteger("Enter user nº to deactivate or 0 to finish ");
                if (option == 0) {
                    System.out.println("No user selected");
                } else {
                    try {
                       SystemUser user = this.theController.deactivateUser(list.get(option - 1));

                        System.out.println(user.username() + " has been \033[33mdisabled\033[0m");

                    } catch (IntegrityViolationException | ConcurrencyException ex) {
                        LOGGER.error("Error performing the operation", ex);
                        System.out.println(
                                "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
                    }
                }
            }
        }
        else if(option == 2) {
            if (!deactivedUsers.iterator().hasNext()) {
                System.out.println("There is no registered User");
            } else {
                int cont = 1;
                System.out.println("SELECT User to activate\n");
                System.out.printf("%-6s%-30s%-40s%-10s%n", "Nº:", "Email", "Firstname", "Lastname");
                for (final SystemUser user : deactivedUsers) {
                    list.add(user);
                    System.out.printf("%-6d%-30s%-40s%-10s%n", cont, user.username(), user.name().firstName(),
                            user.name().lastName());
                    cont++;
                }
                option = Console.readInteger("Enter user nº to activate or 0 to finish ");
                if (option == 0) {
                    System.out.println("No user selected");
                } else {
                    try {
                        SystemUser user = this.theController.activateUser(list.get(option - 1));

                        System.out.println(user.username() + " has been \033[32menabled\033[0m");


                    } catch (IntegrityViolationException | ConcurrencyException ex) {
                        LOGGER.error("Error performing the operation", ex);
                        System.out.println(
                                "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
                    }
                }
            }
        }

        return true;
    }

    @Override
    public String headline() {
        return "Deactivate User";
    }
}
