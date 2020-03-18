package mops.application.services;


import lombok.NoArgsConstructor;
import mops.controllers.dtos.QuestionPollAccessibilityDto;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollLink;
import mops.domain.models.repository.QuestionPollRepositoryInterface;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

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

    /**
     * Setzt das Accessibility Objekt in der Factory.
     * @param builder
     * @param accessibilityDto
     */
    public void addAccessibility(final QuestionPollBuilder builder,
                                 final QuestionPollAccessibilityDto accessibilityDto) {

    }

    /** Fügt einen weiteren Nutzer zu der Liste der Nutzer hinzu, die an einer Umfrage Teilnehmen dürfen.
     * Darf nur aufgerufen werden, wenn es sich um eine geschlossene Umfrage handelt (restrictedAccess = true)
     * @param builder
     * @param userId
     */
    public void addParticipator(final QuestionPollBuilder builder, UserId... userId) {

    }
}
