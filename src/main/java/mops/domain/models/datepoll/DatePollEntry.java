package mops.domain.models.datepoll;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@EqualsAndHashCode
@RequiredArgsConstructor
class DatePollEntry implements ValidateAble {

    private final Timespan suggestedPeriod;
    //Anzahl der Stimmen fuer diesen Termin.
    @Getter
    private int yesVotes;
    @Getter
    private int maybeVotes;

    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    /* noErrors() ist Konstruktor*/
    public Validation validate() {
        final Validation validation = Validation.noErrors();
        return validation.appendValidation(suggestedPeriod.validate());
    }

    /**
     * Vergleich von Entries an Hand des vorgeschlagenen Timeslots.
     * @param other
     * @return boolean
     */
    boolean representsSamePeriod(DatePollEntry other) {
        return suggestedPeriod.equals(other.suggestedPeriod);
    }

    void incYesVote() {
        yesVotes++;
    }

    void decYesVote() {
        yesVotes--;
    }

    void incMaybeVote() {
        yesVotes++;
    }

    void decMaybeVote() {
        maybeVotes--;
    }
}
