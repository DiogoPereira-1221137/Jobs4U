package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.domain.Address;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.application.UpdateJobOpeningController;
import lapr4.jobs4u.jobopening.domain.ContractTypes;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.Modes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type List job opening ui.
 */
public class UpdateJobOpeningUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateJobOpeningUI.class);

    private final UpdateJobOpeningController controller = new UpdateJobOpeningController();

    @Override
    protected boolean doShow() {
        Scanner scanner = new Scanner(System.in);

        int filterOption = -1;

        filterOption = -1;
        List<JobOpening> jO = controller.filterJobOpeningsBySystemUser();
        displayJobOpenings(jO);
        while (filterOption <= 0 || filterOption > jO.size()) {
            System.out.print("\nSelect the Job Opening:\n");
            try {
                filterOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            if(filterOption <= 0 || filterOption > jO.size()){
                System.out.print("Invalid Option\n");
            }
        }
        JobOpening selected = jO.get(filterOption-1);
        boolean valid = false;
        filterOption = -1;
        displayProperty(selected);
        while (!valid) {
            try {
                System.out.print("Select Attribute to Edit>\n");
                filterOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            if (filterOption < 0 || filterOption > 8) {
                System.out.print("Invalid Option\n");
            } else {
                if (filterOption == 0) return false;
                if (controller.phaseCompatibility(selected, filterOption)) {
                    switch (filterOption) {
                        case 1: //Mode
                            modeUI(selected);
                            break;
                        case 2: //Contract Type
                            contractTypeUI(selected);
                            break;
                        case 3: //Address
                            addressUI(selected);
                            break;
                        case 4: //Description
                            controller.updateDescription(selected, Console.readLine("Description: "));
                            break;
                        case 5: //Vacancies
                            vacanciesUI(selected);
                            break;
                        case 6: //Function
                            controller.updateJF(selected, Console.readLine("Job Function: "));
                            break;
                        case 7: //Requirements
                            //Call US
                            SelectRequirementsUI altUI = new SelectRequirementsUI();
                            altUI.definePlugin(selected);
                            break;
                        case 8: //Interview Model
                            //Call US
                            if (selected.recruitmentProcess().hasInterview()) {
                                SelectInterviewModelUI altUI2 = new SelectInterviewModelUI();
                                altUI2.definePlugin(selected);
                            } else {
                                System.out.println("This info cannot be edited as the Job Opening has no Interview Phase.");
                            }
                            break;
                        default:
                    }
                    valid = true;
                } else {
                    System.out.println("This info cannot be edited as it is incompatible with this phase.");
                }
            }
        }
        return false;
    }

    private void vacanciesUI(JobOpening selected) {
        int numberOfVacancies = -1;
        while (numberOfVacancies <= 0) {
            try {
                numberOfVacancies = Integer.parseInt(Console.readLine("Number Of Vacancies: "));
                if (numberOfVacancies <= 0) {
                    System.out.println("Number of vacancies must be a positive integer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        controller.updateVacancies(selected, numberOfVacancies);
    }


    private void addressUI(JobOpening selected) {
        Address address = null;
        while (address == null) {
            try {

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
                address = new Address(street, city, state, country, zipCode);

            } catch (Exception e) {
                System.out.println("Your address does not meet the requirements.");
            }
        }
        controller.updateAddress(selected, address);
    }



    private void contractTypeUI(JobOpening selected) {
        int choice = -1;
        while (choice <= 0 || choice > 2) {
            System.out.println("Select Contract Type:");
            System.out.println("1. " + ContractTypes.FULL_TIME);
            System.out.println("2. " + ContractTypes.PART_TIME);
            choice = Console.readInteger("Enter your choice: ");
            switch (choice) {
                case 1:
                    controller.updateCType(selected, ContractTypes.FULL_TIME);
                case 2:
                    controller.updateCType(selected, ContractTypes.PART_TIME);
                default:
                    System.out.println("Invalid choice. Please enter either 1 or 2.");
            }
        }
    }

    private void modeUI(JobOpening selected) {
        int choice = -1;
        while (choice <= 0 || choice > 3) {
            System.out.println("Select Mode:");
            System.out.println("1. " + Modes.REMOTE);
            System.out.println("2. " + Modes.HYBRID);
            System.out.println("3. " + Modes.ONSITE);
            try {
                choice = Console.readInteger("Enter your choice: ");
                switch (choice) {
                    case 1:
                        controller.updateMode(selected, Modes.REMOTE);
                        break;
                    case 2:
                        controller.updateMode(selected, Modes.HYBRID);
                        break;
                    case 3:
                        controller.updateMode(selected, Modes.ONSITE);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private void displayProperty(JobOpening selected) {
//        String current = String.valueOf(selected.currentPhase());
//        switch (current) {
//            case "null":
//                if (selected.recruitmentProcess().hasInterview()) {
                    System.out.println("1 - Mode\n2 - Contract Type\n3 - Address\n4 - Description\n5 - No. Vacancies\n6 - Job Function\n7 - Requirements\n8 - Interview Model\n\n0 - Exit");
//                } else {
//                    System.out.println("1 - Mode\n2 - Contract Type\n3 - Address\n4 - Description\n5 - No. Vacancies\n6 - Job Function\n7 - Requirements\n");
//                }
//            case "APPLICATION":
//                if (selected.recruitmentProcess().hasInterview()) {
//                    System.out.println("1 - Requirements\n2 - Interview Model\n");
//                } else {
//                    System.out.println("1 - Requirements\n");
//                }
//            case "SCREENING":
//                if (selected.recruitmentProcess().hasInterview()) {
//                    System.out.println("1 - Interview Model\n");
//                } else {
//                    System.out.println("There is no information that can be edited\n");
//                }
//            default:
//                System.out.println("There is no information that can be edited\n");
//        }

    }


    private void displayJobOpenings(Iterable<JobOpening> jobOpenings) {

        System.out.println("\n======List of Job Openings======\n");

        List<JobOpening> jobList = new ArrayList<>();
        jobOpenings.forEach(jobList::add);


        Comparator<JobOpening> comparator = new Comparator<JobOpening>() {
            @Override
            public int compare(JobOpening o1, JobOpening o2) {

                return Integer.compare(o1.identity().jobReference(), o2.identity().jobReference());
            }
        };

        jobList.sort(comparator);


        for (JobOpening jobOpening : jobList) {
            System.out.println("\033[1mJob Reference:\033[0m " + jobOpening.jobReference().jobReference() + "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpening.numberOfVacancies().toString() + "\n\033[1mJob Function:\033[0m " + jobOpening.jobFunction().toString() +
                    "\n\033[1mMode:\033[0m " + jobOpening.mode() + "\n\033[1mContract Type:\033[0m " + jobOpening.contractType() +  "\n" + jobOpening.address() + "\n\033[1mCustomer (Company Name):\033[0m " + jobOpening.companyName().user().email() +
                    "\n\033[1mDescription:\033[0m " + jobOpening.description().toString() + "\n\033[1mRegistration Date:\033[0m " + jobOpening.registrationDate().getTime() + "\n\033[1mCurrent State:\033[0m " + jobOpening.jobOpeningStatus() + "\n"  );
        }

    }
    private Customer displayCustomerMenu(List<Customer> customers) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a customer:");

            for (int i = 0; i < customers.size(); i++) {
                System.out.println((i + 1) + ". " + customers.get(i).user().email());
            }

            try {
                int filterOption = Integer.parseInt(scanner.nextLine());

                if (filterOption >= 1 && filterOption <= customers.size()) {
                    return customers.get(filterOption - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }

    private Date displayRegisterDates(List<Date> registrationDate) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a date:");

            for (int i = 0; i < registrationDate.size(); i++) {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                System.out.println((i + 1) + ". " + dateFormat.format(registrationDate.get(i)));
            }

            try {
                int filterOption = Integer.parseInt(scanner.nextLine());

                if (filterOption >= 1 && filterOption <= registrationDate.size()) {
                    return registrationDate.get(filterOption - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }

    @Override
    public String headline() {
        return "Update Job Opening";
    }
}
