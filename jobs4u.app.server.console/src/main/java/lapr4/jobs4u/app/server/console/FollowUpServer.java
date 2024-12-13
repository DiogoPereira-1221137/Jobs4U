package lapr4.jobs4u.app.server.console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Name;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Role;
import lapr4.jobs4u.app.server.console.application.ListAllApplicationsReqController;
import lapr4.jobs4u.app.server.console.application.ListJobOpeningServerController;
import lapr4.jobs4u.app.server.console.application.NotifyReqController;
import lapr4.jobs4u.customer.domain.Customer;
import lapr4.jobs4u.infrastructure.authz.AuthenticationCredentialHandler;
import lapr4.jobs4u.infrastructure.authz.CredentialHandler;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.*;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.notification.domain.Notification;
import lapr4.jobs4u.notification.domain.NotificationDTO;
import lapr4.jobs4u.notification.repository.NotificationRepository;
import lapr4.jobs4u.usermanagement.domain.PasswordGenerator;
import org.springframework.stereotype.Component;

@Component
public class FollowUpServer {

    public static final int COMMTEST = 0;
    public static final int DISCONN = 1;
    public static final int ACK = 2;
    public static final int ERR = 3;
    public static final int AUTH = 4;
    public static final int REQ_NOTFLIST = 5;
    public static final int RESP_NOTFLIST = 6;
    public static final int REQ_UNREAD = 7;
    public static final int RESP_UNREAD = 8;
    public static final int REQ_JOBOP = 9;
    public static final int RESP_JOBOP = 10;
    public static final int REQ_HMUCHJOBAPP = 11;
    public static final int RESP_HMUCHJOBAPP = 12;
    public static final int REQ_MARKREAD = 13;
    public static final int REQ_SENDNOTF = 14;
    public static final int REQ_SENDPUBLSIH = 15;
    public static final int RESP_SENDPUBLSIH = 16;

    public static final int REQ_JOBAPPSTATE = 17;
    public static final int RESP_JOBAPPSTATE = 18;

    public static final int REQ_JOBAPPRESULT = 19;
    public static final int RESP_JOBAPPRESULT= 20;


    private static NotificationRepository notificationRepository =  PersistenceContext
            .repositories().notifications();


    public static void main(String args[]) throws Exception {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new PasswordGenerator(),
                new PlainTextEncoder());
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("\n\n======================================================");
        System.out.println("Server is listening on port 12345...");
        System.out.println("======================================================");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connection from " + socket.getInetAddress().getHostAddress() +
                    ", port number " + socket.getPort());

            new ClientHandler(socket,notificationRepository).start();
        }
    }
}



class ClientHandler extends Thread {

    private final ListJobOpeningServerController listJobOpeningServerController = new ListJobOpeningServerController();

    private CredentialHandler credentialHandler;

    private AuthorizationService authz;
    private Socket socket;
    private NotificationRepository notificationRepository;

    private NotifyReqController notifyReqController;

    private ListAllApplicationsReqController listAllApplicationsReqController;




    public ClientHandler(Socket socket, NotificationRepository notificationRepository) {
        this.socket = socket;
        notifyReqController = new NotifyReqController(socket);
        listAllApplicationsReqController = new ListAllApplicationsReqController(socket);
        this.notificationRepository = notificationRepository;
        this.credentialHandler = new AuthenticationCredentialHandler();
        this.authz = AuthzRegistry.authorizationService(); // For Authentication
    }

    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            while (!socket.isClosed()) {

                CustomMessage message = MessageUtil.readMessage(input);

                switch (message.code) {

                    case FollowUpServer.COMMTEST:
                        System.out.println("CONNECTION STABLE");
                        MessageUtil.writeMessage(output, FollowUpServer.ACK, new ArrayList<>());
                        if (authz.hasSession()) {
                            System.out.println("LOGGED USER> " + authz.session().get().authenticatedUser().email());
                        }
                        break;

                    case FollowUpServer.DISCONN:
                        System.out.println("CONNECTION TERMINATED");
                        MessageUtil.writeMessage(output, FollowUpServer.ACK, new ArrayList<>());
                        socket.close();
                        authz.clearSession();
                        break;

                    case FollowUpServer.ACK:
                        /*
                         * Most Likely to be Unused in FollowUpServer.
                         * From my understanding the Candidate/Customer apps should never be able to send this message code, as the only send Requests.
                         * Only possible exception would be if they are responding to a COMMTEST or DISCONN, though I fail to see a reason for that situation to happen in the way described.
                         */
                        System.out.println("THIS CODE IT'S NOT SUPPOSED TO BE SENT AS A REQUEST");
                        break;

                    case FollowUpServer.ERR:
                        /*
                         * Most Likely to be Unused in FollowUpServer.
                         * From my understanding the Candidate/Customer apps should never be able to send this message code, as the only send Requests.
                         * Only possible exception would be if they are responding to a COMMTEST or DISCONN, though I fail to see a reason for that situation to happen in the way described.
                         */

                        System.out.println("THIS CODE IT'S NOT SUPPOSED TO BE SENT AS A REQUEST");

                        break;

                    case FollowUpServer.AUTH:
                        System.out.println("LOGIN ATTEMPT");
                        //Receives info containing email (DATA1) and password (DATA2) of user
                        //Maybe send role (app) on DATA3? (the onlyWithThis variable presented below)
                        //Try to log in. If success respond with ACK else ERR
                        if (credentialHandler.authenticated(new String(message.data.get(0), StandardCharsets.UTF_8), new String(message.data.get(1), StandardCharsets.UTF_8), Role.valueOf(new String(message.data.get(2), StandardCharsets.UTF_8)))) {
                            MessageUtil.writeMessage(output, FollowUpServer.ACK, new ArrayList<>());
                            System.out.println("LOG IN SUCCESSFUL> " + authz.session().get().authenticatedUser().email());

                        } else {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("LOG IN FAILED. PLEASE CHECK CREDENTIALS AND TRY AGAIN.".getBytes())));
                            System.out.println("LOG IN FAILED");
                        }
                        break;


