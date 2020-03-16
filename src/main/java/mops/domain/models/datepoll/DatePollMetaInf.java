package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.Setter;
import mops.controllers.dtos.InputFieldNames;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.old.DatePollDescription;
import mops.domain.models.datepoll.old.DatePollLocation;

import java.time.LocalDateTime;

@Getter
@Setter
public class DatePollMetaInf implements ValidateAble {

    private String title = "";
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
    private Timespan datePollLifeCycle;

    public DatePollMetaInf(String title, String description, String location) {
        this.title = title;
        this.datePollDescription = new DatePollDescription(description);
        this.datePollLocation = new DatePollLocation(location);
        this.datePollLifeCycle = new Timespan(LocalDateTime.now(), LocalDateTime.now());
    }

    public DatePollMetaInf(String title, String description, String location, Timespan lifecycle) {
        this(title, description, location);
        this.datePollLifeCycle = lifecycle;
    }

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
        if (title.length() == 0) {
            validation.appendValidation(new Validation(InputFieldNames.DATE_POLL_TITLE_TOO_LONG, ""));
        }
        validation.appendValidation(datePollLocation.validate());
        validation.appendValidation(datePollDescription.validate());
        validation.appendValidation(datePollLifeCycle.validate());
        return validation;
    }
}
