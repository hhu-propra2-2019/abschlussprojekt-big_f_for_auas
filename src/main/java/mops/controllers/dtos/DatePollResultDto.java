package mops.controllers.dtos;

import lombok.Data;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollEntry;

@Data
public class DatePollResultDto implements Comparable<DatePollResultDto> {
    private Timespan suggestedPeriod;
    private int yesVotes;
    private int maybeVotes;

    public DatePollResultDto(DatePollEntry entry) {
        suggestedPeriod = entry.getSuggestedPeriod();
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
}
