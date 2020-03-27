package mops.infrastructure.controllers.dtos;

import java.util.List;
import lombok.Data;

@Data
public class DatePollUserEntryOverview {

    private List<DatePollEntryDto> allEntries;
    private List<DatePollEntryDto> votedYes;
    private List<DatePollEntryDto> votedMaybe;

    //TODO: können die 3 gelöscht werden?
    private String title;
    private String description;
    private String location;

    /**
     * Wenn der User keinen Eintrag mit maybe votet, dann ist das set null.
     * @return boolean
     */
    public boolean isVotedMaybeNull() {
        return votedMaybe == null;
    }
}
