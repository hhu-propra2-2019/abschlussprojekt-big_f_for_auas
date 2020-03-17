package mops.domain.models.questionpoll;

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
        testValidator.appendValidation();
        QuestionPollHeader header = new QuestionPollHeader("", "", "");
        Validation validator = header.validate();
        validator.getErrorMessages();
    }
}
