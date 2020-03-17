package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

/**
 * Speichert eine Option über die in einem QuestionPoll abgestimmt werden kann und
 *  verfolgt wie oft für diese Option abgestimmt wurde.
 */

@AllArgsConstructor
public class QuestionPollEntry implements ValidateAble {
    private final String title;
    private long count;

    //Vorläufige Werte
    private final int MAX_LENGTH_TITLE = 40;
    private final int MIN_LENGTH_TITLE = 10;

    public QuestionPollEntry(final String title) {
        this.title = title == null ? "" : title.trim();
        this.count = 0;
    }

    @Override
    public Validation validate() {
        Validation validator = Validation.noErrors();
        if (this.title.isEmpty()) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_EMPTY));
        }
        if (this.title.isBlank() && !this.title.isEmpty()) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_ONLY_WHITESPACE));
        }
        if (this.title.length() > MAX_LENGTH_TITLE) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_TOO_LONG));
        }
        if (this.title.length() < MIN_LENGTH_TITLE) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_TOO_SHORT));
        }
        if (this.count < 0) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_COUNT_IS_NEGATIVE));
        }
        return validator;
    }
}
