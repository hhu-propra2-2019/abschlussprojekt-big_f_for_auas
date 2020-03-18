package mops.application.services;


import lombok.NoArgsConstructor;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollLink;
import mops.domain.models.repository.QuestionPollRepositoryInterface;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor // PMD zuliebe
public class QuestionPollSyndicationService {

    private transient QuestionPollRepositoryInterface questionPollRepo;

    /** Schließt den Factory Prozess ab und speichert den generierte QuestionPolll ab.
     * Gibt generierte Url zurück
     * @param builder
     * @return QuestionPollLink
     */
    public QuestionPollLink publishQuestionPoll(final QuestionPollBuilder builder) {
        return questionPollRepo.save(builder.build());
    }


    /** Fügt eine Liste von UserIds zu der Liste der Nutzer hinzu, die an einer Umfrage Teilnehmen dürfen.
     * Darf nur aufgerufen werden, wenn es sich um eine geschlossene Umfrage handelt (restrictedAccess = true)
     * @param builder
     * @param userId
     */
    public void addParticipants(final QuestionPollBuilder builder, List<UserId> userIds) {
        builder.questionPollParticipants(userIds);
    }
}
