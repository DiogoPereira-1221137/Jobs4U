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
package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.RollbackException;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.SignupUI;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.UserDataWidget;
import lapr4.jobs4u.usermanagement.application.AddUserController;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;
import lapr4.jobs4u.usermanagement.domain.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class AddUserUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupUI.class);

    private final AddUserController theController = new AddUserController();
    private String password;

    @Override
    protected boolean doShow() {
//        // FIXME avoid duplication with SignUpUI. reuse UserDataWidget from
//        // candidateApp
//        final String username = Console.readLine("Username");
//        final String password = Console.readLine("Password");
////        final String password = PasswordGenerator.generatePassword();
//        final String firstName = Console.readLine("First Name");
//        final String lastName = Console.readLine("Last Name");
//        final String email = Console.readLine("E-Mail");

        final UserDataWidget userData = new UserDataWidget();

        userData.show();


        this.password = theController.generatePassword();


        final Set<Role> roleTypes = new HashSet<>();
        boolean show;
        do {
            show = showRoles(roleTypes);
        } while (!show);

        try {
            this.theController.addUser(userData.email(), password,
                    userData.firstName(), userData.lastName(),roleTypes);

           System.out.println("First Name: " + userData.firstName() + "\nLast Name: "+ userData.lastName() + "\nEmail: " + userData.email() + "\nPassword: " + password + "\nRole: " + roleTypes );

        } catch (final IntegrityViolationException | ConcurrencyException | RollbackException e) {
            System.out.println("\nThat email is already in use.");
        }

        return false;
    }


    private boolean showRoles(final Set<Role> roleTypes) {
        // TODO we could also use the "widget" classes from the framework...
        final Menu rolesMenu = buildRolesMenu(roleTypes);
        final MenuRenderer renderer = new VerticalMenuRenderer(rolesMenu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildRolesMenu(final Set<Role> roleTypes) {
        final Menu rolesMenu = new Menu();
        int counter = 0;
        rolesMenu.addItem(MenuItem.of(counter++, "No Role", Actions.SUCCESS));
        for (final Role roleType : theController.getRoleTypes()) {
            rolesMenu.addItem(MenuItem.of(counter++, roleType.toString(), () -> roleTypes.add(roleType)));
        }
        return rolesMenu;
    }

    @Override
    public String headline() {
        return "Add User";
    }
}
