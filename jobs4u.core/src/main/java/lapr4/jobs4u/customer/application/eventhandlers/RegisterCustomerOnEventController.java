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
package lapr4.jobs4u.customer.application.eventhandlers;

import eapli.framework.functional.Functions;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import lapr4.jobs4u.candidatemanagement.domain.Address;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.customer.domain.events.RegisterCustomerEvent;
import lapr4.jobs4u.customer.repositories.CustomerRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.usermanagement.domain.Roles;
import lapr4.jobs4u.usermanagement.domain.events.DeleteUserEvent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * The type Register customer on event controller.
 */
/* package */ class RegisterCustomerOnEventController {


    private final CustomerRepository customerRepository = PersistenceContext
            .repositories().customers();

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final UserManagementService userSvc = AuthzRegistry.userService();

    private final UserRepository repo = PersistenceContext.repositories().users();



    private final EventPublisher dispatcher = InProcessPubSub.publisher();


    /**
     * Register customer customer.
     *
     * @param event the event
     * @return the customer
     */
    public Customer registerCustomer(final RegisterCustomerEvent event) {
        final Set<Role> roles = new HashSet<>();
        roles.add(Roles.CUSTOMER);

        SystemUser newUser = event.getTheRegisterRequest();

        if (findUser(event)) {
            try{
                Code codeCustomer = event.code();

                Address addressCustomer = event.address();

                SystemUser customeManager = event.customerManagerAssociated();

                Customer customer = new Customer(newUser,codeCustomer,addressCustomer,customeManager);

                customer.setId(codeCustomer);

                customerRepository.save(customer);

                return customer;

            }catch(Exception ex){
                DeleteUserEvent error = new DeleteUserEvent(newUser);
                dispatcher.publish(error);
            }


        }


        return null;

    }

    @SuppressWarnings("squid:S1488")
    private boolean findUser(final RegisterCustomerEvent event) {
        final Optional<SystemUser> newUser = Functions
                .retry(() -> repo.ofIdentity(event.getTheRegisterRequest().username()), 1000, 3);
        return newUser.isPresent();
    }

}
