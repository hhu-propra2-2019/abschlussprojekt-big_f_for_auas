package mops.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.user.UserId;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Data
public class DatePollBallotDto {

    private UserId user;
    private Set<DatePollEntryDto> selectedEntriesYes = new HashSet<>();

    public DatePollBallotDto(UserId user) {
        this.user = user;
    }

    /** Adden und so.
     *
     * @param entry
     */
    public void addEntries(DatePollEntryDto entry) {
        selectedEntriesYes.add(entry);
    }
}
