package mops.domain.models.datepoll;

import lombok.Value;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public class DatePollMetaInf implements ValidateAble {

    private String title;
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
    private DatePollLifeCycle datePollLifeCycle;

    /**
     * ...
     * @return
     */
    public Validation validate() {
        Validation validation = Validation.noErrors();
        validation.appendValidation(datePollLocation.validate());
        validation.appendValidation(datePollDescription.validate());
        validation.appendValidation(datePollLifeCycle.validate());
        return validation;
    }
}
