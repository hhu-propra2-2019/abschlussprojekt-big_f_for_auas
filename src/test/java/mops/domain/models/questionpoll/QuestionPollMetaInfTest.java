package mops.domain.models.questionpoll;

import mops.domain.models.FieldErrorNames;
import mops.domain.models.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionPollMetaInfTest { //NOPMD
    private Validation testValidator; //NOPMD

    //Entspricht Vorläufigen Werten
    private static final String VALID_TITLE = "niceTitle";
    private static final String LONG_TITLE = "This Title Is Too Big.";
    private static final String VALID_QUESTION = "Question";
    private static final String LONG_QUESTION = "This Question is longer than 40 characters.";
    private static final String VALID_DESCRIPTION = "Description";

    /**
     * Erstellt leeren validator der in jedem Test angepasst wird.
     */
    @BeforeEach
    public void setValidator() {
        this.testValidator = Validation.noErrors();
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})//NOPMD
    public void longTitle() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf(LONG_TITLE, VALID_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void emptyTitle() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf("", VALID_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void nullTitle() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf(null, "Question", "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void longQuestion() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf(VALID_TITLE,
                LONG_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_LONG));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void emptyQuestion() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf(VALID_TITLE, "", "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void nullQuestion() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf(VALID_TITLE, null, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void completeHeader() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf(VALID_TITLE, VALID_QUESTION, VALID_DESCRIPTION);
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void allEmpty() {
        final QuestionPollMetaInf header = new QuestionPollMetaInf("", "", "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }
    //TODO mock tests für PollDescription und Timespan.
}
