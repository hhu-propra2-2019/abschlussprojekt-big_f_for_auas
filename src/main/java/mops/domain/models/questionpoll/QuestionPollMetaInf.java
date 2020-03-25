package mops.domain.models.questionpoll;

import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.PollDescription;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

@Value
public class QuestionPollMetaInf implements ValidateAble {

    private String title;
    private String question;
    private PollDescription description;
    private Timespan timespan;

    // Vorläufige Werte.
    private static final int MAX_TITLE_LENGTH = 20;
    private static final int MAX_QUESTION_LENGTH = 40;

    /**
     * Speichert den Titel, die Frage und die optionale Beschreibung für eine QuestionPoll.
     * @param title
     * @param question
     * @param description
     */
    public QuestionPollMetaInf(String title, String question, String description) {
        this.title = DomainObjectCreationUtils.convertNullToEmptyAndTrim(title);
        this.question = DomainObjectCreationUtils.convertNullToEmptyAndTrim(question);
        this.description = new PollDescription(description);
        this.timespan = new Timespan(null, null);
    }

    public QuestionPollMetaInf(String title, String question, String description, Timespan timespan) {
        this.title = DomainObjectCreationUtils.convertNullToEmptyAndTrim(title);
        this.question = DomainObjectCreationUtils.convertNullToEmptyAndTrim(question);
        this.description = new PollDescription(description);
        this.timespan = timespan;
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
            } else if (this.question.length() > MAX_QUESTION_LENGTH) {
                newValidator = newValidator.appendValidation(
                        new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_LONG));
            }
        }
        return newValidator;
    }
}
