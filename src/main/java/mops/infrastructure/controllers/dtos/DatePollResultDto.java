package mops.infrastructure.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;

import java.time.format.DateTimeFormatter;

@Data

//ZU TESTZWECKEN//
@NoArgsConstructor
//////////////////
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
     * Gibt den Wochentag zurück.
     * @return Wochentag als String.
     */
    public String printDay() {
        return timespan.getStartDate().format(DateTimeFormatter.ofPattern("EEEE,"));
    }

    /**
     * Gibt das Datum zurück.
     * @return Datum als String.
     */
    public String printDate() {
        return timespan.getStartDate().format(DateTimeFormatter.ofPattern("d MMM yyyy"));
    }

    /**
     * Gibt den Zeitraum zurück.
     * @return Zeitraum als String.
     */
    public String printTimespan() {
        final String start = timespan.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr";
        final String end = timespan.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr";
        return start + " bis " + end;
    }

}