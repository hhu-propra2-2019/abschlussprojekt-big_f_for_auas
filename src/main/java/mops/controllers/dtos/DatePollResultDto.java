package mops.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollEntry;

import java.time.format.DateTimeFormatter;

@Data

//ZU TESTZWECKEN//
@NoArgsConstructor
//////////////////

public class DatePollResultDto implements Comparable<DatePollResultDto> {
    private Timespan timespan;
    private int yesVotes;
    private int maybeVotes;

    public DatePollResultDto(DatePollEntry entry) {
        timespan = entry.getSuggestedPeriod();
        yesVotes = entry.getYesVotes();
        maybeVotes = entry.getMaybeVotes();
    }

    /**
     * Damit die Results abwärts sortiert angezeigt werden.
     */
    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    public int compareTo(DatePollResultDto other) {
        return -1 * Integer.compare(this.yesVotes, other.yesVotes);
    }

    public String printDay() {
        return timespan.getStartDate().format(DateTimeFormatter.ofPattern("EEEE,"));
    }

    public String printDate() {
        return timespan.getStartDate().format(DateTimeFormatter.ofPattern("d MMM yyyy"));
    }

    public String printTimespan() {
        final String start = timespan.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr";
        final String end = timespan.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr";
        return start + " bis " + end;
    }
}
