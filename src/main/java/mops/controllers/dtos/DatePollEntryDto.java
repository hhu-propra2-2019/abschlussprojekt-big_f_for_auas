package mops.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class DatePollEntryDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Timespan timespan ;

    private boolean votedFor ;


    public DatePollEntryDto(LocalDateTime start, LocalDateTime end) {
        this.startDate = start;
        this.endDate = end;
        timespan = new Timespan(startDate, endDate);
    }

    public DatePollEntryDto(Timespan timespan) {
        this(timespan.getStartDate(), timespan.getEndDate());
        this.timespan = timespan;
    }

    public String getId() {
        return startDate.toString() + "@"+ endDate.toString();
    }

    public String formatString() {
        String start = startDate.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy HH:mm")) + " Uhr";
        String end = endDate.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy HH:mm")) + " Uhr";
        return start + " bis " + end;
    }
}
