package mops.domain.models.datepoll;

import mops.domain.models.FieldErrorNames;
import mops.domain.models.PollLink;
import mops.domain.models.Validation;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static mops.domain.models.datepoll.DatePollBuilder.COULD_NOT_CREATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.CommentDefaultAccessModifier", "PMD.DefaultPackage",
        "PMD.BeanMembersShouldSerialize", "PMD.AtLeastOneConstructor", "PMD.TooManyStaticImports"})
        /* fluent assertj api stellt keine Verletzung des LoD dar, selbst wenn ist das bei den tests nicht schlimm.
         * Alle test methods können package private sein.
         * Felder transient in tests zu machen erscheint wenig sinvoll.
         */
class DatePollBuilderTest {


    private DatePollMetaInf metaInfTarget;
    private User pollCreatorTarget;
    private DatePollConfig configTarget;
    private DatePollEntry mockEntry;
    private GroupId mockGroupId;
    private PollLink linkTarget;

    @BeforeEach
    void init() {
        metaInfTarget = mock(DatePollMetaInf.class);
        pollCreatorTarget = mock(User.class);
        configTarget = mock(DatePollConfig.class);
        linkTarget = mock(PollLink.class);
        mockEntry = mock(DatePollEntry.class);
        mockGroupId = mock(GroupId.class);
    }

    @Test
    void allFieldsSetCorrectlyBuildSuccessful() {
        when(metaInfTarget.validate()).thenReturn(Validation.noErrors());
        when(pollCreatorTarget.validate()).thenReturn(Validation.noErrors());
        when(configTarget.validate()).thenReturn(Validation.noErrors());
        when(mockEntry.validate()).thenReturn(Validation.noErrors());
        when(mockGroupId.validate()).thenReturn(Validation.noErrors());
        when(linkTarget.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.creator(pollCreatorTarget);
        underTest.datePollConfig(configTarget);
        underTest.datePollEntries(Set.of(mockEntry));
        underTest.datePollLink(linkTarget);
        underTest.participatingGroups(Set.of(mockGroupId));
        underTest.datePollMetaInf(metaInfTarget);

        assertThat(underTest.build()).isNotNull();
    }

    @Test
    void notAllFieldsSetBuildFailed() {
        when(metaInfTarget.validate()).thenReturn(Validation.noErrors());
        when(pollCreatorTarget.validate()).thenReturn(Validation.noErrors());
        when(configTarget.validate()).thenReturn(Validation.noErrors());
        when(mockEntry.validate()).thenReturn(Validation.noErrors());
        when(mockGroupId.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.creator(pollCreatorTarget);
        underTest.datePollConfig(configTarget);
        underTest.datePollEntries(Set.of(mockEntry));
        underTest.participatingGroups(Set.of(mockGroupId));
        underTest.datePollMetaInf(metaInfTarget);

        assertThatThrownBy(underTest::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(COULD_NOT_CREATE);
    }

    @Test
    void metaInfNotSetCorrectlyBuildFailed() {
        when(metaInfTarget.validate()).thenReturn(new Validation(FieldErrorNames.DATE_POLL_DESCRIPTION_TOO_LONG));
        when(pollCreatorTarget.validate()).thenReturn(Validation.noErrors());
        when(configTarget.validate()).thenReturn(Validation.noErrors());
        when(mockEntry.validate()).thenReturn(Validation.noErrors());
        when(mockGroupId.validate()).thenReturn(Validation.noErrors());
        when(linkTarget.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.creator(pollCreatorTarget);
        underTest.datePollConfig(configTarget);
        underTest.datePollEntries(Set.of(mockEntry));
        underTest.datePollLink(linkTarget);
        underTest.participatingGroups(Set.of(mockGroupId));
        underTest.datePollMetaInf(metaInfTarget);

        assertThatThrownBy(underTest::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(COULD_NOT_CREATE);
    }


    @Test
    void metaInfValidationIsCalled() {
        when(metaInfTarget.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.datePollMetaInf(metaInfTarget);

        verify(metaInfTarget).validate();
    }

    @Test
    void userIdValidationIsCalled() {
        when(pollCreatorTarget.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.creator(pollCreatorTarget);

        verify(pollCreatorTarget).validate();
    }

    @Test
    void configValidationIsCalled() {
        when(configTarget.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.datePollConfig(configTarget);

        verify(configTarget).validate();
    }

    @Test
    void linkValidationIsCalled() {
        when(linkTarget.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.datePollLink(linkTarget);

        verify(linkTarget).validate();
    }

    @Test
    void entriesValidationIsCalled() {
        when(mockEntry.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.datePollEntries(Set.of(mockEntry));

        verify(mockEntry).validate();
    }

    @Test
    void groupIdValidationIsCalled() {
        when(mockGroupId.validate()).thenReturn(Validation.noErrors());
        final DatePollBuilder underTest = new DatePollBuilder();

        underTest.participatingGroups(Set.of(mockGroupId));

        verify(mockGroupId).validate();
    }

}
