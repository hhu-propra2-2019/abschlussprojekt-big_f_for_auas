package mops.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.Timespan;

@Data
@AllArgsConstructor
public class DatePollEntryDto {

    private Timespan suggestedPeriod;
    private final int yesVotes;
    private final int noVotes;
}
