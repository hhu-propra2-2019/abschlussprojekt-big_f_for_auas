package mops.infrastructure.adapters.webflow.datepoll.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public final class EntryDto implements Comparable<EntryDto>, Serializable {

    public static final long serialVersionUID = 575665475776735L;

    private String date;
    private String startTime;
    private String endTime;

    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public int compareTo(EntryDto entryDto) {
        final int difference = LocalDate.parse(this.date).atTime(LocalTime.parse(this.startTime))
                .compareTo(LocalDate.parse(entryDto.getDate()).atTime(LocalTime.parse(entryDto.getStartTime())));
        // Wenn das Startdatum und die Startzeit gleich sind, gibt die Endzeit den Ausschlag
        if (difference == 0) {
            return LocalTime.parse(this.endTime)
                    .compareTo(LocalTime.parse(entryDto.getEndTime()));
        }
        return difference;
    }
}
