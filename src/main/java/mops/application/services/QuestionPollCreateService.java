package mops.application.services;

import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollHeader;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;
import java.util.List;

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

    /** fügt dem builder und dem builderAndView ein Header hinzu.
     *
     * @param questionPollBuilderAndView
     * @param questionPollHeader
     */
    public void addQuestionPollHeader(
            final QuestionPollBuilderAndView questionPollBuilderAndView, final QuestionPollHeader questionPollHeader) {
        QuestionPollBuilder builder = questionPollBuilderAndView.getBuilder();
        questionPollBuilderAndView.setValidation(
                builder.questionPollHeader(questionPollHeader).getValidationState()
        );
        questionPollBuilderAndView.setHeader(questionPollHeader);
    }

    /**  fügt dem builder und dem builderAndView ein Liste von Entries hinzu.
     *
     * @param questionPollBuilderAndView
     * @param questionPollEntries
     */
    public void initQuestionPollEntryList(
            final QuestionPollBuilderAndView questionPollBuilderAndView,
            final List<QuestionPollEntry> questionPollEntries) {
        QuestionPollBuilder builder = questionPollBuilderAndView.getBuilder();
        questionPollBuilderAndView.setValidation(
                builder.questionPollEntries(questionPollEntries)
                        .getValidationState()
        );
        questionPollBuilderAndView.setQuestionPollEntries(questionPollEntries);
    }


    /** setzt die Option ob auch mehrfach Entries gewaehlt werden können.
     *
     * @param questionPollBuilderAndView
     * @param usingMultiChoice
     */
    public void setUsingMultiChoice(final QuestionPollBuilderAndView questionPollBuilderAndView,
                                    final boolean usingMultiChoice) {
        QuestionPollBuilder builder = questionPollBuilderAndView.getBuilder();
        QuestionPollConfig oldConfig = questionPollBuilderAndView.getConfig();
        QuestionPollConfig newConfig = oldConfig.withUsingMultiChoice(usingMultiChoice);
        questionPollBuilderAndView.setValidation(
                builder.questionPollConfig(newConfig).getValidationState()
        );
        questionPollBuilderAndView.setConfig(newConfig);
    }


    /** setzt die Option als Alias angezeigt zu werden bzw nicht mit der mops id.
     *
     * @param questionPollBuilderAndView
     * @param usingAlias
     */
    public void setUsingAlias(final QuestionPollBuilderAndView questionPollBuilderAndView, final boolean usingAlias) {
        QuestionPollBuilder builder = questionPollBuilderAndView.getBuilder();
        QuestionPollConfig oldConfig = questionPollBuilderAndView.getConfig();
        QuestionPollConfig newConfig = oldConfig.withUsingAlias(usingAlias);
        questionPollBuilderAndView.setValidation(
                builder.questionPollConfig(newConfig).getValidationState()
        );
        questionPollBuilderAndView.setConfig(newConfig);
    }
}
