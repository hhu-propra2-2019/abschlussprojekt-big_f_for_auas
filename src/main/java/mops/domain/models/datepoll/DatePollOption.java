package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@RequiredArgsConstructor
class DatePollOption implements ValidateAble {

    private final Timespan lifeCycle;
    //private final DatePollLifeCycle dateOptionDuration;
    //Anzahl der Stimmen fuer diesen Termin.
    @Getter
    private int votes;

    /*DatePollOption(final LocalDateTime startDate, final LocalDateTime endDate) {
        this.dateOptionDuration = new DatePollLifeCycle(startDate, endDate);
    }*/

    @Override
    public Validation validate() {
        //return dateOptionDuration.validate();
        return Validation.noErrors();
    }
}
