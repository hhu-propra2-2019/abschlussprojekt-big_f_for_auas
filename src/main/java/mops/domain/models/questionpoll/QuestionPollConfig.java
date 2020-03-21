package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

/**
 * Speichert Einstellungen ob die QuestionPoll anonym ist (usingAlias = true) und ob es sich um eine
 *  Multi-Choice Umfrage geben.
 */
@Value
@With
@AllArgsConstructor
public class QuestionPollConfig implements ValidateAble {

    private boolean singleChoice;
    private boolean anonymous;
    private boolean open;

    public QuestionPollConfig() {
        this.anonymous = false;
        this.singleChoice = false;
        this.open = true;
    }

    /**
     * Validiert die Config.
     * @return Validation Objekt.
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }
}
