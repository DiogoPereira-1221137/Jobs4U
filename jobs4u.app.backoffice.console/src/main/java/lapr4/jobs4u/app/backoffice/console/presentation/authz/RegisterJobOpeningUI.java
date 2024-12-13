package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.time.util.CurrentTimeCalendars;
import lapr4.jobs4u.jobopening.application.RegisterJobOpeningController;
import lapr4.jobs4u.candidatemanagement.domain.*;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * The type Register job opening ui.
 */
public class RegisterJobOpeningUI extends AbstractUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterJobOpeningUI.class);

    private final RegisterJobOpeningController controller = new RegisterJobOpeningController();

    private Customer companyName;
    private Integer numberOfVacancies;
    private String jobFunction;
    private Mode mode;
    private ContractType contractType;
    private Integer jobReference;
    private Address address;
    private String  description;
    private Calendar registrationDate;


    @Override
    protected boolean doShow() {

        List<Customer> filteredCustomers = controller.filterCustomerBySystemUser();
        if (!filteredCustomers.isEmpty()) {
            companyName= displayCustomerMenu(filteredCustomers);

        } else {
            System.out.println("No customers associated with the current user.");
            System.out.println("It is not possible to register a job opening.");
            return false;
        }

        while (true) {
            try {
                this.numberOfVacancies = Integer.parseInt(Console.readLine("Number Of Vacancies: "));
                if (numberOfVacancies <= 0) {
                    System.out.println("Number of vacancies must be a positive integer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        this.jobFunction =String.valueOf(Console.readLine("Job Function: "));

        this.mode = Mode.valueOf(selectMode());

        this.contractType = ContractType.valueOf(selectContractType());

        try{

            System.out.println("- Address -");
            final String street;
            final String city;
            final String state;
            final String country;
            String zipCode;

            street = Console.readLine("Street: ");

            city = Console.readLine("City: ");

            state = Console.readLine("State: ");

            country = Console.readLine("Country: ");

            while (true) {
                zipCode = Console.readLine("Zip Code (format: xxxx-yyy , 4 numbers - 3 numbers): ");
                if (zipCode.matches("\\d{4}-\\d{3}")) { // Regex pattern for ZIP code format
                    break; // Exit loop if format is valid
                } else {
                    System.out.println("Invalid ZIP code format. Please enter in the format xxxx-yyy (4 numbers - 3 numbers).");
                }
            }
            this.address = new Address(street,city,state,country,zipCode);


        }
        catch (Exception e){
            System.out.println("Your address does not meet the requirements.");
        }

        this.description = Console.readLine("Description: ");



        try {
            Integer lastJobOpeningReference = controller.getLastJobReference();
            JobReference jobReference = controller.generateNextJobReference(lastJobOpeningReference);
            this.controller.registerJobOpening(new NumberOfVacancies(numberOfVacancies),
                   JobFunction.valueOf(jobFunction), mode, contractType, jobReference, address, companyName, new Description(description));

            System.out.println("\n==========Job Opening Registered Successfully!==========");
            System.out.println("\033[1mJob Reference:\033[0m " + jobReference.jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + numberOfVacancies + "\n\033[1mJob Function:\033[0m "+ jobFunction + "\n\033[1mMode:\033[0m " + mode + "\n\033[1mContract Type:\033[0m "
                    + contractType +  "\n" + address + "\n\033[1mCustomer (Company Name):\033[0m " + companyName.user().email() + "\n\033[1mDescription:\033[0m" + description + "\n\033[1mRegistration Date:\033[0m " + CurrentTimeCalendars.now().getTime());



        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
    }

        return true;
    }

    @Override
    public String headline() {
        return "Add Job Opening";
    }

    private String selectMode() {
        while (true) {
            System.out.println("Select Mode:");
            System.out.println("1. " + Modes.REMOTE);
            System.out.println("2. " + Modes.HYBRID);
            System.out.println("3. " + Modes.ONSITE);
            try {
                int choice = Console.readInteger("Enter your choice: ");
                switch (choice) {
                    case 1:
                        return Modes.REMOTE.toString();
                    case 2:
                        return Modes.HYBRID.toString();
                    case 3:
                        return Modes.ONSITE.toString();
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private String selectContractType() {
        while (true) {
            System.out.println("Select Contract Type:");
            System.out.println("1. " + ContractTypes.FULL_TIME);
            System.out.println("2. " + ContractTypes.PART_TIME);
            int choice = Console.readInteger("Enter your choice: ");
            switch (choice) {
                case 1:
                    return ContractTypes.FULL_TIME.toString();
                case 2:
                    return ContractTypes.PART_TIME.toString();
                default:
                    System.out.println("Invalid choice. Please enter either 1 or 2.");
            }
        }
    }

    private Customer displayCustomerMenu(List<Customer> customers) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a customer:");

            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i + 1) + ". " + "\033[1mName:\033[0m " + customers.get(i).user().name() + " , " + "\033[1mCode:\033[0m " + customers.get(i).getId() + " , " + "\033[1mEmail:\033[0m " + customers.get(i).user().email());
            }

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= customers.size()) {
                    return customers.get(choice - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }
}
