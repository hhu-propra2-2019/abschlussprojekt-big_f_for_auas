package mops.domain.models.datepoll;

import java.util.HashSet;
import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

import java.util.Set;

@Getter
public class DatePollBallot implements ValidateAble {
    private final UserId user;
    private Set<DatePollEntry> selectedEntriesYes;
    private Set<DatePollEntry> selectedEntriesMaybe;

    /**
     * Konstruktor wo man ja und vielleicht abstimmen kann.
     * @param qpUserId
     * @param selectedEntriesYes
     * @param selectedEntriesMaybe
     */
    public DatePollBallot(UserId qpUserId, Set<DatePollEntry> selectedEntriesYes,
        Set<DatePollEntry> selectedEntriesMaybe) {
        this.user = qpUserId;
        this.selectedEntriesYes = selectedEntriesYes;
        this.selectedEntriesMaybe = selectedEntriesMaybe;
    }

    /** Konstruktor wo man nur ja abstimmen kann.
     *
     * @param qpUserId
     * @param selectedEntriesYes
     */
    public DatePollBallot(UserId qpUserId, Set<DatePollEntry> selectedEntriesYes) {
        this.user = qpUserId;
        this.selectedEntriesYes = selectedEntriesYes;
        this.selectedEntriesMaybe = new HashSet<>();
    }

    /**
     * Anzahl der ja stimmen.
     * @return int
     */
    public int getYesEntriesSize() {
        return selectedEntriesYes.size();
    }

    /**
     * Anzahl der vielleicht stimmen.
     * @return int
     */
    public int getMaybeEntriesSize() {
        return selectedEntriesMaybe.size();
    }

    /**
     * ...
     * @return...
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }

    /**
     * Gibt an ob das Ballot dem übergebenem User gehört.
     * @param otherUser
     * @return boolean
     */
    boolean belongsTo(UserId otherUser) { //NOPMD
        return this.user.equals(otherUser);
    }

    /**
     * Speichert eine neue Abstimmung eines Users. Passt Yes- Votes in den Entries entsprechend an.
     * @param newYes
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DefaultPackage"})
    void updateYes(Set<DatePollEntry> newYes, Set<DatePollEntry> rootEntries) { //NOPMD
        final Set<DatePollEntry> leftSetDifference = DatePollEntry.difference(newYes, selectedEntriesYes);
        final Set<DatePollEntry> rightSetDifference = DatePollEntry.difference(selectedEntriesYes, newYes);
        rootEntries.stream().filter(leftSetDifference::contains).forEach(DatePollEntry::incYesVote);
        rootEntries.stream().filter(rightSetDifference::contains).forEach(DatePollEntry::decYesVote);
        this.selectedEntriesYes = newYes;
    }

    /**
     * Wie updateYes nur für maybe Votes.
     * @param newMaybe
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DefaultPackage"})
    void updateMaybe(Set<DatePollEntry> newMaybe, , Set<DatePollEntry> rootEntries) { //NOPMD
        final Set<DatePollEntry>  leftSetDifference = DatePollEntry.difference(newMaybe, selectedEntriesMaybe);
        final Set<DatePollEntry> rightSetDifference = DatePollEntry.difference(selectedEntriesMaybe, newMaybe);
        rootEntries.stream().filter(leftSetDifference::contains).forEach(DatePollEntry::incMaybeVote);
        rootEntries.stream().filter(rightSetDifference::contains).forEach(DatePollEntry::decYesVote);
        this.selectedEntriesMaybe = newMaybe;
    }
}
