package mops.domain.models.questionpoll;

import java.util.Optional;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.PollLink;
import mops.domain.models.group.GroupId;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@Getter
public class QuestionPoll {

    private final PollRecordAndStatus recordAndStatus;
    private final QuestionPollMetaInf metaInf;
    private final User creator;
    private final QuestionPollConfig config;
    private final Set<QuestionPollEntry> entries;
    private Set<GroupId> groups;
    private final Set<QuestionPollBallot> ballots;
    private final PollLink pollLink;

    public static QuestionPollBuilder builder() {
        return new QuestionPollBuilder();
    }

    /**
     * gibt den Status des Users zurück.
     * @param user User dessen Status zurückgegeben werden soll.
     * @return Status des Users.
     */
    public PollStatus getUserStatus(User user) {
        return recordAndStatus.getUserStatus(user);
    }

    /**
     * Gibt den Ballot eines Users zurück, wenn der Ballot existiert.
     * @param user User dessen Ballot (falls existent) zurückgegeben wird.
     * @return Ballot des Users oder Null
     */
    @SuppressWarnings({"PMD.LawOfDemeter"}) //stream
    private Optional<QuestionPollBallot> getUserBallot(UserId user) {
        return ballots.stream()
                .filter(questionPollBallot -> questionPollBallot.belongsTo(user))
                .findAny();
    }

    /**
     * Fügt einen neuen Stimmzettel zu einer Abstimmung hinzu.
     * @param user User, dessen Stimmzettel hinzugefügt wird.
     * @param yes Set mit allen Entries, bei denen der User für Ja gestimmt hat
     */
    public void castBallot(UserId user, Set<QuestionPollEntry> yes) {
        if (recordAndStatus.isTerminated()) {
            return;
        }
        //&& !participants.contains(user) --> TODO: Is participant in group?
        if (!config.isOpen()) {
            return;
        }
        if (config.isSingleChoice() && yes.size() > 1) {
            return;
        }
        final QuestionPollBallot ballot = getUserBallot(user)
                .orElse(new QuestionPollBallot(user, yes));
        ballots.add(ballot);
    }

    /**
     * Gibt zurück, ob ein User Participant dieser QuestionPoll ist.
     * @param user betreffender User
     * @return Boolean, ob der User Participant ist oder nicht.
     */
    //TODO: || participants.contains(user) --> group.contains(user)...
    public boolean isUserParticipant(UserId user) {
        return config.isOpen();
    }
}
