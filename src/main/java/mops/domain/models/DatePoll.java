package mops.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DatePoll {
    private DatePollMetaInf datePollMetaInf;
    private DatePollID datePollID;
    private User creator;
    private DatePollConfig datePollConfig;
    private List<DatePollOption> datePollOptionList;
    private List<User> participants;
}
