package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@RequiredArgsConstructor
class DatePollOption implements ValidateAble {

    private final Timespan lifeCycle;
    //Anzahl der Stimmen fuer diesen Termin.
    @Getter
    private int votes;

    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    /* noErrors() ist Konstruktor*/
    public Validation validate() {
        final Validation validation = Validation.noErrors();
        return validation.appendValidation(lifeCycle.validate());
    }
}
