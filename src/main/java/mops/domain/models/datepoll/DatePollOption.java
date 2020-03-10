package mops.domain.models.datepoll;

import mops.controllers.DatePollOptionDto;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.util.Date;

class DatePollOption implements ValidateAble {
    private Date date;
    //Anzahl der Stimmen fuer diesen Termin.
    private int votes;

    DatePollOption(final Date date) {

    }

    @Override
    public Validation validate() {
        return null;
    }
}
