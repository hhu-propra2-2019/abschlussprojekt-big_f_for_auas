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
        Validation validation = Validation.noErrors();
        if (startDate == null || endDate == null) {
            if (startDate == null) {
                validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_START_NULL));
            }
            if (endDate == null) {
                validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_END_NULL));
            }
        } else if (endDate.isBefore(startDate)) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SWAPPED));
        }
        return validation;
    }
}
