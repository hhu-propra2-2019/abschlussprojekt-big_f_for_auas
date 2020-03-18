package mops.domain.models;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Timespan implements ValidateAble {

    private transient LocalDateTime startDate;
    private transient LocalDateTime endDate;

    /**
     * Stellt sicher, dass das startDate vor dem endDate liegt.
     * @return Validation-Objekt mit oder ohne Error
     */
    @Override
    public Validation validate() {
        final Validation validation = Validation.noErrors();
        if (endDate.isBefore(startDate)) {
            validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SWAPPED));
        }
        return validation;
    }
}
