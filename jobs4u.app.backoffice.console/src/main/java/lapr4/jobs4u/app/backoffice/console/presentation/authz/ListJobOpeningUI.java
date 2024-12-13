package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.jobopening.application.ListJobOpeningController;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type List job opening ui.
 */
public class ListJobOpeningUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListJobOpeningUI.class);

    private final ListJobOpeningController controller = new ListJobOpeningController();

    @Override
    protected boolean doShow() {
        Scanner scanner = new Scanner(System.in);

        int filterOption;
        while (true) {
            System.out.println("Filter Options:");
            System.out.println("1. Show All Job Openings");
            System.out.println("2. Filter Job Openings By Customer");
            System.out.println("3. Filter Job Openings By Registration Date");

            System.out.print("\nEnter your filterOption:\n");
            try {
                filterOption = Integer.parseInt(scanner.nextLine());

                switch (filterOption) {
                    case 1:
                        displayJobOpenings(controller.filterJobOpeningsBySystemUser());
                        return false;
                    case 2:
                        Customer customer = selectCustomer();
                        if (customer != null) {
                            displayJobOpenings(controller.filterJobOpeningsByCustomer(customer));
                            return false;
                        }
                        break;
                    case 3:

                        Date registrationDate = selectRegistrationDate();
                        if (registrationDate != null) {
                            displayJobOpenings(controller.filterJobOpeningsByRegistrationDate(registrationDate));
                            return false;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private Customer selectCustomer() {
        List<Customer> customers = controller.filterCustomersBySystemUser();
        if (!customers.isEmpty()) {
            return displayCustomerMenu(customers);
        } else {
            System.out.println("No customers available.");
            return null;
        }
    }

    private Date selectRegistrationDate() throws ParseException {
        List<Date> registrationDates = controller.allJobOpeningRegistrationDates();
        if (!registrationDates.isEmpty()) {
            return displayRegisterDates(registrationDates);
        } else {
            System.out.println("No customers available.");
            return null;
        }
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
        return "List Job Openings";
    }
}
