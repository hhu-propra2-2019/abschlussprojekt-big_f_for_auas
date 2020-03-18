package mops.domain.models.datepoll;

import lombok.Value;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.utils.DomainObjectCreationUtils;

@Value
public final class DatePollLocation implements ValidateAble {

    private transient String location;

    public DatePollLocation(String location) {
        this.location = DomainObjectCreationUtils.convertNullToEmptyAndTrim(location);
    }

    @Override
    public Validation validate() {
        return Validation.noErrors();
    }
}
