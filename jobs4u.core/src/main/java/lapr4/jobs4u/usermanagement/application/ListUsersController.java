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
package lapr4.jobs4u.usermanagement.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lapr4.jobs4u.usermanagement.domain.Roles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 * The type List users controller.
 *
 * @author losa
 */
@UseCaseController
public class ListUsersController{

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    /**
     * All users iterable.
     *
     * @return the iterable
     */
    public Iterable<SystemUser> allUsers() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        return userSvc.allUsers();
    }


    /**
     * Filtered users of back office iterable.
     *
     * @return the iterable
     */
    public Iterable<SystemUser> filteredUsersOfBackOffice() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        List<SystemUser> filteredUsers = new ArrayList<>();

        for (SystemUser user : userSvc.allUsers()) {
            if (user.roleTypes().contains(Roles.ADMIN) || user.roleTypes().contains(Roles.OPERATOR)
                    || user.roleTypes().contains(Roles.LANGUAGE_ENGINEER) || user.roleTypes().contains(Roles.CUSTOMER_MANAGER) ) {
                filteredUsers.add(user);
            }
        }

        return filteredUsers;
    }


    /**
     * Find optional.
     *
     * @param u the u
     * @return the optional
     */
    public Optional<SystemUser> find(final Username u) {
        return userSvc.userOfIdentity(u);
    }
}
