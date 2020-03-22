package mops.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;
import org.bouncycastle.util.Times;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class DatePollEntryDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Timespan timespan ;

    private boolean votedFor;
    private String title;


    public DatePollEntryDto(LocalDateTime start, LocalDateTime end) {
        this.startDate = start;
        this.endDate = end;
        timespan = new Timespan(startDate, endDate);
    }

    public DatePollEntryDto(Timespan timespan) {
        this.timespan = timespan;
    }

    public String formatString() {

        String startDateTime = startDate.format(DateTimeFormatter.ofPattern("HH:mm"));
        String startDateDate = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String endDateTime = startDate.format(DateTimeFormatter.ofPattern("HH:mm"));
        String endDateDate = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return startDateDate + " " + startDateTime +" Uhr " +" bis " + endDateDate + " " + endDateTime +" Uhr ";
    }
}
