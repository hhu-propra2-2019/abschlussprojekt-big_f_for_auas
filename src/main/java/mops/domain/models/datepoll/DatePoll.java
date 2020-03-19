package mops.domain.models.datepoll;

import java.time.LocalDateTime;
import java.util.Set;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@SuppressFBWarnings(
        value = "URF_UNREAD_FIELD",
        justification = "Implemntierung folgt")
public final class  DatePoll {

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

    /**
     * Fügt einen neuen Stimmzettel hinzu, oder aktualisiert einen Bestehenden.
     * @param user
     * @param yes - DatePollEntries für welche der User mit ja gestimmt hat.
     * @param maybe - DatePollEntries für welche der User mit vielleicht gestimmt hat.
     */
    public void castBallot(UserId user, Set<DatePollEntry> yes, Set<DatePollEntry> maybe) {
        updatePollStatus();
        if (datePollRecordAndStatus.isTerminated()) {
            return;
        }
        if (!datePollConfig.isOpen() && !participants.contains(user)) {
            return;
        }
        if (datePollConfig.isSingleChoice() && yes.size() > 1) {
            return;
        }
        if (datePollConfig.isOpenForOwnEntries()) {
            aggregateNewEntries(yes, maybe);
        }

        DatePollBallot ballot = datePollBallots.stream()
            .filter(datePollBallot -> datePollBallot.belongsTo(user))
            .findAny()
            .orElse(new DatePollBallot(user, yes, maybe));
        datePollBallots.add(ballot);
        ballot.updateYes(yes);
        ballot.updateMaybe(maybe);
    }

    /**
     * Fügt neue Terminvorschläge zur Liste hinzu.
     * Wird sich wahrscheinlich noch ändern, sobald applicationService und Web Oberfläche da sind.
     * @param setOne
     * @param setTwo
     */
    private void aggregateNewEntries(Set<DatePollEntry> setOne, Set<DatePollEntry> setTwo) {
        Stream.concat(setOne.stream(), setTwo.stream())
            .filter(potentialNewEntry -> datePollEntries.stream()
                .noneMatch(knownEntry -> knownEntry.representsSamePeriod(potentialNewEntry)))
            .forEach(newEntry -> datePollEntries.add(newEntry));
    }

    private void updatePollStatus() {
        if (datePollMetaInf.isBeforeEnd(LocalDateTime.now())) {
            datePollRecordAndStatus.terminate();
        }
    }
}
