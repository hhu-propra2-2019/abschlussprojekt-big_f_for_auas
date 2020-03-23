package mops.application.services;


import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.user.UserId;
import mops.domain.repositories.QuestionPollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
//TODO: was ist das hier?
@Service
public class QuestionPollSyndicationService {

    private final transient QuestionPollRepository questionPollRepo;

    @Autowired
    public QuestionPollSyndicationService(QuestionPollRepository questionPollRepo) {
        this.questionPollRepo = questionPollRepo;
    }

//    /**
//     * Schließt den Builder Prozess ab und speichert den generierte QuestionPolll ab.
//     * Gibt generierte Url zurück
//     *
//     * @param builder
//     * @return QuestionPollLink
//     */
    /*
    public PollLink publishQuestionPoll(final QuestionPollBuilder builder) {
        return questionPollRepo.save(builder.build());
    }
    */


    /**
     * Fügt eine Liste von UserIds zu der Liste der Nutzer hinzu, die an einer Umfrage Teilnehmen dürfen.
     * Darf nur aufgerufen werden, wenn es sich um eine geschlossene Umfrage handelt (restrictedAccess = true)
     *
     * @param builder
     * @param userIds
     */
    public void addParticipants(final QuestionPollBuilder builder, Set<UserId> userIds) {
        builder.participants(userIds);
    }
}
