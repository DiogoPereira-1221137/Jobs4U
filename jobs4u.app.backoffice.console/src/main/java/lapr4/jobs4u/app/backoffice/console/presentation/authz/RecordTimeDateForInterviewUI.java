package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.interview.domain.Interview;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobopening.application.RecordTimeDateForInterviewController;
import lapr4.jobs4u.jobopening.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RecordTimeDateForInterviewUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordTimeDateForInterviewUI.class);

    private final RecordTimeDateForInterviewController controller = new RecordTimeDateForInterviewController();

    private Calendar date;
    private Calendar hour;

    @Override
    protected boolean doShow() {
        JobOpening selectedJobOpening = selectJobOpening();
        if(selectedJobOpening!=null) {
            JobApplication selectedCandidate = selectCandidate(selectedJobOpening);

            if ( selectedCandidate != null) {
               List<Interview> existingInterviews = controller.findInterviewsByJobApplication(selectedCandidate);

               if(!existingInterviews.isEmpty()){
                   int size =existingInterviews.size();
                   Interview existingInterview = existingInterviews.get(size-1);
                   Scanner scanner = new Scanner(System.in);
                   Calendar date = existingInterview.interviewSchedule().date();
                   Calendar hour = existingInterview.interviewSchedule().hour();

                   SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                   SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

                   String formattedDate = dateFormatter.format(date.getTime());
                   String formattedHour = timeFormatter.format(hour.getTime());

                   System.out.println("A scheduled interview already exists for this candidate:");
                   System.out.println("\033[1mCandidate:\033[0m");
                   System.out.println("\033[1mName:\033[0m " + selectedCandidate.Candidate().user().name() +"\033[1mEmail:\033[0m " + selectedCandidate.Candidate().user().email());
                   System.out.println("\n\033[1mDate:\033[0m " + formattedDate);
                   System.out.println("\033[1mHour:\033[0m " + formattedHour );
                   System.out.println("\nDo you want to reschedule the Interview? Choose an option:");
                   System.out.println("1. Update Interview");
                   System.out.println("0. Exit");

                   int choice = -1;
                   while (true) {
                       try {
                           choice = Integer.parseInt(scanner.nextLine().trim());
                           if (choice == 1 || choice == 0) {
                               break;
                           } else {
                               System.out.println("Invalid option. Please enter 0 or 1.");
                           }
                       } catch (NumberFormatException e) {
                           System.out.println("Invalid input. Please enter a number (0 or 1).");
                       }
                   }

                   switch (choice) {
                       case 1:
                           InterviewPhase interviewPhase = selectedJobOpening.recruitmentProcess().getInterviewPhase();
                           if (interviewPhase != null) {
                               TimePeriod timePeriod = interviewPhase.timePeriod();
                               Calendar startDate = timePeriod.startDate();
                               Calendar endDate = timePeriod.endDate();

                               System.out.println("\nInterview Phase Time Period:");
                               System.out.println("Start Date: " + new SimpleDateFormat("dd-MM-yyyy").format(startDate.getTime()));
                               System.out.println("End Date: " + new SimpleDateFormat("dd-MM-yyyy").format(endDate.getTime()));

                               date = typeDateWithinTimePeriod(startDate, endDate);
                               hour = typeHourWithinTimePeriod();
                               if(!controller.findEqualInterview(selectedJobOpening,date,hour).iterator().hasNext()) {
                                   controller.updateInterview(existingInterview, date, hour);

                                   System.out.println("\n==========Interview Schedule Registered Successfully!==========");
                                   displayInterviewSchedule(selectedJobOpening, selectedCandidate, date, hour);
                               }else{
                                   System.out.println("\nAn Interview for that Job Opening, hour and date is already registered");
                               }
                           }
                           break;
                       case 0:
                           System.out.println("Returning to the main menu...");
                           return false;
                       default:
                           System.out.println("Invalid option. Returning to the main menu...");
                   }

               }else {
                   registerInterview(selectedJobOpening, selectedCandidate);
               }

            }
        }
        return false;
    }

    private JobOpening selectJobOpening() {

        List<JobOpening> jobOpenings = controller.findAllJobOpeningsWithInterview();
        if (!jobOpenings.isEmpty()) {
            return displayJobOpeningMenu(jobOpenings);
        } else {
            System.out.println("No Active Job Openings with the Interview Phase that are currently at the Screening or Interview Phase  available.");
            return null;
        }
    }

    private JobApplication selectCandidate(JobOpening selectedJobOpening) {

        List<JobApplication> jobApplications = controller.getAcceptedJobApplicationsByJobOpening(selectedJobOpening);
        if (!jobApplications.isEmpty()) {
            return displayCandidateMenu(jobApplications);
        } else {
            System.out.println("No Candidates available.");
            return null;
        }
    }
    private JobOpening displayJobOpeningMenu(List<JobOpening> jobOpenings) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a Job Opening:");

            for (int i = 0; i < jobOpenings.size(); i++) {
                System.out.println((i + 1) + ". " + "\033[1mJob Reference:\033[0m " + jobOpenings.get(i).jobReference().jobReference() + "\n   \033[1mNumber Of Vacancies:\033[0m " + jobOpenings.get(i).numberOfVacancies().toString() + "\n   \033[1mJob Function:\033[0m " + jobOpenings.get(i).jobFunction().toString() +
                        "\n   \033[1mMode:\033[0m " + jobOpenings.get(i).mode() + "\n   \033[1mContract Type:\033[0m " + jobOpenings.get(i).contractType() +  "\n   " + jobOpenings.get(i).address() + "\n   \033[1mCustomer (Company Name):\033[0m " + jobOpenings.get(i).companyName().user().email() +
                        "\n   \033[1mDescription:\033[0m " + jobOpenings.get(i).description().toString() + "\n   \033[1mRegistration Date:\033[0m " + jobOpenings.get(i).registrationDate().getTime() + "\n   \033[1mCurrent State:\033[0m " + jobOpenings.get(i).jobOpeningStatus() + "\n"  );
            }

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= jobOpenings.size()) {
                    return jobOpenings.get(choice - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }

    private JobApplication displayCandidateMenu(List<JobApplication> jobApplications) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select a Candidate:");

            for (int i = 0; i < jobApplications.size(); i++) {
                System.out.println((i + 1) + ". " + "\033[1mName:\033[0m " + jobApplications.get(i).candidate().user().name() + " \033[1mEmail:\033[0m " + jobApplications.get(i).candidate().user().email() );
            }

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= jobApplications.size()) {
                    return jobApplications.get(choice - 1);
                } else {
                    System.out.println("Invalid choice! Please select a number from the list!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number!");
            }
        }
    }

    public Calendar typeDateWithinTimePeriod(Calendar startDate, Calendar endDate){
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        while(true){
            System.out.println("Enter a date within the interview phase time period for the selected job opening:");
            String dateString = scanner.nextLine();

            try{
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateFormat.parse(dateString));

                if (calendar.compareTo(startDate) >= 0 && calendar.compareTo(endDate) <= 0) {
                    return calendar;
                } else {
                    System.out.println("Invalid date! Please enter a date within the specified time period.");
                }

            }catch (ParseException e){
                System.out.println("Invalid date format! Please enter the date in the format: yyyy-MM-dd");
            }
        }
    }


    public Calendar typeHourWithinTimePeriod(){
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        while(true){
            System.out.println("Enter the hour (HH:mm) between 08:00 and 19:00 (excluding 19:00):");
            String hourString= scanner.nextLine();

            try{
                Date date = hourFormat.parse(hourString);
                Calendar calendar =Calendar.getInstance();
                calendar.setTime(date);

                int hourOfDay =calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                if ((hourOfDay==8 || hourOfDay == 19) && minute!=0) {
                    System.out.println("Invalid hour! Please enter a full hour (HH:00).");
                } else if (hourOfDay >= 8 && hourOfDay < 19) {
                    return calendar;
                } else {
                    System.out.println("Invalid hour! Please enter an hour within the specified time period.");
                }
            }catch (ParseException e){
                System.out.println("Invalid date format! Please enter the date in the format: yyyy-MM-dd");
            }

        }

    }

    public void registerInterview(JobOpening jobOpening, JobApplication jobApplication){
        InterviewPhase interviewPhase = jobOpening.recruitmentProcess().getInterviewPhase();
        if (interviewPhase != null) {
            TimePeriod timePeriod = interviewPhase.timePeriod();
            Calendar startDate = timePeriod.startDate();
            Calendar endDate = timePeriod.endDate();

            System.out.println("\nInterview Phase Time Period:");
            System.out.println("Start Date: " + new SimpleDateFormat("dd-MM-yyyy").format(startDate.getTime()));
            System.out.println("End Date: " + new SimpleDateFormat("dd-MM-yyyy").format(endDate.getTime()));

            date = typeDateWithinTimePeriod(startDate, endDate);
            hour = typeHourWithinTimePeriod();

        }

        try {
            if(!controller.findEqualInterview(jobOpening,date,hour).iterator().hasNext()) {
                this.controller.registerInterview(jobOpening,jobApplication, date, hour);

                System.out.println("\n==========Interview Schedule Registered Successfully!==========");
                displayInterviewSchedule(jobOpening, jobApplication, date, hour);
            }else{
                System.out.println("\nAn Interview for that Job Opening, hour and date is already registered");
            }

        } catch (final IntegrityViolationException | ConcurrencyException e) {
            LOGGER.error("Error performing the operation", e);
            System.out.println("Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
        }

    }
    public void displayInterviewSchedule(JobOpening jobOpening, JobApplication jobApplication, Calendar date, Calendar hour) {

        InterviewPhase interviewPhase = jobOpening.recruitmentProcess().getInterviewPhase();
            TimePeriod timePeriod = interviewPhase.timePeriod();
            Calendar startDate = timePeriod.startDate();
            Calendar endDate = timePeriod.endDate();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        String formattedStartDate = dateFormatter.format(startDate.getTime());
        String formattedEndDate = dateFormatter.format(endDate.getTime());
        String formattedDate = dateFormatter.format(date.getTime());
        String formattedHour = timeFormatter.format(hour.getTime());
        Calendar registrationDate =jobOpening.registrationDate();
        String formattedRegistrationDate=dateFormatter.format(registrationDate.getTime());

        System.out.println("\n\033[1mJob Opening:\033[0m\n" +
                "\033[1mJob Reference:\033[0m " + jobOpening.jobReference().jobReference() +
                "\n\033[1mNumber Of Vacancies:\033[0m " + jobOpening.numberOfVacancies().toString() +
                "\n\033[1mJob Function:\033[0m " + jobOpening.jobFunction().toString() +
                "\n\033[1mMode:\033[0m " + jobOpening.mode() +
                "\n\033[1mContract Type:\033[0m " + jobOpening.contractType() +
                "\n" + jobOpening.address() +
                "\n\033[1mCustomer (Company Name):\033[0m " + jobOpening.companyName().user().email() +
                "\n\033[1mDescription:\033[0m " + jobOpening.description().toString() +
                "\n\033[1mRegistration Date:\033[0m " +formattedRegistrationDate +
                "\n\033[1mCurrent State:\033[0m " + jobOpening.jobOpeningStatus() +
                "\n\n\033[1mInterview Phase Tipe Period:\033[0m " +
                "\n\033[1mStart Date:\033[0m " + formattedStartDate +
                "\n\033[1mStart Date:\033[0m " + formattedEndDate +
                "\n\n\033[1mCandidate:\033[0m\n" +
                "\033[1mName:\033[0m"+ jobApplication.Candidate().user().name() +
                "\n\033[1mEmail:\033[0m " + jobApplication.Candidate().user().email() +
                "\n\n\033[1mDate and Hour:\033[0m" +
                "\n\033[1mDate:\033[0m " + formattedDate +
                "\n\033[1mHour:\033[0m " + formattedHour);
    }

    @Override
    public String headline() {
        return "Record Interview with Candidate";
    }
}
