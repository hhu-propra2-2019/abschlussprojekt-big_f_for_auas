package mops.domain.models.datepoll;


import mops.domain.models.Timespan;
import mops.domain.models.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentDefaultAccessModifier", "PMD.DefaultPackage",
        "PMD.BeanMembersShouldSerialize", "PMD.AtLeastOneConstructor"})
        /* fluent assertj api stellt keine Verletzung des LoD dar, selbst wenn ist das bei den tests nicht schlimm.
         * Alle test methods sind package private.
         * Felder transient in tests zu machen erscheint wenig sinvoll.
         */
class DatePollEntryValidationTest {

    private Timespan timespanMock;
    private DatePollEntry validDatePollEntry;

    @BeforeEach
    void setUp() {
        timespanMock = mock(Timespan.class);
        when(timespanMock.validate()).thenReturn(Validation.noErrors());
        validDatePollEntry = new DatePollEntry(timespanMock);
    }

    @Test
    void timespanValidationIsCalled() {
        validDatePollEntry.validate();

        verify(timespanMock).validate();
    }
}
