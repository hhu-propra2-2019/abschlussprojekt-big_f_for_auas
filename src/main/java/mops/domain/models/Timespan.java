package mops.domain.models;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Timespan implements ValidateAble {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Stellt sicher, dass das startDate vor dem endDate liegt.
     * @return Validation-Objekt mit oder ohne Error
     */
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (startDateTime == null) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_START_NULL));
        } else if (endDateTime == null) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_END_NULL));
        } else if (endDateTime.isBefore(startDateTime)) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SWAPPED));
        }
        return validation;
    }

    /**
     * Ist die übergebene Zeit vor dem Ende?
     * @param time
     * @return boolean
     */
    public boolean isBeforeEnd(LocalDateTime time) {
        return this.endDateTime.isAfter(time);
    }

    /**
     * Formatiert die Timespan als String.
     * @return formattierten String.
     */
    public String toString() {
        final String startDateString = startDateTime.toLocalDate().toString();
        final String startTimeString = startDateTime.toLocalTime().toString();
        final String endDateString = endDateTime.toLocalDate().toString();
        final String endTimeString = endDateTime.toLocalTime().toString();

        return startDateString + ", " + startTimeString + " Uhr " + " - "
                + endDateString + ", " + endTimeString + " Uhr";
    }
}
