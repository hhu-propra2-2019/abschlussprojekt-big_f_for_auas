package mops.infrastructure.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollEntry;

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

    /**
     * Gibt den formatieren String zurück.
     * @return formatierter String.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    public String printName() {
        return timespan.toString();
    }
}
