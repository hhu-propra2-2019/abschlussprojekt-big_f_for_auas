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
     * @return ...
     */
    @Override
    /*
     * noErrors() ist wie ein Konstruktor, nur mit expliziten namen, daher keine violationj of law of demeter
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public Validation validate() {
        final Validation validation = Validation.noErrors();
        validation.appendValidation(datePollLocation.validate());
        validation.appendValidation(datePollDescription.validate());
        validation.appendValidation(datePollLifeCycle.validate());
        return validation;
    }
}
