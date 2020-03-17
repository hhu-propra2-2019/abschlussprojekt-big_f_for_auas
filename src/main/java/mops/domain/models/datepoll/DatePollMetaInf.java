package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.Setter;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.old.DatePollDescription;
import mops.domain.models.datepoll.old.DatePollLocation;

import java.time.LocalDateTime;

@Getter
@Setter
public class DatePollMetaInf implements ValidateAble {

    public static final int MAX_TITLE_LENGTH = 60;
    private String title = "";
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
    private Timespan datePollLifeCycle;

    public DatePollMetaInf(String title, String description, String location) {
        this.title = title;
        this.datePollDescription = new DatePollDescription(description);
        this.datePollLocation = new DatePollLocation(location);
        this.datePollLifeCycle = new Timespan(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
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
    @SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:MagicNumber"})
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (title.length() == 0) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_TITLE_EMPTY));
        } else if (title.length() >= MAX_TITLE_LENGTH) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_TITLE_TOO_LONG));
        }
        return validation
                .appendValidation(datePollLocation.validate())
                .appendValidation(datePollDescription.validate())
                .appendValidation(datePollLifeCycle.validate());
    }
}
