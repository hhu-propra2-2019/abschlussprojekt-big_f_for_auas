package mops.application.services;


import mops.controllers.dtos.QuestionPollAccessibilityDto;
import mops.domain.models.questionpoll.QuestionPollFactory;
import mops.domain.models.questionpoll.QuestionPollLink;
import mops.domain.models.repository.QuestionPollRepositoryInterface;
import mops.domain.models.user.UserId;
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
        QuestionPollLink  questionPollLink  = questionPollRepo.save(factory.build());
        return questionPollLink;
    }

    /**
     * Setzt das Accessibility Objekt in der Factory.
     * @param factory
     * @param accessibilityDto
     */
    public void addAccessibility(final QuestionPollFactory factory,
                                 final QuestionPollAccessibilityDto accessibilityDto) {
        factory.accessibility(accessibilityDto);
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
