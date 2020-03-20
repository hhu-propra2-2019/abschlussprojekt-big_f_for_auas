package mops.domain.models.questionpoll;

import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

@Value
public class QuestionPollHeader implements ValidateAble {

    private final String title;

    private final String question;

    private final String description;

    // Vorläufige Werte.
    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MAX_QUESTION_LENGTH = 40;
    private static final int MAX_DESCRIPTION_LENGTH = 80;

    /**
     * Speichert den Titel, die Frage und die optionale Beschreibung für eine QuestionPoll.
     * @param title
     * @param question
     * @param description
     */
    public QuestionPollHeader(final String title, final String question, final String description) {
        this.title = DomainObjectCreationUtils.convertNullToEmptyAndTrim(title);
        this.question = DomainObjectCreationUtils.convertNullToEmptyAndTrim(question);
        this.description = DomainObjectCreationUtils.convertNullToEmptyAndTrim(description);
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
        if (this.title.isEmpty()) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        } else if (this.title.length() > MAX_TITLE_LENGTH) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG));
        }
        return newValidator;
    }

    private Validation validateQuestion(Validation validator) {
        Validation newValidator = validator;
        if (this.question.isEmpty()) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        } else if (this.question.length() > MAX_QUESTION_LENGTH) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_LONG));
        }
        return newValidator;
    }

    private Validation validateDescription(Validation validator) {
        Validation newValidator = validator;
        if (this.description.length() > MAX_DESCRIPTION_LENGTH) {
            newValidator = newValidator.appendValidation(
                    new Validation(FieldErrorNames.QUESTION_POLL_DESCRIPTION_IS_TOO_LONG));
        }
        return newValidator;
    }
}
