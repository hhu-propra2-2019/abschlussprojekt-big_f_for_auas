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

    public Validation validate() {
        return null;
    }
}
