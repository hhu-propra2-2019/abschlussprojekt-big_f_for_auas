package mops.domain.models.questionpoll;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;
import org.junit.jupiter.api.Test;

public class QuestionPollAccessibilityTest { //NOPMD

    @Test
    @SuppressWarnings("PMD.LawOfDemeter")
    public void closedPollNeedsParticipantsSizeAboveOne() {
        final Set<UserId> mockSet = (Set<UserId>) mock(Set.class);
        when(mockSet.size()).thenReturn(1);

        final QuestionPollAccessibility accessTest = new QuestionPollAccessibility(true, mockSet);
        final Validation validator = accessTest.validate();

        assertThat(validator.hasNoErrors()).isFalse();
    }
}
