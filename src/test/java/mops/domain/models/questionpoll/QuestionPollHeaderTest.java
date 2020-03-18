package mops.domain.models.questionpoll;

import mops.domain.models.FieldErrorNames;
import mops.domain.models.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionPollHeaderTest { //NOPMD
    private Validation testValidator; //NOPMD

    //Entspricht Vorläufigen Werten
    private static final String VALID_TITLE = "niceTitle";
    private static final String SHORT_TITLE = "sh";
    private static final String LONG_TITLE = "This Title Is Too Big.";
    private static final String VALID_QUESTION = "Question";
    private static final String SHORT_QUESTION = "sh";
    private static final String LONG_QUESTION = "This Question is longer than 40 characters.";
    private static final String VALID_DESCRIPTION = "Description";
    private static final String LONG_DESCRIPTION =
            "This Description is longer than 80 Characters. This Description is longer than 80 Characters.";

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
        final QuestionPollHeader header = new QuestionPollHeader(LONG_TITLE, VALID_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void shortTitle() {
        final QuestionPollHeader header = new QuestionPollHeader(SHORT_TITLE, VALID_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_SHORT));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void emptyTitle() {
        final QuestionPollHeader header = new QuestionPollHeader("", VALID_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void nullTitle() {
        final QuestionPollHeader header = new QuestionPollHeader(null, "Question", "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void longQuestion() {
        final QuestionPollHeader header = new QuestionPollHeader(VALID_TITLE,
                LONG_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_LONG));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void shortQuestion() {
        final QuestionPollHeader header = new QuestionPollHeader(VALID_TITLE, SHORT_QUESTION, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_TOO_SHORT));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void emptyQuestion() {
        final QuestionPollHeader header = new QuestionPollHeader(VALID_TITLE, "", "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void nullQuestion() {
        final QuestionPollHeader header = new QuestionPollHeader(VALID_TITLE, null, "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void longDescription() {
        final QuestionPollHeader header = new QuestionPollHeader(VALID_TITLE, VALID_QUESTION, LONG_DESCRIPTION);
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_DESCRIPTION_IS_TOO_LONG));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void completeHeader() {
        final QuestionPollHeader header = new QuestionPollHeader(VALID_TITLE, VALID_QUESTION, VALID_DESCRIPTION);
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void allEmpty() {
        final QuestionPollHeader header = new QuestionPollHeader("", "", "");
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_EMPTY));
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.QUESTION_POLL_QUESTION_IS_EMPTY));
        final Validation validator = header.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }
}
