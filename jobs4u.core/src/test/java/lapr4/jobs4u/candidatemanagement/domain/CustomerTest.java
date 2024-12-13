package lapr4.jobs4u.candidatemanagement.domain;

import static org.junit.Assert.*;

import eapli.framework.infrastructure.authz.domain.model.*;
import lapr4.jobs4u.candidatemanagement.domain.Address;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.junit.*;

public class CustomerTest {


    public static SystemUser dummyUser(final String email, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(email, "duMMy1", "dummy", "dummy", email).build();
    }

    public static SystemUser crocodileUser(final String email, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(email, "CroC1_", "Crocodile", "SandTomb", email).withRoles(roles).build();
    }

    public static SystemUser customerManagerUser(final String email, final Role... roles) {
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with(email, "customerM", "dummy", "dummy", email).build();
    }

    private SystemUser getNewUserFirst() {
        return dummyUser("dummy@gmail.com", Roles.ADMIN);
    }

    private SystemUser getNewUserSecond() {
        return crocodileUser("crocodile@gmail.com", Roles.OPERATOR);
    }

    private SystemUser getCustomerManager() {
        return customerManagerUser("customerM@gmail.com", Roles.CUSTOMER_MANAGER);
    }



    @Test
    public void testCustomerConstructor() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);

        assertNotNull(customer);
        assertEquals(user, customer.user());
        assertEquals(code, customer.getId());
        assertEquals(address, customer.address());
    }

    @Test
    public void testChangeAddress() {
        Address address1 = new Address("Street1", "City1", "State1", "Country1", "1234-232");
        Address address2 = new Address("Street2", "City2", "State2", "Country2", "5432-232");

        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Customer customer = new Customer(user, code, address1, userCustomerManager);

        assertEquals(address1, customer.address());

        customer.changeAddress(address2);

        assertEquals(address2, customer.address());
    }

    @Test
    public void testEquals() {
        SystemUser user1 = getNewUserFirst();
        SystemUser user2 = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code1 = new Code("ABC123");
        Code code2 = new Code("DEF456");
        Address address1 = new Address("Street1", "City1", "State1", "Country1", "1234-232");
        Address address2 = new Address("Street2", "City2", "State2", "Country2", "5432-232");


        Customer customer1 = new Customer(user1, code1, address1, userCustomerManager);
        Customer customer2 = new Customer(user1, code1, address1, userCustomerManager);
        Customer customer3 = new Customer(user2, code2, address2, userCustomerManager);

        assertTrue(customer1.equals(customer2)); // Teste de igualdade com outro objeto igual
        assertFalse(customer1.equals(customer3)); // Teste de igualdade com outro objeto diferente
    }

    @Test
    public void testHashCode() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");


        Customer customer = new Customer(user, code, address, userCustomerManager);
        int hashCode1 = customer.hashCode();
        int hashCode2 = new Customer(user, code, address, userCustomerManager).hashCode();

        assertEquals(hashCode1, hashCode2); // hashCode deve ser igual para objetos iguais
    }

    @Test
    public void testId() {
        SystemUser user = getNewUserFirst();
        SystemUser userCustomerManager = getCustomerManager();
        Code code1 = new Code("ABC123");
        Code code2 = new Code("DEF456");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");


        Customer customer = new Customer(user, code1, address, userCustomerManager);

        customer.setId(code2);

        assertTrue(customer.hasIdentity(code2)); // Teste de igualdade com outro objeto igual
    }

    @Test
    public void testChangeUser() {
        SystemUser user1 = getNewUserFirst();
        SystemUser user2 = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");


        Customer customer = new Customer(user1, code, address, userCustomerManager);
        customer.changeUser(user2);

        assertTrue(customer.sameAs(user2)); // Teste de igualdade com outro objeto igual
    }


    @Test
    public void testCodeId() {
        SystemUser user1 = getNewUserFirst();
        SystemUser user2 = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Code code2 = new Code("DEF456");
        String code3 = "GHI123";
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");


        Customer customer = new Customer(user1, code, address, userCustomerManager);
        customer.getId().setCode(code3);
        Code code4 = new Code(customer.getId().getCode());

        assertEquals(0, customer.compareTo(code4)); // Teste de igualdade com outro objeto igual
    }

    // Testes adicionais para os métodos restantes

}
