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
package lapr4.jobs4u.usermanagement.domain.events;


import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

/**
 * The type Delete user event.
 *
 * @author Paulo Gandra de Sousa
 */
public class DeleteUserEvent extends DomainEventBase implements DomainEvent {

    private static final long serialVersionUID = 1L;

    private final SystemUser theRegisterRequest;

    /**
     * Instantiates a new Delete user event.
     *
     * @param theRegisterRequest the the register request
     */
    public DeleteUserEvent(final SystemUser theRegisterRequest) {
        this.theRegisterRequest = theRegisterRequest;
    }

    /**
     * Gets system user.
     *
     * @return the system user
     */
    public SystemUser getSystemUser() {
        return theRegisterRequest;
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



    @Override
    public String toString() {
        return "RegisterUser(" + theRegisterRequest.username() + ")";
    }
}
