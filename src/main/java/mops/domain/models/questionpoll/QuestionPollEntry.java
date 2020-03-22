package mops.domain.models.questionpoll;

import lombok.Getter;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;


/**
 * Speichert eine Option über die in einem QuestionPoll abgestimmt werden kann und
 *  verfolgt wie oft für diese Option abgestimmt wurde.
 */
public class QuestionPollEntry implements ValidateAble {
    @Getter
    private final String title;

    //Vorläufige Werte
    private static final int MAX_LENGTH_TITLE = 40;
    private static final int MIN_LENGTH_TITLE = 10;

    public QuestionPollEntry(final String title) {
        this.title = title == null ? "" : title.trim();
    }

    /** Validate the title ( possibles Answer from Question) if it empty or only whitespaces etc.
     * Validate count if it is negative.
     * @return
     */
    @Override
    public Validation validate() {
        Validation validator = Validation.noErrors();
        if (this.title.isEmpty()) {
            validator = validator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_EMPTY));
        }
        if (this.title.isBlank() && !this.title.isEmpty()) {
            validator = validator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_ONLY_WHITESPACE));
        }
        if (this.title.length() > MAX_LENGTH_TITLE) {
            validator = validator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_TOO_LONG));
        }
        if (this.title.length() < MIN_LENGTH_TITLE) {
            validator = validator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_TOO_SHORT));
        }
        return validator;
    }
}
