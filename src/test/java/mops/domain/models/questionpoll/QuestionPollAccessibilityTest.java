package mops.domain.models.questionpoll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;
import org.junit.jupiter.api.Test;

public class QuestionPollAccessibilityTest {

    @Test
    public void closedPollNeedsParticipantsSizeAboveOne() {
        final Set<UserId> mockSet = (Set<UserId>) mock(Set.class);
        when(mockSet.size()).thenReturn(1);

        final QuestionPollAccessibility accessTest = new QuestionPollAccessibility(true, mockSet);
        final Validation validator = accessTest.validate();

        assertFalse(validator.hasNoErrors());
    }
}
