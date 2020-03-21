package mops.domain.models;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public final class Timespan implements ValidateAble, Comparable<Timespan>, Serializable {

    public static final long serialVersionUID = 9786879798798789L;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Stellt sicher, dass das startDate vor dem endDate liegt.
     *
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
        } else if (startDate.equals(endDate)) {
            validation = validation.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SAME));
        }
        return validation;
    }

    /**
     * Ist die übergebene Zeit vor dem Ende?
     * @param time
     * @return boolean
     */
    public boolean isBeforeEnd(LocalDateTime time) {
        return this.endDate.isAfter(time);
    }

    @Override
    public int compareTo(Timespan timespan) {
        final int difference = this.startDate.compareTo(timespan.startDate);
        // Wenn Startdatum und -zeit gleich sind, ist das Enddatum ausschlaggebend
        if (difference == 0) {
            return this.endDate.compareTo(timespan.endDate);
        }
        return difference;
    }
}
