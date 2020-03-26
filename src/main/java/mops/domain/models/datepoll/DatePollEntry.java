package mops.domain.models.datepoll;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.infrastructure.controllers.dtos.DatePollEntryDto;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public final class DatePollEntry implements ValidateAble {

    @EqualsAndHashCode.Include private final Timespan suggestedPeriod;
    //Anzahl der Stimmen fuer diesen Termin.
    private int yesVotes;
    private int maybeVotes;

    public DatePollEntry(Timespan suggestedPeriod) {
        this.suggestedPeriod = suggestedPeriod;
        yesVotes = 0;
        maybeVotes = 0;
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    /* noErrors() ist Konstruktor*/
    public Validation validate() {
        final Validation validation = Validation.noErrors();
        return validation.appendValidation(suggestedPeriod.validate());
    }

    /**
     * Vergleich von Entries an Hand des vorgeschlagenen Timeslots.
     *
     * @param other
     * @return boolean
     */
    boolean representsSamePeriod(DatePollEntry other) { //NOPMD
        return suggestedPeriod.equals(other.suggestedPeriod);
    }

    void incYesVote() { //NOPMD
        yesVotes++;
    }

    void decYesVote() { //NOPMD
        yesVotes--;
    }

    void incMaybeVote() { //NOPMD
        yesVotes++;
    }

    void decMaybeVote() { //NOPMD
        maybeVotes--;
    }

    public DatePollEntryDto toDto() {
        return new DatePollEntryDto(this.suggestedPeriod.getStartDate(),
                this.suggestedPeriod.getEndDate());
    }
}
