package mops.domain.models.datepoll;

import java.time.LocalDateTime;
import java.util.Set;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@SuppressFBWarnings(
        value = "URF_UNREAD_FIELD",
        justification = "Implementierung folgt")
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
    private Set<DatePollEntry> datePollEntries;
    @Getter
    private Set<UserId> participants;
    private Set<DatePollBallot> datePollBallots;
    @Getter
    private DatePollLink datePollLink;

    public static DatePollBuilder builder() {
        return new DatePollBuilder();
    }

    public PollStatus getUserStatus(User user) {
        return datePollRecordAndStatus.getUserStatus(user);
    }

    public void castBallot(DatePollBallot ballot) {
        updatePollStatus();
        if (datePollRecordAndStatus.isTerminated()) {
            return;
        }
        if (!datePollConfig.isOpen() && !participants.contains(ballot.getUser())) {
            return;
        }
        if (datePollConfig.isSingleChoice() && ballot.getYesEntriesSize() > 1) {
            return;
        }
    }

    private void updatePollStatus() {
        if (datePollMetaInf.isBeforeEnd(LocalDateTime.now())) {
            datePollRecordAndStatus.terminate();
        }
    }
}