                        case FollowUpServer.REQ_NOTFLIST:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }
                        EmailAddress email = authz.session().get().authenticatedUser().email();
                        List<NotificationDTO> notificationsList = notifyReqController.nofityRep(email);

                        MessageUtil.writeMessage(output, FollowUpServer.RESP_NOTFLIST, new ArrayList<>(Collections.singleton(SerializeUtil.serializeIterable(notificationsList))));
                        break;



                        case FollowUpServer.REQ_UNREAD:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }
                        Integer count = notificationRepository.unReadCountByReceiverEmaill(authz.session().get().authenticatedUser().email());
                        MessageUtil.writeMessage(output, FollowUpServer.RESP_UNREAD, new ArrayList<>(Collections.singleton(String.valueOf(count).getBytes())));
                        break;


                    case FollowUpServer.REQ_JOBOP:
                        // If not logged in, Immediately Respond with ERR
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }
//                        Customer customer = SerializeUtil.deserializeObject(message.data.get(0));

                        Customer customer = listJobOpeningServerController.findByEmail(authz.session().get().authenticatedUser().email());

                        Iterable<JobOpening> jobOpeningList = listJobOpeningServerController.findAllByCustomer(customer);

                        byte[] jobOpeningListData = SerializeUtil.serializeIterable(jobOpeningList);

                        MessageUtil.writeMessage(output, FollowUpServer.RESP_JOBOP, new ArrayList<>(Collections.singleton(jobOpeningListData)));

                        break;

                    case FollowUpServer.REQ_HMUCHJOBAPP:
                        // If not logged in, Immediately Respond with ERR
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }
                        JobOpening jobOpening = SerializeUtil.deserializeObject(message.data.get(0));

                        List<JobApplication> jobApplications = (List<JobApplication>) listJobOpeningServerController.findByJobOpening(jobOpening);

                        byte[] jobApplicationsData = SerializeUtil.serialize(jobApplications.size());

                        MessageUtil.writeMessage(output, FollowUpServer.RESP_HMUCHJOBAPP, new ArrayList<>(Collections.singleton(jobApplicationsData)));

                        break;



                    case FollowUpServer.REQ_MARKREAD:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }

                        NotificationDTO notificationDTO = SerializeUtil.deserializeObject(message.data.get(0));

                        Notification notif = notifyReqController.maskAsReadRep(notificationDTO);

                        if (notif.receiverEmail().equals(authz.session().get().authenticatedUser().email())){
                            notif.markAsRead();
                            notificationRepository.save(notif);
                            MessageUtil.writeMessage(output, FollowUpServer.ACK, new ArrayList<>());

                        } else {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("YOU'RE NOT SUPPOSED TO BE HERE".getBytes())));
                        }
                        break;



                    case FollowUpServer.REQ_SENDNOTF:
                        // If not logged in, Immediately Respond with ERR. Possible Example:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }

                        Notification notification = SerializeUtil.deserializeObject(message.data.get(0));

                        assert notification != null;
                        boolean sender = SendEmail.sendEmail(notification.receiverEmail().toString(), notification.senderEmail().toString(), notification.emailFormat());

                        if (sender) {
                            System.out.println("It was sent successfully from the server side");
                            notificationRepository.save(notification);

                        } else System.out.println("There was an error, we couldn't send it");



                        break;


                    case FollowUpServer.REQ_SENDPUBLSIH:
                        // If not logged in, Immediately Respond with ERR. Possible Example:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }

                        List<JobApplicationDTO> publishList = SerializeUtil.deserializeList(message.data.get(0));
                        Integer vacancies = SerializeUtil.deserializeObject(message.data.get(1));
                        EmailAddress companyEmail = SerializeUtil.deserializeObject(message.data.get(2));
                        EmailAddress publisherEmail = SerializeUtil.deserializeObject(message.data.get(3));
                        List<Integer> positions = new ArrayList<>();

                        Name jobOffer = publishList.get(0).companyName();

                        boolean verify = true;

                        int placed = 1;
                        for (JobApplicationDTO job : publishList) {
                            boolean response = false;

                            //TODO: Alterar para o email do candidato e do customer

                              Notification publish = new Notification(publisherEmail,job.candidateEmail() ,"a","b","c");
                            if (placed<=vacancies){
                                publish.approvadeCandidate(job.candidateName(),jobOffer,placed,vacancies);
                                response = SendEmail.sendEmail(publish.senderEmail().toString(), publish.receiverEmail().toString(), publish.emailFormat());
                                positions.add(placed);

                            }
                            else {
                                publish.deapprovadeCandidate(job.candidateName(),jobOffer,placed,vacancies);
                                response = SendEmail.sendEmail(publish.senderEmail().toString(), publish.receiverEmail().toString(), publish.emailFormat());
                                positions.add(0);
                            }

                            //TODO: Fazer um controller ?????
                            if (response) notificationRepository.save(publish);
                            else {
                                System.out.println( "Unable to send email");
                                MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>());
                                verify = false;
                                break;

                            }



                            placed++;

                        }

                        // TODO: Fazer umas validações extras aq e na US1020
                        if (verify) {
                            //EmailAddress.valueOf("1221137@isep.ipp.pt")
                            Notification customerNotify = new Notification(publisherEmail, companyEmail , "a", "b", "c");
                            customerNotify.customerPublishedInfo(publishList, positions);
                            boolean response = SendEmail.sendEmail(customerNotify.senderEmail().toString(), customerNotify.receiverEmail().toString(), customerNotify.emailFormat());


                            if (response){
                                notificationRepository.save(customerNotify);
                                MessageUtil.writeMessage(output, FollowUpServer.RESP_SENDPUBLSIH, new ArrayList<>());
                            }
                            else MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>());
                        }
                        else MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>());

                        break;

                    case FollowUpServer.REQ_JOBAPPSTATE:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }
                        EmailAddress candidateEmail = authz.session().get().authenticatedUser().email();
                        List<JobApplicationStateDTO> jobApplicationStateDTOList = listAllApplicationsReqController.listAllApplications(candidateEmail);

                        MessageUtil.writeMessage(output, FollowUpServer.RESP_JOBAPPSTATE, new ArrayList<>(Collections.singleton(SerializeUtil.serializeIterable(jobApplicationStateDTOList))));
                        break;

                    case FollowUpServer.REQ_JOBAPPRESULT:
                        if (!authz.hasSession()) {
                            MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>(Collections.singleton("ACCESS DENIED, PLEASE LOG IN".getBytes())));
                            break;
                        }
                        List<JobApplicationResultDTO> jobApplicationsList = SerializeUtil.deserializeList(message.data.get(0));
                        EmailAddress customerM_email = SerializeUtil.deserializeObject(message.data.get(1));


                        String jobFunction = jobApplicationsList.get(0).jobFunction();
                        Name companyName = jobApplicationsList.get(0).companyName();

                        boolean response = false;

                        for (JobApplicationResultDTO jobApplication : jobApplicationsList) {
                            Notification notify = new Notification(customerM_email ,jobApplication.candidateEmail() ,"a","b","c");
                            if (jobApplication.status().equals(StatusType.APPROVED)) {
                                notify.approvedApplication(jobApplication.candidateName(), jobFunction, companyName);
                                response = SendEmail.sendEmail(notify.senderEmail().toString(), notify.receiverEmail().toString(), notify.emailFormat());

                            } else if (jobApplication.status().equals(StatusType.REJECTED)) {

                                notify.rejectedApplication(jobApplication.candidateName(), jobFunction, companyName);
                                response = SendEmail.sendEmail(notify.senderEmail().toString(), notify.receiverEmail().toString(), notify.emailFormat());
                            }

                            if (response) {
                                notificationRepository.save(notify);
                                MessageUtil.writeMessage(output, FollowUpServer.RESP_JOBAPPRESULT, new ArrayList<>());
                            } else {
                                System.out.println("Unable to send email");
                                MessageUtil.writeMessage(output, FollowUpServer.ERR, new ArrayList<>());
                                break;

                            }
                        }
                        break;


                    default:
                        // Message Code 255 can also work as an UNKNOWN_COMMAND, but I believe ERR also encompasses this function
                        // "Used in response to unsuccessful requests that caused an error. This response message may carry a human-readable phrase explaining the error." from the RCOMP project description
                        // The human-readable response would still be UNKNOWN_COMMAND or perhaps a fancier message like "This is an unknown Message Code. There are no Commands associated with it"
                        MessageUtil.writeMessage(output, 0xFF, new ArrayList<>(Collections.singleton("UNKNOWN_COMMAND".getBytes())));
                        break;
                }
            }

        } catch (EOFException e) {

        } catch (SocketException e){
            System.out.println("Client disconnected");
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }







}




