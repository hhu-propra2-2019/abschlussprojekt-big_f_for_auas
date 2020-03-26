package mops.infrastructure.controllers.dtos;

import lombok.Data;
import java.util.Set;

@Data
public class DatePollUserEntryOverview {

    private Set<DatePollEntryDto> allEntries;
    private Set<DatePollEntryDto> votedYes;
    private Set<DatePollEntryDto> votedMaybe;

    //TODO: können die 3 gelöscht werden?
    private String title;
    private String description;
    private String location;

    public boolean isVotedMaybeNull() {
        return votedMaybe == null;
    }
}
