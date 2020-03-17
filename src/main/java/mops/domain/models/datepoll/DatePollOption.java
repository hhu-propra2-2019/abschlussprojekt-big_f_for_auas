package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
class DatePollOption implements ValidateAble {

    //private final DatePollLifeCycle dateOptionDuration;
    //Anzahl der Stimmen fuer diesen Termin.
    private int votes;

    /*DatePollOption(final LocalDateTime startDate, final LocalDateTime endDate) {
        this.dateOptionDuration = new DatePollLifeCycle(startDate, endDate);
    }*/

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Override
    public Validation validate() {
        //return dateOptionDuration.validate();
        return Validation.noErrors();
    }
}
