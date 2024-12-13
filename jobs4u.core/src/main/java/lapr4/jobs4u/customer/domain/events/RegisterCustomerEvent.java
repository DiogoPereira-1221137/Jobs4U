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
package lapr4.jobs4u.customer.domain.events;


import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import lapr4.jobs4u.candidatemanagement.domain.Address;
import lapr4.jobs4u.customer.domain.Code;

/**
 * The type Register customer event.
 *
 * @author Paulo Gandra de Sousa
 */
public class RegisterCustomerEvent extends DomainEventBase implements DomainEvent {

    private static final long serialVersionUID = 1L;

    private final SystemUser theRegisterRequest;

    private final Code code;
    private final Address address;

    private final SystemUser customerManager;


    /**
     * Instantiates a new Register customer event.
     *
     * @param theRegisterRequest the the register request
     * @param code               the code
     * @param address            the address
     * @param customerManager    the customer manager
     */
    public RegisterCustomerEvent(final SystemUser theRegisterRequest,  Code code, Address address, final SystemUser customerManager) {
        this.theRegisterRequest = theRegisterRequest;
        this.code = code;
        this.address = address;
        this.customerManager = customerManager;
    }


    /**
     * Name name.
     *
     * @return the name
     */
    public Name name() {
        return theRegisterRequest.name();
    }

    /**
     * Email string.
     *
     * @return the string
     */
    public String email() {
        return String.valueOf(theRegisterRequest.email());
    }

    /**
     * Code code.
     *
     * @return the code
     */
    public Code code() { return code;}

    /**
     * Address address.
     *
     * @return the address
     */
    public Address address() { return address;}

    /**
     * Customer manager associated system user.
     *
     * @return the system user
     */
    public SystemUser customerManagerAssociated() {return customerManager;	}


    /**
     * Gets the register request.
     *
     * @return the the register request
     */
    public SystemUser getTheRegisterRequest() {
        return theRegisterRequest;
    }

    @Override
    public String toString() {
        return "RegisterCustomer(" + theRegisterRequest.username() + ")";
    }
}
