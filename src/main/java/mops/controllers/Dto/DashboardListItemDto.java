package mops.controllers.Dto;

import lombok.Data;
import mops.domain.models.pollstatus.PollStatus;

import java.time.LocalDateTime;

@Data
public class DashboardListItemDto {

    private String title;
    private LocalDateTime endDate;
    private PollStatus status;
}
