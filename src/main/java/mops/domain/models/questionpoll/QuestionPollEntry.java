package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;


/**
 * Speichert eine Option über die in einem QuestionPoll abgestimmt werden kann und
 *  verfolgt wie oft für diese Option abgestimmt wurde.
 */

@AllArgsConstructor
public class QuestionPollEntry implements ValidateAble {
    private final String title;
    private long count;

    //Vorläufige Werte
    private static final int MAX_LENGTH_TITLE = 40;

    public QuestionPollEntry(final String title) {
        this.title = DomainObjectCreationUtils.convertNullToEmptyAndTrim(title);
        this.count = 0;
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
        if (this.title.length() > MAX_LENGTH_TITLE) {
            validator = validator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_TITLE_IS_TOO_LONG));
        }
        if (this.count < 0) {
            validator = validator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_ENTRY_COUNT_IS_NEGATIVE));
        }
        return validator;
    }
}
