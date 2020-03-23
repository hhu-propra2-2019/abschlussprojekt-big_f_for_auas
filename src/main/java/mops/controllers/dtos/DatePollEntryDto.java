package mops.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@SuppressWarnings({"PMD.LawOfDemeter"})
public class DatePollEntryDto implements Comparable<DatePollEntryDto> {

    private Timespan timespan;

    public DatePollEntryDto(LocalDateTime start, LocalDateTime end) {
        timespan = new Timespan(start, end);
    }

    public DatePollEntryDto(Timespan timespan) {
        this.timespan = timespan;
    }


    /**
     *
     * @return String Id eines Timespan Objektes um es beim Post Mapping wieder zu erstellen.
     */
    public String getId() {
        return timespan.getStartDate().toString() + "@" + timespan.getEndDate().toString();
    }

    /**
     * @return formatierte String Repräsentation des Timespan Objektes.
     */
    public String formatString() {
        final String start = timespan.getStartDate()
                .format(DateTimeFormatter
                        .ofPattern("EEEE, d MMM yyyy HH:mm"))
                + " Uhr";
        final String end = timespan.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr";
        return start + " bis " + end;
    }

    /**
     *
     * @return formatierte String Repräsentation des Timespan startDate Objektes (= Anfangs LocalDateTime).
     */
    public String formatStringDate() {
        return timespan.getStartDate().format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"));
    }


    /**
     *
     * @param time
     * @return Stunden und minuten von einer uebergebenen LocalDateTime
     */
    public String formatStringTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /** vergleict zwei DatePollEntryDtos, indem es die beiden Anfangs LocalDateTimes vergleicht.
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(DatePollEntryDto other) {
        return timespan.getStartDate().compareTo(other.timespan.getStartDate());
    }
}
