package mops.domain.models.datepoll;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

import java.util.List;

@AllArgsConstructor
public final class DatePoll {

    @Getter
    private PollRecordAndStatus pollRecordAndStatus;
    @Getter
    private DatePollMetaInf datePollMetaInf;
    private final UserId creator;
    private DatePollConfig datePollConfig;
    private List<DatePollEntry> datePollEntries;
    private List<UserId> participants;
    private Set<DatePollBallot> datePollBallots;

    @Getter
    private DatePollLink datePollLink;

    public static DatePollBuilder builder() {
        return new DatePollBuilder();
    }

    public PollStatus getUserStatus(User user) {
        return pollRecordAndStatus.getUserStatus(user);
    }

    public void castBallot(DatePollBallot ballot) {
        updatePollStatus();
        if (pollRecordAndStatus.isTerminated()) {
            return;
        }
    }

    private void updatePollStatus() {
        if (datePollMetaInf.isBeforeEnd(LocalDateTime.now())) {
            pollRecordAndStatus.terminate();
        }
    }
}
