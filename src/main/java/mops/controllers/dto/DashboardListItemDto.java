package mops.controllers.dto;

import lombok.Data;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.pollstatus.PollStatus;

import java.time.LocalDateTime;

@Data
public class DashboardListItemDto {

    //Identifier ohne Kontextpfad (also z.B. „j5kl43lk5“)
    private String datePollIdentifier;
    private String title;
    private LocalDateTime endDate;
    private PollStatus status;
}
