package mops.domain.models.datepoll;

import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

public class DatePollLink implements ValidateAble {

    private static final String HOSTNAME = "mops.cs.hhu.de/";

    private String datePollIdentifier;

    public DatePollLink(final String newDatePollIdentifier) {
        this.datePollIdentifier = newDatePollIdentifier;
    }

    @Override
    public Validation validate() {
        return null;
    }
}
