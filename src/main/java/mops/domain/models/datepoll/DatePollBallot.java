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
     * @param userId
     * @param selectedEntriesYes
     * @param selectedEntriesMaybe
     */
    public DatePollBallot(UserId userId, Set<DatePollEntry> selectedEntriesYes,
        Set<DatePollEntry> selectedEntriesMaybe) {
        this.user = userId;
        this.selectedEntriesYes = selectedEntriesYes;
        this.selectedEntriesMaybe = selectedEntriesMaybe;
    }

    /** Konstruktor wo man nur ja abstimmen kann.
     *
     * @param userId
     * @param selectedEntriesYes
     */
    public DatePollBallot(UserId userId, Set<DatePollEntry> selectedEntriesYes) {
        this.user = userId;
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
    void updateYes(Set<DatePollEntry> newYes) { //NOPMD
        final Set<DatePollEntry> leftSetDifference = DatePollEntry.difference(newYes, selectedEntriesYes);
        final Set<DatePollEntry> rightSetDifference = DatePollEntry.difference(selectedEntriesYes, newYes);
        leftSetDifference.forEach(DatePollEntry::incYesVote); // PMD meckert. Wenn man es als Loop schreibt gehts durch
        rightSetDifference.forEach(DatePollEntry::decYesVote);
        this.selectedEntriesYes = newYes;
    }

    /**
     * Wie updateYes nur für maybe Votes.
     * @param newMaybe
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DefaultPackage"})
    void updateMaybe(Set<DatePollEntry> newMaybe) { //NOPMD
        final Set<DatePollEntry>  leftSetDifference = DatePollEntry.difference(newMaybe, selectedEntriesMaybe);
        final Set<DatePollEntry> rightSetDifference = DatePollEntry.difference(selectedEntriesMaybe, newMaybe);
        leftSetDifference.forEach(DatePollEntry::incMaybeVote); // als loop wirft pmd hier kein lawOfDemeter (??)
        rightSetDifference.forEach(DatePollEntry::decMaybeVote);
        this.selectedEntriesMaybe = newMaybe;
    }
}
