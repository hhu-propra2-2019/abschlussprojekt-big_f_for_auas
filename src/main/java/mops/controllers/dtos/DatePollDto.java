package mops.controllers.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatePollDto {

    private String title;
    private String description;
    private String location;
    private LocalDateTime endDate;
    private String pollStatus;
}
