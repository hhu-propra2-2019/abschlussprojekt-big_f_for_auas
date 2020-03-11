package mops.controllers.Dto;

import lombok.Data;
import mops.domain.models.pollstatus.PollStatus;

@Data
public class DatePollDto {

    private String title;
    private String description;
    private String location;
    private String endDate;
    private PollStatus pollStatus;
}
