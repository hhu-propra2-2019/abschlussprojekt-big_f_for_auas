package mops.domain.models.questionpoll;

import mops.domain.models.FieldErrorNames;
import mops.domain.models.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionPollHeaderTest {
    private Validation testValidator;

    @BeforeEach
    public void setValidator() {
        this.testValidator = Validation.noErrors();
    }

    @Test
    public void longTitle() {
        QuestionPollHeader header = new QuestionPollHeader("thisTitleIsObviouslyTooBig", "Question", "");
        testValidator.appendValidation(new Validation(FieldErrorNames.QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG));
        Validation validator = header.validate();
        //weitermachen
    }
}
