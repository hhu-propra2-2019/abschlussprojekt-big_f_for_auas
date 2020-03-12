package mops.controllers.dto;

import lombok.Data;
import mops.domain.models.pollstatus.PollStatus;

import java.time.LocalDateTime;

@Data
public class DatePollDto {

    private String title;
    private String description;
    private String location;
    private LocalDateTime endDate;
    private PollStatus pollStatus;
}
