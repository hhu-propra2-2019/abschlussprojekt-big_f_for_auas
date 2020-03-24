package mops.domain.models.datepoll;

import mops.domain.models.FieldErrorNames;
import mops.domain.models.PollDescription;
import mops.domain.models.Timespan;
import mops.domain.models.Validation;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentDefaultAccessModifier", "PMD.DefaultPackage",
        "PMD.BeanMembersShouldSerialize", "PMD.AtLeastOneConstructor"})
        /* fluent assertj api stellt keine Verletzung des LoD dar, selbst wenn ist das bei den tests nicht schlimm.
         * Alle test methods sind package private.
         * Felder transient in tests zu machen erscheint wenig sinvoll.
         */
class DatePollMetaInfTest {


    private static final String TITLE = "Jens Clojure Meting";
    private static final String DESCRIPTION = "Wann soll das nächste Clojure Meeting stadtfinden?";
    private static final String LOCATION = "HHU";
    private static final int DAYS_TO_ADD_FROM_NOW_ON = 1;

    private final Timespan lifeCycle = new Timespan(
            LocalDateTime.now(),
            LocalDateTime.now().plus(DAYS_TO_ADD_FROM_NOW_ON, ChronoUnit.DAYS));
    private DatePollMetaInf validMetaInf;
    private DatePollLocation locationMock;
    private PollDescription descriptionMock;
    private Timespan timespanMock;

    @BeforeEach
    void setUp() {
        locationMock = mock(DatePollLocation.class);
        descriptionMock = mock(PollDescription.class);
        timespanMock = mock(Timespan.class);
        when(locationMock.validate()).thenReturn(Validation.noErrors());
        when(descriptionMock.validate()).thenReturn(Validation.noErrors());
        when(timespanMock.validate()).thenReturn(Validation.noErrors());
        validMetaInf = new DatePollMetaInf(TITLE, descriptionMock, locationMock, timespanMock);
    }

    @Test
    void validateAllMetaInfFieldsAreSetCorrectly() {
        final DatePollMetaInf correctMetaInf = new DatePollMetaInf(TITLE, DESCRIPTION, LOCATION, lifeCycle);
        final EnumSet<FieldErrorNames> noErrors = EnumSet.noneOf(FieldErrorNames.class);

        final Validation validation = correctMetaInf.validate();

        assertThat(validation)
                .extracting(Validation::getErrorMessages)
                .isEqualTo(noErrors);
    }

    @Test
    void validateTitleDescriptionLocationSetCorrectlyButTimespanIsNotSet() {
        final DatePollMetaInf correctMetaInf = new DatePollMetaInf(TITLE, DESCRIPTION, LOCATION);
        final EnumSet<FieldErrorNames> timespanNotSet = EnumSet.of(FieldErrorNames.TIMESPAN_START_NULL,
                FieldErrorNames.TIMESPAN_END_NULL);

        final Validation underTest = correctMetaInf.validate();

        assertThat(underTest)
                .extracting(Validation::getErrorMessages)
                .isEqualTo(timespanNotSet);
    }

    @Test
    void validateTitleIsNotSet() {
        final DatePollMetaInf wrongTitleMetaInf = new DatePollMetaInf("", DESCRIPTION, LOCATION, lifeCycle);
        final EnumSet<FieldErrorNames> titleNotSet = EnumSet.of(FieldErrorNames.DATE_POLL_TITLE_EMPTY);

        final Validation underTest = wrongTitleMetaInf.validate();

        assertThat(underTest)
                .extracting(Validation::getErrorMessages)
                .isEqualTo(titleNotSet);
    }

    @Test
    void validateTitleIsToLong() {
        final String randomStringThatIsToLong = RandomStringUtils
                .randomAlphabetic(DatePollMetaInf.MAX_TITLE_LENGTH + 1);
        final DatePollMetaInf wrongTitleMetaInf = new DatePollMetaInf(
                randomStringThatIsToLong, DESCRIPTION, LOCATION, lifeCycle);
        final EnumSet<FieldErrorNames> titleTooLong = EnumSet.of(FieldErrorNames.DATE_POLL_TITLE_TOO_LONG);

        final Validation underTest = wrongTitleMetaInf.validate();

        assertThat(underTest)
                .extracting(Validation::getErrorMessages)
                .isEqualTo(titleTooLong);
    }

    @Test
    void datePollLocationValidationIsCalled() {
        validMetaInf.validate();

        verify(locationMock).validate();
    }

    @Test
    void datePollDescriptionValidationIsCalled() {
        validMetaInf.validate();

        verify(descriptionMock).validate();
    }

    @Test
    void timespanValidationIsCalled() {
        validMetaInf.validate();

        verify(timespanMock).validate();
    }

}
