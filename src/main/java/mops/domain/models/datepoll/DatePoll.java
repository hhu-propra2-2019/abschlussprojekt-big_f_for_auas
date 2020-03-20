package mops.domain.models.datepoll;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

import java.util.List;

@AllArgsConstructor
@SuppressFBWarnings(
        value = "URF_UNREAD_FIELD",
        justification = "Implemntierung folgt")
public final class DatePoll {


    @Getter
    private DatePollRecordAndStatus datePollRecordAndStatus;
    @Getter
    private DatePollMetaInf datePollMetaInf;
    @Getter
    private final UserId creator;
    @Getter
    private DatePollConfig datePollConfig;
    @Getter
    private List<DatePollOption> datePollOptions;
    @Getter
    private List<UserId> participants;
    @Getter
    private DatePollLink datePollLink;

    public static DatePollBuilder builder() {
        return new DatePollBuilder();
    }

    public PollStatus getUserStatus(User user) {
        return datePollRecordAndStatus.getUserStatus(user);
    }

}
