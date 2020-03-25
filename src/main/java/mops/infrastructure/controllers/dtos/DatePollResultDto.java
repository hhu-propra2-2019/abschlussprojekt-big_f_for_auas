package mops.infrastructure.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.Timespan;

@Data
@AllArgsConstructor
public class DatePollResultDto implements Comparable<DatePollResultDto> {
    private Timespan timespan;
    private int yesVotes;
    private int maybeVotes;

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
