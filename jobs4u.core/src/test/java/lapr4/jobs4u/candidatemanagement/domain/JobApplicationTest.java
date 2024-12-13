package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.*;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobApplication.domain.*;
import lapr4.jobs4u.jobopening.domain.*;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobApplicationTest {



    private final PhoneNumber phoneNumber = new PhoneNumber("966866522");

    private final NumberOfVacancies numberOfVacancies = new NumberOfVacancies(34);
    private final JobFunction jobFunction = new JobFunction("front end programmer");

    private final Mode mode= new Mode("hybrid");

    private final ContractType contractType = new ContractType("full-time");
    private static final String email = "candidate@gmail.com";

    private final Description jobDescription= new Description("we are looking for a good java and c# programmer");



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
    public void testCreate() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");


        JobReference jobReference = new JobReference(1);


        Customer customer = new Customer(user, code, address, userCustomerManager);
        JobOpening jo = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
        , customer, new Description("Testing Unit"), jobReference, Calendar.getInstance());
        Candidate c = new Candidate(getNewUserFirst(), phoneNumber);
        List<ApplicationFile> fp = new ArrayList<>();

        JobApplication first = new JobApplication(c, jo);
        Type type = Types.INFO;
        first.FilePaths(fp);


        assertNotNull(first);
        assertEquals(jo, first.JobOpening());
        assertEquals(fp, first.filePathsInfo());
    }

//    @Test
//    public void testEquals() {
//        SystemUser user = getNewUserSecond();
//        SystemUser userCustomerManager = getCustomerManager();
//        Code code = new Code("ABC123");
//        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");
//
//        Customer customer = new Customer(user, code, address, userCustomerManager);
//
//        JobReference jobReference = new JobReference(1);
//
//
//        JobOpening jo = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
//                , customer, new Description("Testing Unit"), jobReference, Calendar.getInstance());
//        Candidate c = new Candidate(getNewUserFirst(), phoneNumber);
//        List<ApplicationFile> fp = new ArrayList<>();
//
//        JobApplication first = new JobApplication(c, jo);
//        first.FilePaths(fp);
//        JobApplication second = new JobApplication(c, jo);
//        second.FilePaths(fp);
//
//
//        assertEquals(first, second);
//    }

    @Test
    public void testNotEquals() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");
        Address address2 = new Address("Street2", "City2", "State2", "Country2", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);
        Customer customer2 = new Customer(user, code, address2, userCustomerManager);

        JobReference jobReference = new JobReference(1);

        JobReference jobReference2 = new JobReference(2);


        JobOpening jo = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
                , customer, new Description("Testing Unit"),jobReference, Calendar.getInstance());
        JobOpening jo2 = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
                , customer2, new Description("Testing Unit"), jobReference2, Calendar.getInstance());
        Candidate c = new Candidate(getNewUserFirst(), phoneNumber);
        List<ApplicationFile> fp = new ArrayList<>();

        JobApplication first = new JobApplication(c, jo);
        first.FilePaths(fp);
        JobApplication second = new JobApplication(c, jo);


        assertNotEquals(first, second);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testError() {
        JobApplication first = new JobApplication(null, null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNullCandidateError() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);

        JobReference jobReference = new JobReference(1);

        JobOpening jo = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
                , customer, new Description("Testing Unit"), jobReference, Calendar.getInstance());

        List<ApplicationFile> fp = new ArrayList<>();



        JobApplication first = new JobApplication(null, jo);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullJobOpeningError() {
        Candidate c = new Candidate(getNewUserFirst(), phoneNumber);


        JobApplication first = new JobApplication(c, null);
        List<ApplicationFile> fp = new ArrayList<>();
        first.FilePaths(fp);
    }

    @Test(expected = NullPointerException.class)
    public void verifyRequirementsWithNoFiles() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        JobReference jobReference = new JobReference(1);

        Customer customer = new Customer(user, code, address, userCustomerManager);
        JobOpening jo = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
                , customer, new Description("Testing Unit"), jobReference, Calendar.getInstance());
        Candidate c = new Candidate(getNewUserFirst(), phoneNumber);

        JobApplication jobApplication = new JobApplication(c, jo);


        String file = null;
        String message = "";


        Plugin requirementsPlugin = jo.requirementsPlugin();
        File filePlugin = new File(requirementsPlugin.jarFileName().toString());
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass(requirementsPlugin.pluginMainClassName().name());
        Method method = jarFile.getMethod("verifyRequirements");
        assertFalse((Boolean) method.invoke(null, file));

    }

//    @Test
//    public void verifyRequirementsWithFiles() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        SystemUser user = getNewUserSecond();
//        SystemUser userCustomerManager = getCustomerManager();
//        Code code = new Code("ABC123");
//        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");
//
//        JobReference jobReference = new JobReference(1);
//
//        Customer customer = new Customer(user, code, address, userCustomerManager);
//        JobOpening jo = new JobOpening(new NumberOfVacancies(1), new JobFunction("Testing Unit"), Modes.HYBRID, ContractType.valueOf(ContractTypes.FULL_TIME), new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111")
//                , customer, new Description("Testing Unit"), jobReference, Calendar.getInstance());
//        Candidate c = new Candidate(getNewUserFirst(), phoneNumber);
//        List<ApplicationFile> fp = new ArrayList<>();
//        PluginType pluginType = PluginType.REQUIREMENTS;
//        Description description = new Description("requirements");
//        PluginMainClassName mainClassName = new PluginMainClassName("main");
//        JarFileName jarFileName = new JarFileName("testRequirementsPlugin.jar");
//        PluginIdentifier pluginIdentifier = PluginIdentifier.generatePluginIdentifier(pluginType, jarFileName, 123456789L);
//
//
//        Plugin plugin = new Plugin(pluginIdentifier, pluginType, description, mainClassName, jarFileName);
//        jo.putRequirementsPlugin(plugin);
//
//        String file = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/requirementTxtValid.txt";
//        String message = "";
//        Plugin requirementsPlugin = jo.requirementsPlugin();
//        File filePlugin = new File("plugins/"+requirementsPlugin.jarFileName().toString());
//        URL url = filePlugin.toURI().toURL();
//
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
//        Class<?> jarFile = classLoader.loadClass(requirementsPlugin.pluginMainClassName().name());
//        Method method = jarFile.getMethod("verifyRequirements", String.class);
//
//        assertTrue((Boolean) method.invoke(null, file));
//
//
//    }
}
