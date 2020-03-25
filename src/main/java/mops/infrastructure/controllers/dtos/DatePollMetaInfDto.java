package mops.infrastructure.controllers.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatePollMetaInfDto {

    private String title;
    private String description;
    private String location;
    private LocalDateTime endDate;

    public DatePollMetaInfDto(String title, String description, String location,
        LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.endDate = endDate;
    }
}
