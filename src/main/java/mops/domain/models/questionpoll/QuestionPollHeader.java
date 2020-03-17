package mops.domain.models.questionpoll;

import lombok.NonNull;
import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public class QuestionPollHeader implements ValidateAble {

    @NonNull
    private final String title;

    @NonNull
    private final String question;

    private final String description;

    // Vorläufige Werte.
    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_QUESTION_LENGTH = 40;
    private static final int MIN_QUESTION_LENGTH = 5;
    private static final int MAX_DESCRIPTION_LENGTH = 80;
    private static final int MIN_DESCRIPTION_LENGTH = 0;

    /**
     * Speichert den Titel, die Frage und die optionale Beschreibung für eine QuestionPoll.
     * @param title
     * @param question
     * @param description
     */
    public QuestionPollHeader(final String title, final String question, final String description) {
        this.title = title.trim();
        this.question = question.trim();
        if (description == null || description.trim().isBlank()) {
            this.description = "";
        } else {
            this.description = description.trim();
        }
    }

    /**
     * Validiert den Zustand des Headers.
     * @return Validation Objekt.
     */
    @Override
    public Validation validate() {
        Validation validator = Validation.noErrors();
        validateTitle(validator);
        validateQuestion(validator);
        validateDescription(validator);
        return validator;
    }

    private void validateTitle(Validation validator) {
        if (this.title == null) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_NULL));
            return;
        }
        if (this.title.isEmpty()) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        }
        if (this.title.length() > this.MAX_TITLE_LENGTH) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG));
        }
        if (this.title.length() < this.MIN_TITLE_LENGTH) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_SHORT));
        }
    }

    private void validateQuestion(Validation validator) {
        if (this.question == null) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_NULL));
            return;
        }
        if (this.question.isEmpty()) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_QUESTION_IS_EMPTY));
        }
        if (this.question.length() > this.MAX_QUESTION_LENGTH) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_LONG));
        }
        if (this.question.length() < this.MIN_QUESTION_LENGTH) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_SHORT));
        }
    }

    private void validateDescription(Validation validator) {
        if (this.description.length() > this.MAX_DESCRIPTION_LENGTH) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_DESCRIPTION_IS_TOO_LONG));
        }
        if (this.description.length() < this.MIN_DESCRIPTION_LENGTH) {
            validator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_DESCRIPTION_IS_TOO_SHORT));
        }
    }
}
