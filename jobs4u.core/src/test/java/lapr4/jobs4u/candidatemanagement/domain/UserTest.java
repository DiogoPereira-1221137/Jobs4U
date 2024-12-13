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
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UserTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    public static SystemUser dummyUser(final String email, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(email, "duMMy1", "dummy", "dummy", email).build();
    }

    public static SystemUser crocodileUser(final String email, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(email, "CroC1_", "Crocodile", "SandTomb", email).withRoles(roles).build();
    }

    private SystemUser getNewUserFirst() {
        return dummyUser("dummy@gmail.com", Roles.ADMIN);
    }

    private SystemUser getNewUserSecond() {
        return crocodileUser("crocodile@gmail.com", Roles.OPERATOR);
    }
    private SystemUser getNewUserThird() {
        return crocodileUser("crocodile@gmail.com", Roles.CANDIDATE);
    }


    @DisplayName("Equals Users")
    @Test
    public void verifyIfUsersAreEquals() {
//        String email = "dummy@gmail.com";
//        String password = "duMMy1";
//        String firstName = "dummy";
//        String lastName = "dummy";
//        String name = firstName + " " + lastName;
//        final Set<Role> roleTypes = new HashSet<>();
//        roleTypes.add(Roles.ADMIN);
        assertTrue(getNewUserFirst().equals(getNewUserFirst()));
    }

    @DisplayName("Different Users")
    @Test
    public void verifyIfUsersAreNotEquals() {
        assertFalse(getNewUserFirst().equals(getNewUserSecond()));

    }

    @Test
    public void testAssignToRole() {
        SystemUser user = getNewUserFirst();
        user.assignToRole(Roles.OPERATOR);
        assertTrue(user.hasAny(Roles.OPERATOR));
    }

    @Test
    public void testUnassignRole() {
        SystemUser user = getNewUserSecond();

        assertTrue(user.unassignRole(Roles.OPERATOR));
    }

    @Test
    public void testActivate() {
        SystemUser user = getNewUserFirst();
        user.deactivate(Calendar.getInstance());
        user.activate();
        assertTrue(user.isActive());
    }
    @Test
    public void testActivateDeactivateCandidate() {
        SystemUser user = getNewUserThird();
        user.deactivate(Calendar.getInstance());
        assertFalse(user.isActive());
        user.activate();
        assertTrue(user.isActive());
    }
    @Test
    public void testCreatedOn() {
        assertNotNull(getNewUserFirst().createdOn());
    }

    @Test
    public void testHasAll() {
        SystemUser user = getNewUserSecond();
        user.assignToRole(Roles.OPERATOR);
        assertTrue(user.hasAll(Roles.OPERATOR));
    }

//    @Test
//    public void testChangePassword() {
//        SystemUser user = getNewUserSecond();
//        Password newPassword = new Password("newPassword");
//        user.changePassword(newPassword);
//        assertTrue(user.passwordMatches("newPassword", passwordEncoder));
//    }



//    @Test
//    public void ensurecandidateEqualsPassesForTheSameMecanographicNumber() throws Exception {
//
//        final candidate aClientUser = new candidateBuilder().withMecanographicNumber("DUMMY")
//                .withSystemUser(getNewDummyUser()).build();
//
//        final candidate anotherClientUser = new candidateBuilder().withMecanographicNumber("DUMMY")
//                .withSystemUser(getNewDummyUser()).build();
//
//        final boolean expected = aClientUser.equals(anotherClientUser);
//
//        assertTrue(expected);
//    }
//
//    @Test
//    public void ensureClientUserEqualsFailsForDifferenteMecanographicNumber() throws Exception {
//        final Set<Role> roles = new HashSet<>();
//        roles.add(Roles.ADMIN);
//
//        final candidate aClientUser = new candidateBuilder().withMecanographicNumber(aMecanographicNumber)
//                .withSystemUser(getNewDummyUser()).build();
//
//        final candidate anotherClientUser = new candidateBuilder()
//                .withMecanographicNumber(anotherMecanographicNumber).withSystemUser(getNewDummyUser()).build();
//
//        final boolean expected = aClientUser.equals(anotherClientUser);
//
//        assertFalse(expected);
//    }

//    @Test
//    public void ensureClientUserEqualsAreTheSameForTheSameInstance() throws Exception {
//        final candidate aClientUser = new candidate(this.systemUser);
//
//        final boolean expected = aClientUser.equals(aClientUser);
//
//        assertTrue(expected);
//    }

//    @Test
//    public void ensureClientUserEqualsFailsForDifferenteObjectTypes() throws Exception {
//        final Set<Role> roles = new HashSet<>();
//        roles.add(Roles.ADMIN);
//
//        final candidate aClientUser = new candidateBuilder().withMecanographicNumber("DUMMY")
//                .withSystemUser(getNewDummyUser()).build();
//
//        @SuppressWarnings("unlikely-arg-type")
//        final boolean expected = aClientUser.equals(getNewDummyUser());
//
//        assertFalse(expected);
//    }
//
//    @Test
//    public void ensureClientUserIsTheSameAsItsInstance() throws Exception {
//        final candidate aClientUser = new candidateBuilder().withMecanographicNumber("DUMMY")
//                .withSystemUser(getNewDummyUser()).build();
//
//        final boolean expected = aClientUser.sameAs(aClientUser);
//
//        assertTrue(expected);
//    }
//
//    @Test
//    public void ensureTwoClientUserWithDifferentMecanographicNumbersAreNotTheSame() throws Exception {
//        final Set<Role> roles = new HashSet<>();
//        roles.add(Roles.ADMIN);
//        final candidate aClientUser = new candidateBuilder().withMecanographicNumber(aMecanographicNumber)
//                .withSystemUser(getNewDummyUser()).build();
//
//        final candidate anotherClientUser = new candidateBuilder()
//                .withMecanographicNumber(anotherMecanographicNumber).withSystemUser(getNewDummyUser()).build();
//
//        final boolean expected = aClientUser.sameAs(anotherClientUser);
//
//        assertFalse(expected);
//    }
}
