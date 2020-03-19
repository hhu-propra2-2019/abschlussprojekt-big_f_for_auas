package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.Setter;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public final class DatePollMetaInf implements ValidateAble {

    public static final int MAX_TITLE_LENGTH = 60;
    private String title;
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
    private Timespan datePollLifeCycle;

    public DatePollMetaInf(String title, String description, String location) {
        this.title = DomainObjectCreationUtils.convertNullToEmptyAndTrim(title);
        this.datePollDescription = new DatePollDescription(description);
        this.datePollLocation = new DatePollLocation(location);
        this.datePollLifeCycle = new Timespan(LocalDateTime.now(), LocalDateTime.now().minusDays(1));
    }

    public DatePollMetaInf(String title, String description, String location, Timespan lifecycle) {
        this(title, description, location);
        this.datePollLifeCycle = lifecycle;
    }

    @Override
    public Validation validate() {
        return validateSecondStep();
    }

    /*
     * noErrors() ist wie ein Konstruktor, nur mit expliziten namen, daher keine violation des LawOfDemeter
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public Validation validateFirstStep() {
        Validation validation = Validation.noErrors();
        if (title.length() == 0) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_TITLE_EMPTY));
        } else if (title.length() >= MAX_TITLE_LENGTH) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_TITLE_TOO_LONG));
        }
        return validation
                .appendValidation(datePollLocation.validate())
                .appendValidation(datePollDescription.validate());
    }

    /*
     * ViolationFirstStep liegt in der selben Klasse und konstruiert das Validation Objekt sowieso, eine Violation ist
     * hier nicht weiter tragisch, da sie den Sinn der Regel nicht verletzt.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public Validation validateSecondStep() {
        return validateFirstStep().appendValidation(datePollLifeCycle.validate());
    }
}
