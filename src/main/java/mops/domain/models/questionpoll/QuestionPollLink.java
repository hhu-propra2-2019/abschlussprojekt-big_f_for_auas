package mops.domain.models.questionpoll;

import java.util.UUID;
import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

/**
 * Speichert die Url der QuestionPoll.
 */
public class QuestionPollLink implements ValidateAble {
    @Getter
    private final UUID questionLinkId;

    public QuestionPollLink() {
        this.questionLinkId = UUID.randomUUID();
    }

    /**
     * ...
     * @return...
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }
}
