package mops.application.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mops.domain.models.Validation;

import java.util.List;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollHeader;

@RequiredArgsConstructor
@Data
public class QuestionPollBuilderAndView {

    private final QuestionPollBuilder builder;
    private QuestionPollConfig config;
    private QuestionPollHeader header;
    private List<QuestionPollEntry> questionPollEntries;
    private Validation validation;

    /**
     * Methode dient zur erstellung des QuestionPoll Objektes.
     *
     * @return QuestionpPoll Objekt, welches den derzeitigen Status des Builders implementiert
     */
    public QuestionPoll startBuildingQuestionPoll() {
        return this.builder.build();
    }

}
