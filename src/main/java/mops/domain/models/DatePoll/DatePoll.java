package mops.domain.models.DatePoll;

import lombok.Builder;
import lombok.Getter;
import mops.controllers.DatePollConfigDto;
import mops.controllers.DatePollMetaInfDto;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.User.UserId;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DatePoll {

    private DatePollMetaInf datePollMetaInf;
    private DatePollId datePollId;
    private UserId creator;
    private DatePollConfig datePollConfig;
    private List<DatePollOption> datePollOptions;
    private List<UserId> participants;


    @SuppressWarnings("checkstyle:HiddenField")
    @Builder
    public DatePoll(final UserId creator,
                    final DatePollId datePollId,
                    final DatePollMetaInfDto datePollMetaInfDto,
                    final DatePollConfigDto datePollConfigDto,
                    final List<DatePollOptionDto> datePollOptionDtos,
                    final List<UserId> participants) {
        this.creator = creator;
        this.datePollId = datePollId;
        this.participants = participants;
        this.datePollMetaInf = new DatePollMetaInf(datePollMetaInfDto);
        this.datePollConfig = new DatePollConfig(datePollConfigDto);
        this.datePollOptions = datePollOptionDtos.stream()
                .map(DatePollOption::new)
                .collect(Collectors.toList());
    }

}
