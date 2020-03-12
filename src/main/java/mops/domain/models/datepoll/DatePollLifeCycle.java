package mops.domain.models.datepoll;

import lombok.Value;
import mops.controllers.dto.InputFieldNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

import java.time.LocalDateTime;

@Value
public class DatePollLifeCycle implements ValidateAble {
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
        Validation validation = Validation.noErrors();

        if (startDate.isBefore(endDate)) {
            errorMessage =
                    errorMessage + " StartDate " + startDate.toString() + " is bigger than " + endDate.toString() + "\n";
            validation.appendValidation(new Validation(InputFieldNames.DATE_POLL_LIFECYCLE, errorMessage));
        } else if (startDate.isEqual(endDate)) {
            errorMessage =
                    errorMessage + " StartDate " + startDate.toString() + " is equal to " + endDate.toString() + "\n";
            validation.appendValidation(new Validation(InputFieldNames.DATE_POLL_LIFECYCLE, errorMessage));
        }

        return validation;
    }
}
