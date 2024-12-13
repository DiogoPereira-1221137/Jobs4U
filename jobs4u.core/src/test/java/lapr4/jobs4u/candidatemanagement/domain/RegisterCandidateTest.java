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
package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.*;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Nuno Bettencourt [NMB] on 03/04/16.
 */
public class RegisterCandidateTest {

    private final PhoneNumber phoneNumber = new PhoneNumber("966866522");
    private static final String email = "candidate@gmail.com";

//    private final RegisterCandidateController theControllerCandidate = new RegisterCandidateController();



    public static SystemUser dummyUser(final String username, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(username, "duMMy1", "dummy", "dummy", email).withRoles(roles).build();
    }

    public static SystemUser dummyUser2(final String username, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(username, "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(roles).build();
    }

    private SystemUser getNewDummyUser() {
        return dummyUser("dummy", Roles.ADMIN);
    }

    @Test
    public void testCandidateConstructor() {
        SystemUser user = getNewDummyUser();
        PhoneNumber phoneNumber1 =new PhoneNumber("966866522");

        Candidate candidate = new Candidate(user, phoneNumber1);

        assertNotNull(candidate);
        assertEquals(user, candidate.user());
        assertEquals(phoneNumber1, candidate.phoneNumber());
        assertEquals(candidate.getId(), candidate.identity());

    }


    @Test
    public void ensureCandidateFailsForDifferentEmail() throws Exception {

        final Candidate aCandidate = new Candidate(getNewDummyUser(), phoneNumber);


        PhoneNumber phoneNumber1 = new PhoneNumber("966888555");
        final Candidate anotherCandidate = new Candidate(getNewDummyUser(), phoneNumber1);

        final boolean expected = aCandidate.equals(anotherCandidate);


        assertFalse(expected);
    }

    @Test
    public void ensureCandidateFailsForDifferentePhoneNumbers() throws Exception {

        final Candidate aCandidate = new Candidate(getNewDummyUser(), phoneNumber);

        final Candidate anotherCandidate = new Candidate(getNewDummyUser(), phoneNumber);

        final boolean expected = aCandidate.equals(anotherCandidate);

        assertFalse(expected);
    }

}
