package mops.domain.models.datepoll;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatePollCastBallotTest {

    private DatePoll createDatePollWithOneEntry() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("Titel", "Testing", "Uni", timespan);
        final UserId creator = new UserId("1");
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        DatePollEntry entry1 = new DatePollEntry(new Timespan(LocalDateTime.now().plusDays(0), LocalDateTime.now().plusDays(10)));
        Set<DatePollEntry> entries = new HashSet<>();
        entries.add(entry1);

        final Set<UserId> participants = new HashSet<>();

        final Group group = new Group(new GroupId("1"), "Testgruppe", Group.GroupVisibility.PRIVATE, participants);
        DatePoll datePoll = new DatePollBuilder()
            .datePollMetaInf(datePollMetaInf)
            .creator(creator)
            .datePollConfig(datePollConfig)
            .datePollEntries(entries)
            .participatingGroups(Set.of(group.getId()))
            .datePollLink(datePollLink)
            .build();
        return datePoll;
    }

    private DatePoll createDatePollWithTwoEntries() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("Titel", "Testing", "Uni", timespan);
        final UserId creator = new UserId("1");
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        DatePollEntry entry1 = new DatePollEntry(new Timespan(LocalDateTime.now().plusDays(0), LocalDateTime.now().plusDays(10)));
        DatePollEntry entry2 = new DatePollEntry(new Timespan(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(10)));
        Set<DatePollEntry> entries = new HashSet<>();
        entries.add(entry1);
        entries.add(entry2);

        final Set<UserId> participants = new HashSet<>();

        final Group group = new Group(new GroupId("1"), "Testgruppe", Group.GroupVisibility.PRIVATE, participants);
        DatePoll datePoll = new DatePollBuilder()
            .datePollMetaInf(datePollMetaInf)
            .creator(creator)
            .datePollConfig(datePollConfig)
            .datePollEntries(entries)
            .participatingGroups(Set.of(group.getId()))
            .datePollLink(datePollLink)
            .build();
        return datePoll;
    }

    @Test
    public void FirstYesCastUpdatesEntry() {
        DatePoll datePoll = createDatePollWithOneEntry();
        Set<DatePollEntry> entries = datePoll.getEntries();
        int yesVotesBeforeCast = datePoll.getEntries().iterator().next().getYesVotes();
        datePoll.castBallot(new UserId("1"), entries, Collections.emptySet());
        int yesVotesAfterCast = datePoll.getEntries().iterator().next().getYesVotes();
        int maybeVotesAfterCast = datePoll.getEntries().iterator().next().getMaybeVotes();
        assertThat(yesVotesAfterCast).withFailMessage("YesVotes did not change").isEqualTo(1);
        assertThat(maybeVotesAfterCast).withFailMessage("Maybevotes Changed").isEqualTo(0);
    }

    @Test
    public void secondCastUpdatesEntriesFromOldCast() {
        DatePoll datePoll = createDatePollWithTwoEntries();
        Set<DatePollEntry> entries = datePoll.getEntries();
        DatePollEntry entry1 = null;
        DatePollEntry entry2 = null;
        for (final DatePollEntry entry : entries) {
            if (entry1 == null) {
                entry1 = entry;
            } else {
                entry2 = entry;
            }
        }
        HashSet<DatePollEntry> vote1 = new HashSet<>();
        HashSet<DatePollEntry> vote2 = new HashSet<>();
        vote1.add(entry1);
        vote2.add(entry2);


        datePoll.castBallot(new UserId("1"), vote1, Collections.emptySet());
        datePoll.castBallot(new UserId("1"), vote2, Collections.emptySet());
        assertThat(entry1.getYesVotes()).withFailMessage("old Vote did not get decremented").isEqualTo(0);
        assertThat(entry2.getYesVotes()).withFailMessage("new Vote did not get incremented").isEqualTo(1);

    }

}
