package mops.domain.models.datepoll;

import java.util.HashSet;
import java.util.stream.Collectors;
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

    /** Konstruktor wo man ja und vielleicht abstimmen kann.
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
    boolean belongsTo(UserId otherUser) {
        return this.user.equals(otherUser);
    }

    /**
     * Speichert eine neue Abstimmung eines Users. Passt Yes- Votes in den Entries entsprechend an.
     * @param newYes
     */
    void updateYes(Set<DatePollEntry> newYes) {
        Set<DatePollEntry> leftSetDifference = difference(newYes, selectedEntriesYes);
        Set<DatePollEntry> rightSetDifference = difference(selectedEntriesYes, newYes);
        leftSetDifference.forEach(DatePollEntry::incYesVote);
        rightSetDifference.forEach(DatePollEntry::decYesVote);
        this.selectedEntriesYes = newYes;
    }

    /**
     * Wie updateYes nur für maybe Votes.
     * @param newMaybe
     */
    void updateMaybe(Set<DatePollEntry> newMaybe) {
        Set<DatePollEntry>  leftSetDifference = difference(newMaybe, selectedEntriesMaybe);
        Set<DatePollEntry> rightSetDifference = difference(selectedEntriesMaybe, newMaybe);
        leftSetDifference.forEach(DatePollEntry::incMaybeVote);
        rightSetDifference.forEach(DatePollEntry::decMaybeVote);
        this.selectedEntriesMaybe = newMaybe;
    }

    /**
     * Gibt SetA - SetB zurück.
     * Bestimmt Gruppenzugehörigkeit nur an Hand der Timespan Objekten in DatePollEntry.
     * @param setA
     * @param setB
     * @return SetA - SetB (alle Elemente aus A, welche nicht in B sind)
     */
    private static Set<DatePollEntry> difference(Set<DatePollEntry> setA, Set<DatePollEntry> setB) {
        return setA.stream().filter(entryFromSetA -> setB.stream()
                .noneMatch(entryFromSetB -> entryFromSetB.representsSamePeriod(entryFromSetA)))
            .collect(Collectors.toSet());
    }
}
