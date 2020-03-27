package mops.infrastructure.controllers.dtos;

import java.time.LocalDateTime;
import lombok.Data;
import mops.domain.models.Timespan;

@Data
public class DatePollResultDto implements Comparable<DatePollResultDto> {
    private Timespan timespan;
    private int yesVotes;
    private int maybeVotes;
    private final LocalDateTime createdAt;

    public DatePollResultDto(Timespan timespan, int yesVotes, int maybeVotes) {
        this.timespan = timespan;
        this.yesVotes = yesVotes;
        this.maybeVotes = maybeVotes;
        this.createdAt = LocalDateTime.now();
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
     * Gibt den Wochentag zurück.
     * @return Wochentag als String.
     */

    public String getStartDay() {
        return timespan.printStartDateDay();
    }

    /**
     * Gibt das Datum zurück.
     * @return Datum als String.
     */
    public String getStartDate() {
        return timespan.printStartDate();
    }

    /**
     * Gibt den Zeitraum zurück.
     * @return Zeitraum als String.
     */
    public String getFormattedTimeSpan() {
        return timespan.printFormatted();
    }
}
