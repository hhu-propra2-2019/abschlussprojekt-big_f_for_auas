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
}