package mops.domain.models.datepoll;

import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
    //private Set<UserId> participants;
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
        } //&& !participants.contains(user) --> TODO: Is participant in group?
        if (config.isSingleChoice() && yes.size() > 1) {
            return;
        }
        if (config.isOpenForOwnEntries()) {
            aggregateNewEntries(yes);
            aggregateNewEntries(maybe);
        }

        final DatePollBallot ballot = getUserBallot(user)
                .orElse(new DatePollBallot(user, Collections.emptySet(), Collections.emptySet()));
        ballots.add(ballot);
        ballot.updateYes(yes, entries);
        ballot.updateMaybe(maybe, entries);
    }

    /**
     * Fügt neue Terminvorschläge zur Liste hinzu.
     * @param potentialNewEntries
     */
    @SuppressWarnings("PMD.LawOfDemeter") // streams.
    private void aggregateNewEntries(Set<DatePollEntry> potentialNewEntries) {
        potentialNewEntries.stream()
            .filter(Predicate.not(entries::contains))
            .filter(entry -> entry.getYesVotes() == 0 && entry.getMaybeVotes() == 0)
            .collect(Collectors.toSet())
            .forEach(entries::add);
    }

    private void updatePollStatus() {
        if (metaInf.isAfterEndOfDatePollTimespan(LocalDateTime.now())) {
            recordAndStatus.terminate();
        }
    }

    //TODO: || participants.contains(user) --> group.contains(user)...
    public boolean isUserParticipant(UserId user) {
        return config.isOpen();
    }
}
