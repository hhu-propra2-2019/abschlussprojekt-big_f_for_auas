package mops.domain.models.datepoll;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBallot;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@SuppressFBWarnings(
        value = "URF_UNREAD_FIELD",
        justification = "Implemntierung folgt")
public final class DatePoll {

    @Getter
    private DatePollRecordAndStatus datePollRecordAndStatus;
    @Getter
    private DatePollMetaInf datePollMetaInf;
    private final UserId creator;
    private DatePollConfig datePollConfig;
    private Set<DatePollEntry> datePollEntries;
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

        UserId ballotCaster = ballot.getUser();
        Set<DatePollEntry> yesVotes = ballot.getSelectedEntriesYes();
        Set<DatePollEntry> maybeVotes = ballot.getSelectedEntriesMaybe();

        DatePollBallot oldBallot = datePollBallots.stream()
            .filter(oldBallots -> oldBallots.getUser().equals(ballotCaster))
            .findAny()
            .orElse(new DatePollBallot(ballotCaster,
                new HashSet<DatePollEntry>(),
                new HashSet<DatePollEntry>()));

        Set<DatePollEntry> oldYesVotes = oldBallot.getSelectedEntriesYes();
        Set<DatePollEntry> oldMaybeVotes = oldBallot.getSelectedEntriesMaybe();

        Set<DatePollEntry> toBeIncremented = yesVotes.stream()
                .filter(datePollEntry -> !oldYesVotes.contains(datePollEntry))
                .collect(Collectors.toSet());
        Set<DatePollEntry> toBeDecremented = oldYesVotes.stream()
                .filter(datePollEntry -> !yesVotes.contains(datePollEntry))
                .collect(Collectors.toSet());

        toBeIncremented.stream().forEach(datePollEntry -> datePollEntry.incYesVote());
        toBeDecremented.stream().forEach(datePollEntry -> datePollEntry.decYesVote());
    }

    private void updatePollStatus() {
        if (datePollMetaInf.isBeforeEnd(LocalDateTime.now())) {
            datePollRecordAndStatus.terminate();
        }
    }
}
