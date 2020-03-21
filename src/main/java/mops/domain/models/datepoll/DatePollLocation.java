package mops.domain.models.datepoll;

import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

@Value
public final class DatePollLocation implements ValidateAble {

    private static final int MAX_LOCATION_LENGTH = 200;
    private transient String location;

    public DatePollLocation(String location) {
        this.location = DomainObjectCreationUtils.convertNullToEmptyAndTrim(location);
    }

    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (location.length() > MAX_LOCATION_LENGTH) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_LOCATION_TOO_LONG));
        }
        return validation;
    }
}
