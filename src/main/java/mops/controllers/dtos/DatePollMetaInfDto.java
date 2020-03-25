package mops.controllers.dtos;

import lombok.Data;

import java.time.LocalDateTime;

import mops.domain.models.datepoll.DatePollMetaInf;

@Data
public class DatePollMetaInfDto {

    private String title;
    private String description;
    private String location;
    private LocalDateTime endDate;

    public DatePollMetaInfDto(DatePollMetaInf datePollMetaInf) {
        title = datePollMetaInf.getTitle();
        description = datePollMetaInf.getDescription().getDescriptionText();
        location = datePollMetaInf.getLocation().getLocation();
        endDate = datePollMetaInf.getTimespan().getEndDate();
    }
}
