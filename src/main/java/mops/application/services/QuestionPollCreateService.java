package mops.application.services;

import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

@Service
public class QuestionPollCreateService {
    /**
     * Initiale Methode zur Erstellung der Abstimmung.
     *
     * @param owner User der die Terminfindung erstellt.
     * @return Lombok-Builder QuestionPoll Objekt.
     */
    public QuestionPollBuilderAndView initializeQuestionPoll(final UserId owner) {
        QuestionPollBuilder builder = QuestionPoll.builder();
        QuestionPollBuilderAndView questionPollBuilderAndView = new QuestionPollBuilderAndView(builder);
        //Hinzufuegen der default configuration fuer einen QuestionPoll (s. QuestionPollConfig NoArgsConstructor)
        QuestionPollConfig defaultConfiguration = new QuestionPollConfig();
        questionPollBuilderAndView.setConfig(defaultConfiguration);
        //Setze den "creator" und die "default configuration" und validiere.
        questionPollBuilderAndView.setValidation(
                builder
                        .owner(owner)
                        .questionPollConfig(defaultConfiguration)
                        .getValidationState()
        );

        return questionPollBuilderAndView;
    }
}
