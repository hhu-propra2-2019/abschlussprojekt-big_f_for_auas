package mops.adapters.datepolladapter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public final class OptionDto implements Comparable<OptionDto>, Serializable {

    public static final long serialVersionUID = 575665475776735L;

    private String date;
    private String startTime;
    private String endTime;

    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public int compareTo(OptionDto optionDto) {
        final int difference = LocalDate.parse(this.date).atTime(LocalTime.parse(this.startTime))
                .compareTo(LocalDate.parse(optionDto.getDate()).atTime(LocalTime.parse(optionDto.getStartTime())));
        // Wenn das Startdatum und die Startzeit gleich sind, gibt die Endzeit den Ausschlag
        if (difference == 0) {
            return LocalTime.parse(this.endTime)
                    .compareTo(LocalTime.parse(optionDto.getEndTime()));
        }
        return difference;
    }
}
