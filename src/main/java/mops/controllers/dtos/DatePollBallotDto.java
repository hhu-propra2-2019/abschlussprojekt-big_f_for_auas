package mops.controllers.dtos;

import lombok.Data;
import mops.domain.models.user.UserId;

import java.util.HashSet;
import java.util.Set;


@Data
public class DatePollBallotDto {

    private UserId user;
    private Set<DatePollEntryDto> entries = new HashSet<>();

    public DatePollBallotDto(UserId user) {
        this.user = user;
    }

    public void addEntries(DatePollEntryDto entry) {
        entries.add(entry);
    }
}
