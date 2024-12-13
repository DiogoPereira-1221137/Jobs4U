package lapr4.jobs4u.app.candidate.console.application;

import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.candidatemanagement.domain.Candidate;
import lapr4.jobs4u.candidatemanagement.repositories.CandidateRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.domain.JobApplication;
import lapr4.jobs4u.jobApplication.domain.JobApplicationStateDTO;
import lapr4.jobs4u.jobApplication.repositories.JobApplicationRepository;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DisplayStateJobApplicationController {

    private final CandidateRepository candidateRepository = PersistenceContext
            .repositories().candidates();

    private final JobApplicationRepository jobApplicationRepository = PersistenceContext
            .repositories().jobApplications();

    private static Socket socket;

    private final DataOutputStream output;

    private final DataInputStream input;


    public DisplayStateJobApplicationController(Socket socket) throws IOException {
        DisplayStateJobApplicationController.socket = socket;
        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(socket.getInputStream());
    }


    public CustomMessage listAllApplications() throws IOException {
        MessageUtil.writeMessage(output, FollowUpServer.REQ_JOBAPPSTATE, new ArrayList<>());

        return MessageUtil.readMessage(input);
    }

}
