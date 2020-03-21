package mops.controllers.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class DatePollUserEntryOverview {
    private Set<DatePollEntryDto> yes;
    private Set<DatePollEntryDto> no;
}
