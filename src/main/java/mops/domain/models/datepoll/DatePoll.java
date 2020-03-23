package mops.domain.models.datepoll;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.UserId;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

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
    @Getter
    private Set<DatePollEntry> datePollEntries;
    private Set<UserId> participants;
    private Set<DatePollBallot> datePollBallots;

    @Getter
    private DatePollLink datePollLink;

    public static DatePollBuilder builder() {
        return new DatePollBuilder();
    }

    public PollStatus getUserStatus(UserId user) {
        return datePollRecordAndStatus.getUserStatus(user);
    }

    @SuppressWarnings({"PMD.LawOfDemeter"}) //stream
    public Optional<DatePollBallot> getUserBallot(UserId user) {
        return datePollBallots.stream()
                .filter(datePollBallot -> datePollBallot.belongsTo(user))
                .findAny();
    }

    /**
     * Fügt einen neuen Stimmzettel hinzu, oder aktualisiert einen Bestehenden.
     *
     * @param user
     * @param yes   - DatePollEntries für welche der User mit ja gestimmt hat.
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
            aggregateNewEntries(yes);
            aggregateNewEntries(maybe);
        }

        final DatePollBallot ballot = getUserBallot(user)
                .orElse(new DatePollBallot(user, yes, maybe));
        datePollBallots.add(ballot);
        ballot.updateYes(yes);
        ballot.updateMaybe(maybe);
    }

    /**
     * Fügt neue Terminvorschläge zur Liste hinzu.
     * Wird sich wahrscheinlich noch ändern, sobald applicationService und Web Oberfläche da sind.
     *
     * @param potentialNewEntries
     */
    private void aggregateNewEntries(Set<DatePollEntry> potentialNewEntries) {
        final Set<DatePollEntry> newEntries = DatePollEntry.difference(datePollEntries, potentialNewEntries);
        datePollEntries.addAll(newEntries);
    }

    private void updatePollStatus() {
        if (datePollMetaInf.isBeforeEnd(LocalDateTime.now())) {
            datePollRecordAndStatus.terminate();
        }
    }

    public boolean isUserParticipant(UserId user) {
        return datePollConfig.isOpen() || participants.contains(user);
    }
}
