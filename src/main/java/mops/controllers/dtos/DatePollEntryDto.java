package mops.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class DatePollEntryDto implements Comparable<DatePollEntryDto>{

    private Timespan timespan;

    public DatePollEntryDto(LocalDateTime start, LocalDateTime end) {
        timespan = new Timespan(start, end);
    }

    public DatePollEntryDto(Timespan timespan) {
        this.timespan = timespan;
    }


    public String getId() {
        return timespan.getStartDate().toString() + "@" + timespan.getEndDate().toString();
    }

    public String formatString() {
        String start = timespan.getStartDate().format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy HH:mm")) + " Uhr";
        String end = timespan.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")) + " Uhr";
        return start + " bis " + end;
    }

    public String formatStringDate() {
        return timespan.getStartDate().format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"));
    }

    public String formatStringTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public int compareTo(DatePollEntryDto other) {
        return timespan.getStartDate().compareTo(other.timespan.getStartDate());
    }
}
