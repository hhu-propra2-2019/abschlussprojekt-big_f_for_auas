package mops.controllers.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class DatePollUserEntryOverview {

    private Set<DatePollEntryDto> allEntries;
    private Set<DatePollEntryDto> votedYes;
    private Set<DatePollEntryDto> votedMaybe;
}
