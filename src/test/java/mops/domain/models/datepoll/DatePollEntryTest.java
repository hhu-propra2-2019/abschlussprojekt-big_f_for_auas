package mops.domain.models.datepoll;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashSet;
import mops.domain.models.Timespan;
import org.junit.jupiter.api.Test;

class DatePollEntryTest {

    @Test
    public void identicalEntriesCantBeTwiceInSet() {
        Timespan fakeTime = new Timespan(LocalDateTime.MIN, LocalDateTime.MAX);

        DatePollEntry entry1 = new DatePollEntry(fakeTime);
        DatePollEntry entry2 = new DatePollEntry(fakeTime);

        HashSet<DatePollEntry> set = new HashSet<>();
        set.add(entry1);
        set.add(entry2);

        assertThat(set.size()).isEqualTo(1);
    }

    @Test
    public void differentEntriesAreTwiceInSet() {
        Timespan fakeTime1 = new Timespan(LocalDateTime.MAX, LocalDateTime.MAX);
        Timespan fakeTime2 = new Timespan(LocalDateTime.MIN, LocalDateTime.MIN);

        DatePollEntry entry1 = new DatePollEntry(fakeTime1);
        DatePollEntry entry2 = new DatePollEntry(fakeTime2);

        HashSet<DatePollEntry> set = new HashSet<>();
        set.add(entry1);
        set.add(entry2);

        assertThat(set.size()).isEqualTo(2);
    }
}
