package mops.domain.models.datepoll;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.PollDescription;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class DatePollMetaInf implements ValidateAble {

    public static final int MAX_TITLE_LENGTH = 60;
    private String title;
    private PollDescription description;
    private DatePollLocation location;
    private Timespan timespan;

    public DatePollMetaInf(String title, String description, String location) {
        this.title = DomainObjectCreationUtils.convertNullToEmptyAndTrim(title);
        this.description = new PollDescription(description);
        this.location = new DatePollLocation(location);
        this.timespan = new Timespan(null, null);
    }

    public DatePollMetaInf(String title, String description, String location, Timespan timespan) {
        this(title, description, location);
        this.timespan = timespan;
    }


    public boolean isBeforeEnd(LocalDateTime time) {
        return timespan.isBeforeEnd(time);
    }

    @Override
    /*
     * noErrors() ist wie ein Konstruktor, nur mit expliziten namen, daher keine violation des LawOfDemeter
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (title.length() == 0) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_TITLE_EMPTY));
        } else if (title.length() >= MAX_TITLE_LENGTH) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_TITLE_TOO_LONG));
        }
        return validation
                .appendValidation(location.validate())
                .appendValidation(description.validate())
                .appendValidation(timespan.validate());
    }

}
