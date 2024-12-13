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

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.SignupUI;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.UserDataWidget;
import lapr4.jobs4u.candidatemanagement.domain.Address;
import lapr4.jobs4u.customer.application.RegisterCustomerController;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class RegisterCustomerUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterCustomerUI.class);

    //private final AddUserController theController = new AddUserController();

    private final RegisterCustomerController theControllerCustomer = new RegisterCustomerController();

    private String codeString;
    private Address address;

    private String password;

    private Code code;

    @Override
    protected boolean doShow() {
        final UserDataWidget userData = new UserDataWidget();

        boolean verify = false;

        userData.show();

        this.password = theControllerCustomer.generatePassword();

        do {

            try {
                verify = false;
                this.codeString = Console.readLine("Code of the customer: ");

                this.code = new Code(codeString);

                verify = true;

            } catch (IllegalArgumentException e) {
                System.out.println(e);

            }

        } while (!verify);

        do{
            try {

                verify = false;

                System.out.println("- Address -");
                final String street;
                final String city;
                final String state;
                final String country;
                final String zipCode;

                state = Console.readLine("State: ");

                country = Console.readLine("Country: ");

                city = Console.readLine("City: ");

                street = Console.readLine("Street: ");

                zipCode = Console.readLine("Zip Code (format: xxxx-yyy , 4 numbers - 3 numbers):");

                this.address = new Address(street, city, state, country, zipCode);

                verify = true;


            } catch (Exception e) {
                System.out.println("Your address does not meet the requirements.");
            }

    } while(!verify);

        final Set<Role> roleTypes = new HashSet<>();
        roleTypes.add(Roles.CUSTOMER);

        try {

            SystemUser customerManager = this.theControllerCustomer.registerCustomer(userData.email(), this.password,
                    userData.firstName(), userData.lastName(), code, address, roleTypes);

            System.out.println("First Name: " + userData.firstName() + "\nLast Name: " + userData.lastName() +
                    "\nEmail: " + userData.email() + "\nPassword: " + this.password + "\nCustomer Manager: " + customerManager.username() + "\nCode: " + code.toString() + "\n" + address);


        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }



        return true;
    }

    @Override
    public String headline() {
        return "Add Customer";
    }
}
