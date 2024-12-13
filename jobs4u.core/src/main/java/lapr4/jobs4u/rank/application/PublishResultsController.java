/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.rank.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.PasswordPolicy;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobApplication.application.JobApplicationService;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobApplication.domain.JobApplicationDTO;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.domain.JobReference;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;
import lapr4.jobs4u.notification.SerializeUtil;
import lapr4.jobs4u.rank.domain.Rank;
import lapr4.jobs4u.rank.repositories.RankRepository;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class PublishResultsController {

    private PasswordPolicy policy;
    private PasswordEncoder encoder;

    private final JobApplicationService service = new JobApplicationService();

    private final RankRepository rankRepository = PersistenceContext
            .repositories().ranks();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext
                .repositories().jobOpenings();


    public Iterable<JobOpening> listAllJobOpenings() {

        return jobOpeningRepository.findAll();
    }

    public Iterable<JobApplicationDTO> findCandidates(JobOpening jobOpeningSelected) {

        return service.findByJobOpening(jobOpeningSelected);
    }


    public Iterable<Id> findRankByJobReference(JobReference jobReference) {

        Optional<Rank> rankSaved = rankRepository.findByJobReference(jobReference);

        return rankSaved.get().rankingList();
    }

    public boolean publishResults(DataOutputStream output, DataInputStream input, List<JobApplicationDTO> publishResults, Integer vacancies, EmailAddress companyEmail, EmailAddress publisherEmail) throws IOException {


        byte[] publishResultsData = SerializeUtil.serialize( publishResults );
        byte[] vacanciesData = SerializationUtils.serialize(vacancies);
        byte[] companyEmailData = SerializationUtils.serialize(companyEmail);
        byte[] publisherEmailData = SerializationUtils.serialize(publisherEmail);
        List<byte[]> bytesList = new ArrayList<>();

        bytesList.add(publishResultsData);
        bytesList.add(vacanciesData);
        bytesList.add(companyEmailData);
        bytesList.add(publisherEmailData);

        MessageUtil.writeMessage(output,15,bytesList);

        CustomMessage message = MessageUtil.readMessage(input);

        return (message.code == 16);
    }

    public JobOpening storeJobOpening(JobOpening jobOpeningSelected) {

        return jobOpeningRepository.save(jobOpeningSelected);
    }



}
