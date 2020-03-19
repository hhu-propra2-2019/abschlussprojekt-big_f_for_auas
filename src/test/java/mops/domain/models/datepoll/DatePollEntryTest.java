package mops.domain.models.datepoll;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashSet;
import mops.domain.models.Timespan;
import org.junit.jupiter.api.Test;

class DatePollEntryTest { //NOPMD

    @Test
    @SuppressWarnings("PMD.LawOfDemeter")
    public void identicalEntriesCantBeTwiceInSet() {
        final Timespan fakeTime = new Timespan(LocalDateTime.MIN, LocalDateTime.MAX);

        final DatePollEntry entry1 = new DatePollEntry(fakeTime);
        final DatePollEntry entry2 = new DatePollEntry(fakeTime);

        final HashSet<DatePollEntry> set = new HashSet<>();
        set.add(entry1);
        set.add(entry2);

        assertThat(set.size()).isEqualTo(1);
    }

    @Test
    @SuppressWarnings("PMD.LawOfDemeter")
    public void differentEntriesAreTwiceInSet() {
        final Timespan fakeTime1 = new Timespan(LocalDateTime.MAX, LocalDateTime.MAX);
        final Timespan fakeTime2 = new Timespan(LocalDateTime.MIN, LocalDateTime.MIN);

        final DatePollEntry entry1 = new DatePollEntry(fakeTime1);
        final DatePollEntry entry2 = new DatePollEntry(fakeTime2);

        final HashSet<DatePollEntry> set = new HashSet<>();
        set.add(entry1);
        set.add(entry2);

        assertThat(set.size()).isEqualTo(2);
    }
}
