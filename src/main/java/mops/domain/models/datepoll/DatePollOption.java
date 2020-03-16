package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
class DatePollOption implements ValidateAble {

    //private final DatePollLifeCycle dateOptionDuration;
    //Anzahl der Stimmen fuer diesen Termin.
    private int votes;

    /*DatePollOption(final LocalDateTime startDate, final LocalDateTime endDate) {
        this.dateOptionDuration = new DatePollLifeCycle(startDate, endDate);
    }*/

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Override
    public Validation validate() {
        //return dateOptionDuration.validate();
        return Validation.noErrors();
    }

    public void validateDatePollOption(ValidationContext context) {
        MessageContext messageContext = context.getMessageContext();
        if (startDate.isAfter(endDate)) {
            messageContext.addMessage(
                    new MessageBuilder()
                            .error()
                            .source("lifecycle")
                            .defaultText("Das Startdatum muss von dem Enddatum liegen")
                            .build());
        }
        if (endDate.isBefore(LocalDateTime.now())) {
            messageContext.addMessage(
                    new MessageBuilder()
                            .error()
                            .source("lifecycle")
                            .defaultText("Das Enddatum muss in der Zukunft liegen")
                            .build());
        }
    }
}
