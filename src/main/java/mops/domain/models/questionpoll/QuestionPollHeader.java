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
        validator = validateTitle(validator);
        validator = validateQuestion(validator);
        validator = validateDescription(validator);
        return validator;
    }

    private Validation validateTitle(Validation validator) {
        Validation newValidator = validator;
        if (this.title == null) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_NULL));
        } else {
            if (this.title.isEmpty()) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
            } else if (this.title.length() < MIN_TITLE_LENGTH) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_SHORT));
            } else if (this.title.length() > MAX_TITLE_LENGTH) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG));
            }
        }
        return newValidator;
    }

    private Validation validateQuestion(Validation validator) {
        Validation newValidator = validator;
        if (this.question == null) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_NULL));
        } else {
            if (this.question.isEmpty()) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
            } else if (this.question.length() < MIN_QUESTION_LENGTH) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_SHORT));
            } else if (this.question.length() > MAX_QUESTION_LENGTH) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_LONG));
            }
        }
        return newValidator;
    }

    private Validation validateDescription(Validation validator) {
        Validation newValidator = validator;
        if (this.description != null && this.description.length() > MAX_DESCRIPTION_LENGTH) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_DESCRIPTION_IS_TOO_LONG));
        }
        return newValidator;
    }
}
