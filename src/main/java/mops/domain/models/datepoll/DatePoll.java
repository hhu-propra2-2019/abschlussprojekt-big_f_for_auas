package mops.domain.models.datepoll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

import java.util.List;

@AllArgsConstructor
public final class DatePoll {


    private PollRecordAndStatus pollRecordAndStatus;
    @Getter private DatePollMetaInf datePollMetaInf;
    private final UserId creator;
    private DatePollConfig datePollConfig;
    private List<DatePollEntry> datePollEntries;
    private List<UserId> participants;

    /**
     * Link fuer den DatePoll.
     */
    @Getter private DatePollLink datePollLink;

    public static DatePollBuilder builder() {
        return new DatePollBuilder();
    }

    public PollStatus getUserStatus(User user) {
        return pollRecordAndStatus.getUserStatus(user);
    }

}
