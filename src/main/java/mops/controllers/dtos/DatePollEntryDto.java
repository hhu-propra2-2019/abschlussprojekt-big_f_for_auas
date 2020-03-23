package mops.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DatePollEntryDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
