package mops.domain.models.datepoll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.PollLink;
import mops.domain.models.group.GroupId;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.UserId;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Getter
@AllArgsConstructor
public final class DatePoll {

    private DatePollRecordAndStatus recordAndStatus;
    private DatePollMetaInf metaInf;
    private final UserId creator;
    private DatePollConfig config;
    private Set<DatePollEntry> entries;
    private Set<UserId> participants;
    private Set<GroupId> groups;
    private Set<DatePollBallot> ballots;
    private PollLink pollLink;

    public static DatePollBuilder builder() {
        return new DatePollBuilder();
    }

    public PollStatus getUserStatus(UserId user) {
        return recordAndStatus.getUserStatus(user);
    }

    @SuppressWarnings({"PMD.LawOfDemeter"}) //stream
    public Optional<DatePollBallot> getUserBallot(UserId user) {
        return ballots.stream()
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
        if (recordAndStatus.isTerminated()) {
            return;
        }
        if (!config.isOpen() && !participants.contains(user)) {
            return;
        }
        if (config.isSingleChoice() && yes.size() > 1) {
            return;
        }
        if (config.isOpenForOwnEntries()) {
            aggregateNewEntries(yes);
            aggregateNewEntries(maybe);
        }

        final DatePollBallot ballot = getUserBallot(user)
                .orElse(new DatePollBallot(user, yes, maybe));
        ballots.add(ballot);
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
        final Set<DatePollEntry> newEntries = DatePollEntry.difference(entries, potentialNewEntries);
        entries.addAll(newEntries);
    }

    private void updatePollStatus() {
        if (metaInf.isBeforeEnd(LocalDateTime.now())) {
            recordAndStatus.terminate();
        }
    }

    public boolean isUserParticipant(UserId user) {
        return config.isOpen() || participants.contains(user);
    }
}
