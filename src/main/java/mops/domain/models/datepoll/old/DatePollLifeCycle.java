package mops.domain.models.datepoll.old;

import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class DatePollLifeCycle implements ValidateAble, Serializable {
    private final LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Erzeugt ein Value Objekt für die Lebenspanne einer Terminfindung.
     * @param startDate start der Terminfindung
     * @param endDate ende der Terminfindung
     */
    public DatePollLifeCycle(LocalDateTime startDate, LocalDateTime endDate) {
         this.startDate = startDate;
         this.endDate = endDate;
    }

    /**
     * ...
     * @return
     */
    @SuppressWarnings("checkstyle:LineLength")
    @Override
    public Validation validate() {
        String errorMessage = "StartDate and EndDate are not valid. DATE_POLL_LIFECYCLE contains problems:\n";
        final Validation validation = Validation.noErrors();

        if (startDate.isBefore(endDate)) {
            errorMessage +=
                    errorMessage + " StartDate " + startDate.toString() + " is bigger than " + endDate.toString() + "\n";
            validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_LIFECYCLE));
        } else if (startDate.isEqual(endDate)) {
            errorMessage +=
                    errorMessage + " StartDate " + startDate.toString() + " is equal to " + endDate.toString() + "\n";
            validation.appendValidation(new Validation(FieldErrorNames.DATE_POLL_LIFECYCLE));
        }

        return validation;
    }
}
