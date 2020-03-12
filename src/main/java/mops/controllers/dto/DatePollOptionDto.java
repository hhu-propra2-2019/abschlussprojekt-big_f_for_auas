package mops.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DatePollOptionDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
