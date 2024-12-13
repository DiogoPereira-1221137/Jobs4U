package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.*;
import lapr4.jobs4u.customer.domain.Code;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobopening.application.RegisterJobOpeningController;
import lapr4.jobs4u.jobopening.domain.*;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class JobOpeningTest {



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
    public void testCreateOpening() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);



        NumberOfVacancies nv = new NumberOfVacancies(1);
        JobFunction jf = new JobFunction("Testing Unit");
        Mode m = Modes.HYBRID;
        ContractType ct = ContractType.valueOf(ContractTypes.FULL_TIME);
        Address ad = new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111");
        Description d = new Description("Testing Unit");
        Calendar c = Calendar.getInstance();
        JobReference jobReference = new JobReference(1);
        JobOpening first = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);

        assertNotNull(first);
        assertEquals(nv, first.numberOfVacancies());
        assertEquals(jf, first.jobFunction());
        assertEquals(ct, first.contractType());
        assertEquals(ad, first.address());
        assertEquals(d, first.description());
        assertEquals(c, first.registrationDate());
    }
    @Test
    public void testEqualsOpening() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);



        NumberOfVacancies nv = new NumberOfVacancies(1);
        JobFunction jf = new JobFunction("Testing Unit");
        Mode m = Modes.HYBRID;
        ContractType ct = ContractType.valueOf(ContractTypes.FULL_TIME);
        Address ad = new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111");
        Description d = new Description("Testing Unit");
        Calendar c = Calendar.getInstance();
        JobReference jobReference = new JobReference(1);

        JobReference jobReference2 = new JobReference(1);
        JobOpening first = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);
        JobOpening second = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);

        assertEquals(first,second);
    }

    @Test
    public void testDifferentOpening() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);



        NumberOfVacancies nv = new NumberOfVacancies(1);
        JobFunction jf = new JobFunction("Testing Unit");
        Mode m = Modes.HYBRID;
        ContractType ct = ContractType.valueOf(ContractTypes.FULL_TIME);
        Address ad = new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111");
        Address ad2 = new Address("Colorado", "United States of America", "Brownstone", "Tanglewood Drive", "4444-122");
        Description d = new Description("Testing Unit");
        Calendar c = Calendar.getInstance();
        JobReference jobReference = new JobReference(1);

        JobReference jobReference2 = new JobReference(2);

        JobOpening first = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);
        JobOpening second = new JobOpening(nv, jf, m, ct, ad2, customer, d, jobReference2, c);

        assertNotEquals(first,second);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testErrorOpening() {
        JobOpening first = new JobOpening(null,null,null,null,null,null,null,null, null);

    }

    private JobOpening getExampleJobOpening(PhaseNames phase){
        JobOpening jO = new JobOpening(new NumberOfVacancies(1), new JobFunction("empty"),
                new Mode("empty"),
                new ContractType("empty"),
                new Address("emtpy","empty","empty","empty","1111-111"),
                new Customer(getNewUserFirst(), new Code("AAA"), new Address("emtpy","empty","empty","empty","1111-111"), getNewUserSecond()),
                new Description("empty"),
                new JobReference(120),
                Calendar.getInstance());
        jO.setRecruitmentProcess(new RecruitmentProcess(false, new ArrayList<>()));
        if (phase != null) {
            jO.setPhase(phase);
        }
        return jO;
    }
    @Test

    public void ensureNumberOfVacanciesWorksInCompatible() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(null);
        assertTrue(jobOpening.updateVacancies(100));
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureNumberOfVacanciesInIncompatibleError() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(PhaseNames.SCREENING);
        NumberOfVacancies newParameter = new NumberOfVacancies(100);
        jobOpening.updateVacancies(100);
    }
    @Test
    public void ensureJobFunctionWorksInCompatible() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(null);
        assertTrue(jobOpening.updateJF("Lorem ipsum dolor sit amet"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureJobFunctionInIncompatibleError() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(PhaseNames.SCREENING);
        jobOpening.updateJF("Lorem ipsum dolor sit amet");
    }
    @Test
    public void ensureModeWorksInCompatible() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(null);
        Mode newParameter = new Mode("Lorem ipsum dolor sit amet");
        assertTrue(jobOpening.updateMode(newParameter));
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureModeInIncompatibleError() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(PhaseNames.SCREENING);
        Mode newParameter = new Mode("Lorem ipsum dolor sit amet");
        jobOpening.updateMode(newParameter);
    }
    @Test
    public void ensureContractTypeWorksInCompatible() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(null);
        assertTrue(jobOpening.updateCType("Lorem ipsum dolor sit amet"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureContractTypeInIncompatibleError() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(PhaseNames.SCREENING);
        jobOpening.updateCType("Lorem ipsum dolor sit amet");
    }
    @Test
    public void ensureAddressWorksInCompatible() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(null);
        Address newParameter = new Address("Lorem","Ipsum","Dolor","Sit Amet","6666-666");
        assertTrue(jobOpening.updateAddress(newParameter));
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureAddressInIncompatibleError() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(PhaseNames.SCREENING);
        Address newParameter = new Address("Lorem","Ipsum","Dolor","Sit Amet","6666-666");
        jobOpening.updateAddress(newParameter);
    }
    @Test
    public void ensureDescriptionWorksInCompatible() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(null);
        assertTrue(jobOpening.updateDescription("Lorem ipsum dolor sit amet"));
    }
    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionInIncompatibleError() throws Exception {
        JobOpening jobOpening = getExampleJobOpening(PhaseNames.SCREENING);
        assertTrue(jobOpening.updateDescription("Lorem ipsum dolor sit amet"));
    }
    @Test
    public void openJobOpeningApplicationPhase() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);


        NumberOfVacancies nv = new NumberOfVacancies(1);
        JobFunction jf = new JobFunction("Testing Unit");
        Mode m = Modes.HYBRID;
        ContractType ct = ContractType.valueOf(ContractTypes.FULL_TIME);
        Address ad = new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111");
        Description d = new Description("Testing Unit");
        Calendar c = Calendar.getInstance();
        JobReference jobReference = new JobReference(1);

        JobOpening jobOpening = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);

        boolean hasInterview = true;

        List<Phase> dates = new ArrayList<>();
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar endDateScreening = Calendar.getInstance();
        Calendar endDateInterview = Calendar.getInstance();
        Calendar endDateAnalysis = Calendar.getInstance();
        Calendar endDateResult = Calendar.getInstance();

        beginDate.set(2024, Calendar.SEPTEMBER, 7);
        endDate.set(2024, Calendar.SEPTEMBER, 8);
        endDateScreening.set(2024, Calendar.SEPTEMBER, 10);
        endDateInterview.set(2024, Calendar.SEPTEMBER, 12);
        endDateAnalysis.set(2024, Calendar.SEPTEMBER, 14);
        endDateResult.set(2024, Calendar.SEPTEMBER, 15);

        ApplicationPhase applicationPhase = new ApplicationPhase(beginDate, endDate);
        ScreeningPhase screeningPhase = new ScreeningPhase(endDate, endDateScreening);
        InterviewPhase interviewPhase = new InterviewPhase(endDateScreening, endDateInterview);
        AnalysisPhase analysisPhase = new AnalysisPhase(endDateInterview, endDateAnalysis);
        ResultPhase resultPhase = new ResultPhase(endDateAnalysis, endDateResult);

        dates.add(applicationPhase);
        dates.add(screeningPhase);
        dates.add(interviewPhase);
        dates.add(analysisPhase);
        dates.add(resultPhase);

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(hasInterview, dates);

        jobOpening.setRecruitmentProcess(recruitmentProcess);

        PhaseNames phase = PhaseNames.APPLICATION;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        jobOpening.recruitmentProcess().setPhasesBeginDate(0, today);
        jobOpening.currentPhase(phase);

        assertEquals(phase,jobOpening.currentPhase());
    }

    @Test
    public void jobOpeningApplicationPhaseToNextPhase() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);


        NumberOfVacancies nv = new NumberOfVacancies(1);
        JobFunction jf = new JobFunction("Testing Unit");
        Mode m = Modes.HYBRID;
        ContractType ct = ContractType.valueOf(ContractTypes.FULL_TIME);
        Address ad = new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111");
        Description d = new Description("Testing Unit");
        Calendar c = Calendar.getInstance();
        JobReference jobReference = new JobReference(1);

        JobOpening jobOpening = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);

        boolean hasInterview = true;

        List<Phase> dates = new ArrayList<>();
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar endDateScreening = Calendar.getInstance();
        Calendar endDateInterview = Calendar.getInstance();
        Calendar endDateAnalysis = Calendar.getInstance();
        Calendar endDateResult = Calendar.getInstance();

        beginDate.set(2024, Calendar.SEPTEMBER, 7);
        endDate.set(2024, Calendar.SEPTEMBER, 8);
        endDateScreening.set(2024, Calendar.SEPTEMBER, 10);
        endDateInterview.set(2024, Calendar.SEPTEMBER, 12);
        endDateAnalysis.set(2024, Calendar.SEPTEMBER, 14);
        endDateResult.set(2024, Calendar.SEPTEMBER, 15);

        ApplicationPhase applicationPhase = new ApplicationPhase(beginDate, endDate);
        ScreeningPhase screeningPhase = new ScreeningPhase(endDate, endDateScreening);
        InterviewPhase interviewPhase = new InterviewPhase(endDateScreening, endDateInterview);
        AnalysisPhase analysisPhase = new AnalysisPhase(endDateInterview, endDateAnalysis);
        ResultPhase resultPhase = new ResultPhase(endDateAnalysis, endDateResult);

        dates.add(applicationPhase);
        dates.add(screeningPhase);
        dates.add(interviewPhase);
        dates.add(analysisPhase);
        dates.add(resultPhase);

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(hasInterview, dates);

        jobOpening.setRecruitmentProcess(recruitmentProcess);

        PhaseNames phase = PhaseNames.APPLICATION;

        PhaseNames nextPhase = PhaseNames.SCREENING;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        jobOpening.recruitmentProcess().setPhasesEndDate(0, today);
        jobOpening.recruitmentProcess().setPhasesBeginDate(1, today);
        jobOpening.currentPhaseToNextPhase(phase);

        assertEquals(nextPhase,jobOpening.currentPhase());
    }

    @Test
    public void jobOpeningScreeningPhaseToPreviousPhase() {
        SystemUser user = getNewUserSecond();
        SystemUser userCustomerManager = getCustomerManager();
        Code code = new Code("ABC123");
        Address address = new Address("Street1", "City1", "State1", "Country1", "1234-232");

        Customer customer = new Customer(user, code, address, userCustomerManager);


        NumberOfVacancies nv = new NumberOfVacancies(1);
        JobFunction jf = new JobFunction("Testing Unit");
        Mode m = Modes.HYBRID;
        ContractType ct = ContractType.valueOf(ContractTypes.FULL_TIME);
        Address ad = new Address("Colorado", "United States of America", "Brownstone", "Willow Street", "4444-111");
        Description d = new Description("Testing Unit");
        Calendar c = Calendar.getInstance();
        JobReference jobReference = new JobReference(1);

        JobOpening jobOpening = new JobOpening(nv, jf, m, ct, ad, customer, d, jobReference, c);

        boolean hasInterview = true;

        List<Phase> dates = new ArrayList<>();
        Calendar beginDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar endDateScreening = Calendar.getInstance();
        Calendar endDateInterview = Calendar.getInstance();
        Calendar endDateAnalysis = Calendar.getInstance();
        Calendar endDateResult = Calendar.getInstance();

        beginDate.set(2024, Calendar.SEPTEMBER, 7);
        endDate.set(2024, Calendar.SEPTEMBER, 8);
        endDateScreening.set(2024, Calendar.SEPTEMBER, 10);
        endDateInterview.set(2024, Calendar.SEPTEMBER, 12);
        endDateAnalysis.set(2024, Calendar.SEPTEMBER, 14);
        endDateResult.set(2024, Calendar.SEPTEMBER, 15);

        ApplicationPhase applicationPhase = new ApplicationPhase(beginDate, endDate);
        ScreeningPhase screeningPhase = new ScreeningPhase(endDate, endDateScreening);
        InterviewPhase interviewPhase = new InterviewPhase(endDateScreening, endDateInterview);
        AnalysisPhase analysisPhase = new AnalysisPhase(endDateInterview, endDateAnalysis);
        ResultPhase resultPhase = new ResultPhase(endDateAnalysis, endDateResult);

        dates.add(applicationPhase);
        dates.add(screeningPhase);
        dates.add(interviewPhase);
        dates.add(analysisPhase);
        dates.add(resultPhase);

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(hasInterview, dates);

        jobOpening.setRecruitmentProcess(recruitmentProcess);

        PhaseNames phase = PhaseNames.SCREENING;

        PhaseNames previousPhase = PhaseNames.APPLICATION;

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        jobOpening.recruitmentProcess().setPhasesEndDate(0, today);
        //jobOpening.recruitmentProcess().setPhasesBeginDate(1, today);
        jobOpening.currentPhaseToPreviousPhase(phase);

        assertEquals(previousPhase,jobOpening.currentPhase());
    }
}
