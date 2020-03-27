package mops.domain.models.datepoll;

import java.util.HashSet;
import java.util.function.Predicate;
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

    /**
     * Erzeugt leeres ballot.
     * @param userId
     */
    public DatePollBallot(UserId userId) {
        this.user = userId;
        this.selectedEntriesYes = new HashSet<>();
        this.selectedEntriesMaybe = new HashSet<>();
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
     * @param rootEntries referenz auf alle Entries im DatePoll root Aggregat.
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DefaultPackage"})
    void updateYes(Set<DatePollEntry> newYes, Set<DatePollEntry> rootEntries) { //NOPMD
        final Set<DatePollEntry> entriesToIncrement = newYes.stream()
            .filter(Predicate.not(selectedEntriesYes::contains)).collect(Collectors.toSet());
        final Set<DatePollEntry> entriesToDecrement = selectedEntriesYes.stream()
            .filter(Predicate.not(newYes::contains)).collect(Collectors.toSet());
        rootEntries.stream().filter(entriesToIncrement::contains).forEach(DatePollEntry::incYesVote);
        rootEntries.stream().filter(entriesToDecrement::contains).forEach(DatePollEntry::decYesVote);
        this.selectedEntriesYes = newYes;
    }

    /**
     * PSpeichert eine neue Abstimmung eines Users. Passt Maybe- Votes in den Entries entsprechend an.
     * @param newMaybe neue ausgewählte Entries für maybe abstimmung.
     * @param rootEntries referenz auf alle Entries im DatePoll root Aggregat.
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DefaultPackage"})
    void updateMaybe(Set<DatePollEntry> newMaybe, Set<DatePollEntry> rootEntries) { //NOPMD
        final Set<DatePollEntry> entriesToIncrement = newMaybe.stream()
            .filter(Predicate.not(selectedEntriesMaybe::contains)).collect(Collectors.toSet());
        final Set<DatePollEntry> entriesToDecrement = selectedEntriesYes.stream()
            .filter(Predicate.not(newMaybe::contains)).collect(Collectors.toSet());
        rootEntries.stream().filter(entriesToIncrement::contains).forEach(DatePollEntry::incMaybeVote);
        rootEntries.stream().filter(entriesToDecrement::contains).forEach(DatePollEntry::decMaybeVote);
        this.selectedEntriesMaybe = newMaybe;
    }
}
