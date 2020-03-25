package mops.domain.models.questionpoll;

import java.util.Set;

import lombok.Value;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

/**
 * Die Ballot (Deutsch: Wahlzettel) speichert welche User für welche Einträge abstimmen.
 */
@Value
public class QuestionPollBallot implements ValidateAble {
    private final UserId user;
    private final Set<QuestionPollEntry> selectedEntries;

    /**
     * Konstruktor.
     * @param qpUserId
     * @param selectedEntries
     */
    public QuestionPollBallot(UserId qpUserId, Set<QuestionPollEntry> selectedEntries) {
        this.user = qpUserId;
        this.selectedEntries = selectedEntries;
    }

    /**
     * Anzahl der ja stimmen.
     * @return int
     */
    public int getYesEntriesSize() {
        return selectedEntries.size();
    }

    /**
     * ...
     * @return...
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }

    boolean belongsTo(UserId otherUser) { //NOPMD
        return this.user.equals(otherUser);
    }

}
