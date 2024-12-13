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
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.SignupUI;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.UserDataWidget;
import lapr4.jobs4u.candidatemanagement.application.RegisterCandidateController;
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
public class RegisterCandidateUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterCandidateUI.class);

    //private final AddUserController theController = new AddUserController();

    private final RegisterCandidateController theControllerCandidate = new RegisterCandidateController();



    private String phoneNumber;
    private String password;

    @Override
    protected boolean doShow() {
        final UserDataWidget userData = new UserDataWidget();

        userData.show();

        this.password = theControllerCandidate.generatePassword();


        this.phoneNumber = Console.readLine("Phone number ");

        final Set<Role> roleTypes = new HashSet<>();
        roleTypes.add( Roles.CANDIDATE);

           try {

            this.theControllerCandidate.registerCandidate(userData.email(), this.password,
                    userData.firstName(), userData.lastName(),phoneNumber, roleTypes);

               System.out.println("\n==========Candidate Registered Successfully!==========");

               System.out.println("\nFirst Name: " + userData.firstName() + "\nLast Name: "+ userData.lastName() +
                    "\nEmail: " + userData.email() + "\nPassword: " + this.password + "\nPhone number: " + phoneNumber);



        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }

        return true;
    }

    @Override
    public String headline() {
        return "Add Candidate";
    }
}
