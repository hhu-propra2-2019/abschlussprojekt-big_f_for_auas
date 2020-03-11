package mops.applicationService;


import mops.domain.models.QuestionPoll.QuestionPollFactory;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.QuestionPoll.QuestionPollLink;
import mops.domain.models.Repository.QuestionPollRepositoryInterface;
import mops.domain.models.User.UserId;
import org.springframework.stereotype.Service;

@Service
public class QuestionPollSyndicationService {

    private QuestionPollRepositoryInterface questionPollRepo;

    /** Schließt den Factory Prozess ab und speichert den generierte QuestionPolll ab.
     * Gibt generierte Url zurück
     * @param factory
     * @return QuestionPollLink
     */
    public QuestionPollLink publishQuestionPoll(final QuestionPollFactory factory) {
        QuestionPollId questionPollId = questionPollRepo.save(factory.build());
        QuestionPollLink link = questionPollRepo.getUrl(questionPollId);
        return link;
    }

    /** Fügt einen weiteren Nutzer zu der Liste der Nutzer hinzu, die an einer Umfrage Teilnehmen dürfen.
     * Darf nur aufgerufen werden, wenn es sich um eine geschlossene Umfrage handelt (restrictedAccess = true)
     * @param factory
     * @param userId
     */
    public void addParticipator(final QuestionPollFactory factory, UserId... userId) {
        factory.accessibilityAddUser(userId);
    }
}
