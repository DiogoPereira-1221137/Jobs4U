package lapr4.jobs4u.notification.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.representations.dto.DTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Calendar;

@DTO
@Data
public class NotificationDTO implements Serializable{
  
    
    private EmailAddress senderEmail;

    private EmailAddress receiverEmail;

    private String title;


    private String subject;


    private String body;

    private Calendar creationDate;

    private boolean status;

    private Long id;


    public NotificationDTO(String title, String subject, String body, Calendar creationDate, boolean status) {
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.status = status;
    }

    public NotificationDTO(EmailAddress senderEmail, EmailAddress receiverEmail, String title, String subject, String body, Calendar creationDate, boolean status, Long id) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.status = status;
        this.id = id;
    }

    protected NotificationDTO() {
    }

    public Notification toObject() {
        return new Notification(senderEmail, receiverEmail, title, subject, body, creationDate, status, id);
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


    public String emailFormat() {
        return title + "\n" + body + "\n" + subject;
    }

    public String approvadeCandidate(Name candidateName, Name companyName, int placed, Integer vacancies) {

        this.title = " **** Congratulations " + candidateName + "you were approved **** \n";
        this.body = " In the job offer " + companyName + " you were placed in the position " + placed + " in " + vacancies + " vacancies \n ";
        this.subject = " **** We will be waiting for you for your new job :) **** ";

        return title + "\n" + body + "\n" + subject;
    }

    public String deapprovadeCandidate(Name candidateName, Name companyName, int placed, Integer vacancies) {

        this.title = " **** We apologize " + candidateName + " you were not approved **** ";
        this.body = " In the job offer " + companyName + " you were placed in the position " + placed + " in " + vacancies + " vacancies \n ";
        this.subject = " **** Good luck next time :( **** ";

        return title + "\n" + body + "\n" + subject;

    }


    public void markAsRead() {
        this.status = true;
    }

}
