package mops.domain.models.datepoll;

import lombok.Getter;
import lombok.Setter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.old.DatePollDescription;
import mops.domain.models.datepoll.old.DatePollLifeCycle;
import mops.domain.models.datepoll.old.DatePollLocation;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.binding.validation.ValidationContext;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class DatePollMetaInf implements ValidateAble, Serializable {

    private String title = "";
    private String description = "";
    private String location = "";
    private LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate = LocalDateTime.now();

    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
    private DatePollLifeCycle datePollLifeCycle;

    /**
     * ...
     * @return ...
     */
    @Override
    /*
     * noErrors() ist wie ein Konstruktor, nur mit expliziten namen, daher keine violationj of law of demeter
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public Validation validate() {
        final Validation validation = Validation.noErrors();
        validation.appendValidation(datePollLocation.validate());
        validation.appendValidation(datePollDescription.validate());
        validation.appendValidation(datePollLifeCycle.validate());
        return validation;
    }

    /**
     * Wird vom Builder oder von Web Flow für den State „mobileSchedulingNameSettings“
     * aufgerufen und aggregiert alle Fehlermeldungen aus den einzelnen Validierungsmethoden.
     * @param context Der ValidationContext wird vom Builder oder Web Flow übergeben und ausgewertet.
     */
    public void validateMobileSchedulingNameSettings(ValidationContext context) {
        MessageContext messageContext = context.getMessageContext();
        validateTitle()
                .ifPresent(messageContext::addMessage);
        validateDescription()
                .ifPresent(messageContext::addMessage);
        validateLocation()
                .ifPresent(messageContext::addMessage);

    }

    /**
     * Wird vom Builder oder von Web Flow für den State „mobileSchedulingAccessSettings“
     * aufgerufen und aggregiert alle Fehlermeldungen aus den einzelnen Validierungsmethoden.
     * @param context Der ValidationContext wird vom Builder oder Web Flow übergeben und ausgewertet.
     */
    public void validateMobileSchedulingAccessSettings(ValidationContext context) {
        MessageContext messageContext = context.getMessageContext();
        validateLifecycle().ifPresent(messageContext::addMessage);
    }

    // TODO: Sinnvolle Beschränkungen und Validierungen setzen, diese Methoden sind nur als Beispiel gedacht.

    private Optional<MessageResolver> validateTitle() {
        if (title.length() > 4) {
            return Optional.of(
                    new MessageBuilder()
                            .error()
                            .source("title")
                            .defaultText("Titel muss kürzer als 5 Zeichen sein")
                            .build());
        }
        return Optional.empty();
    }

    private Optional<MessageResolver> validateDescription() {
        if (description.length() > 200) {
            return Optional.of(
                    new MessageBuilder()
                            .error()
                            .source("description")
                            .defaultText("Beschreibung muss kürzer als 200 Zeichen sein").build());
        }
        return Optional.empty();
    }

    private Optional<MessageResolver> validateLocation() {
        if (location.length() > 100) {
            return Optional.of(
                    new MessageBuilder()
                            .error()
                            .source("location")
                            .defaultText("Ortsangabe muss kürzer als 100 Zeichen sein").build());
        }
        return Optional.empty();
    }

    private Optional<MessageResolver> validateLifecycle() {
        Optional<MessageResolver> messageResolver = Optional.empty();
        if (startDate.isAfter(endDate)) {
            messageResolver = Optional.of(
                    new MessageBuilder()
                            .error()
                            .source("lifecycle")
                            .defaultText("Das Startdatum muss von dem Enddatum liegen")
                            .build());
        }
        if (endDate.isBefore(LocalDateTime.now())) {
            messageResolver = Optional.of(
                    new MessageBuilder()
                            .error()
                            .source("lifecycle")
                            .defaultText("Das Enddatum muss in der Zukunft liegen")
                            .build());
        }
        return messageResolver;
    }
}
