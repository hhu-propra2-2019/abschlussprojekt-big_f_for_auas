package mops.controllers.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DashboardListItemDto {

    //Identifier ohne Kontextpfad (also z.B. „j5kl43lk5“)
    private String datePollIdentifier;
    private String title;
    private LocalDateTime endDate;
    private String status;
}
