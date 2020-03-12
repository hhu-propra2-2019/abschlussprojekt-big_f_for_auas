package mops.domain.models.datepoll;

import lombok.Value;
import mops.controllers.dtos.InputFieldNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public class DatePollLink implements ValidateAble {

    private static final String HOSTNAME = "mops.cs.hhu.de/";

    private String datePollIdentifier;

    public DatePollLink(final String newDatePollIdentifier) {
        this.datePollIdentifier = newDatePollIdentifier;
    }

    /**
     * ...
     * @return ...
     */
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (datePollIdentifier  == null) {
            validation = new Validation(InputFieldNames.DATE_POLL_IDENTIFIER, "Date_poll_identifier is null");
        }
        return validation;
    }
}
