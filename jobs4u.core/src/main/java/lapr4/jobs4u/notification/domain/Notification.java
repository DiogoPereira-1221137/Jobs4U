package lapr4.jobs4u.notification.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import jakarta.persistence.*;
import lapr4.jobs4u.jobApplication.domain.JobApplicationDTO;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Entity
public class Notification implements Serializable, AggregateRoot<Long> {
    private static final long serialVersionUID = 1L;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "sender_email"))
    })
    private EmailAddress senderEmail;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "receiver_email"))
    })
    private EmailAddress receiverEmail;

    @Column
    private String title;

    @Column
    private String subject;

    @Column
    private String body;
    @Column
    private Calendar creationDate;
    @Column
    private boolean status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Notification(String title, String subject, String body) {
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = Calendar.getInstance();
        this.status = false;
    }

    public Notification(EmailAddress senderEmail, EmailAddress receiverEmail, String title, String subject, String body) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = Calendar.getInstance();
        this.status = false;
    }

    public Notification(String title, String subject, String body, Calendar creationDate, boolean status) {
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Notification(EmailAddress senderEmail, EmailAddress receiverEmail, String title, String subject, String body, Calendar creationDate, boolean status) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Notification(EmailAddress senderEmail, EmailAddress receiverEmail, String title, String subject, String body, Calendar creationDate, boolean status, Long id) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.status = status;
        this.id = id;
    }

    protected Notification() {
    }

    public EmailAddress senderEmail() {
        return senderEmail;
    }

    public void changeSenderEmail(EmailAddress senderEmail) {
        this.senderEmail = senderEmail;
    }

    public EmailAddress receiverEmail() {
        return receiverEmail;
    }

    public boolean status(){
        return status;
    }

    public void changeReceiverEmail(EmailAddress receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String title() {
        return title;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public String subject() {
        return subject;
    }

    public void changeSubject(String subject) {
        this.subject = subject;
    }

    public String body() {
        return body;
    }

    public void changeBody(String body) {
        this.body = body;
    }

    public boolean sameAs(Object other) {
        return false;
    }

    public Long identity() {
        return id;
    }

    public void changeId(Long id) {
        this.id = id;
    }
    

    public String emailFormat() {
        return title + "\n" + body + "\n" + subject;
    }

    public String approvadeCandidate(Name candidateName, Name companyName, int placed, Integer vacancies) {

        this.title = " **** Congratulations " + candidateName + " your application was approved **** \n";
        this.body = " In the job offer " + companyName + " you were placed in the position " + placed + " in " + vacancies + " vacancies \n ";
        this.subject = " **** We will be waiting for you for your new job :) **** ";

        return title + "\n" + body + "\n" + subject;
    }

    public String deapprovadeCandidate(Name candidateName, Name companyName, int placed, Integer vacancies) {

        this.title = " **** We apologize " + candidateName + " you application was not approved **** \n";
        this.body = " In the job offer " + companyName + " you were placed in the position " + placed + " in " + vacancies + " vacancies \n ";
        this.subject = " **** Good luck next time :( **** ";

        return title + "\n" + body + "\n" + subject;

    }


    public String approvedApplication(Name candidateName, String jobFunction, Name companyName) {

        this.title = " **** Congratulations " + candidateName + " your application was approved! **** \n";
        this.body = " We are pleased to inform you that your application for the " + jobFunction + " position at "+ companyName + " company  has been successfully reviewed and accepted.\n" ;
        this.subject = " **** We will be waiting for you for your new job :) **** ";

        return title + "\n" + body + "\n" + subject;
    }

    public String rejectedApplication(Name candidateName, String jobFunction, Name companyName) {

        this.title = " **** We apologize " + candidateName + " you application was rejected! **** \n";
        this.body = "Thank you for your interest in the " + jobFunction + " position at "+ companyName + " company!\n" +
                "After careful consideration, we regret to inform you that we have decided to move forward with other candidates who more closely match the requirements for this position. ";
        this.subject = " **** Good luck next time :( **** ";

        return title + "\n" + body + "\n" + subject;

    }

    public String customerPublishedInfo(List<JobApplicationDTO> publishList, List<Integer> positions) {

        this.title = " **** Hello " + publishList.get(0).companyName() + " your JobOpening is completed, this is the candidates we think it's the best option for you **** \n";
        this.subject = " **** Thank you for choosing Jobs4U Company **** ";
        this.body = "";
        int count=0;
        for (JobApplicationDTO application: publishList) {

            this.body+= "   " + (count+1) + ". " + application.candidateName() + " =-=-= " + (positions.get(count)!=0 ? "Placed " : "No Placed ") + "\n";

            count++;
        }

        return title + "\n" + body + "\n" + subject;
    }


    public void markAsRead() {
        this.status = true;
    }

    public NotificationDTO toDTO() {
        return new NotificationDTO(senderEmail, receiverEmail, title, subject, body, creationDate, status, id);
    }


}
