package mops.domain.models.datepoll;


import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

@Value
public final class DatePollDescription implements ValidateAble {

    public static final int MAX_TITLE_LENGTH = 600;
    private String description;

    public DatePollDescription(String description) {
        this.description = DomainObjectCreationUtils.convertNullToEmptyAndTrim(description);
    }

    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (description.length() > MAX_TITLE_LENGTH) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_DESCRIPTION_TOO_LONG));
        }
        return validation;
    }
}
