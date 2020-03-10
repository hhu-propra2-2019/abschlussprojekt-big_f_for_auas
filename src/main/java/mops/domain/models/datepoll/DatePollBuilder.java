package mops.domain.models.datepoll;

import lombok.Getter;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DatePollBuilder {

    public static final String COULD_NOT_CREATE = "The Builder contains errors and DatePoll could not be created";

    private DatePollMetaInf datePollMetaInf;
    private UserId creator;
    private DatePollConfig datePollConfig;
    private List<DatePollOption> datePollOptions = new ArrayList<>();
    private List<UserId> participants = new ArrayList<>();
    private DatePollLink datePollLink;
    @Getter
    private Validation validationState;
    private EnumSet<DatePollFields> validatedFields = EnumSet.noneOf(DatePollFields.class);

    private <T extends ValidateAble> Optional<T> validationProcess(T validateAble) {
        Validation newValidation = validateAble.validate();
        validationState.appendValidation(newValidation);
        if (newValidation.hasNoErrors()) {
            return Optional.of(validateAble);
        } else {
            return Optional.empty();
        }
    }

    private <T extends ValidateAble> void validationProcessAndValidationHandling(
            T validateAble, Consumer<T> applyToValidated, DatePollFields addToFieldsAfterSuccessfulValidation) {
        validationProcess(validateAble).ifPresent(validated -> {
            applyToValidated.accept(validated);
            validatedFields.add(addToFieldsAfterSuccessfulValidation);
        });
    }

    private <T extends ValidateAble> List<T> validateAllAndGetCorrect(List<T> mappedOptions) {
        return mappedOptions.stream()
                .map(this::validationProcess)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * Setzt die Metainformationen, wenn diese die Validierung durchläufen.
     *
     * @param datePollMetaInf die Metainformationen.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder datePollMetaInf(DatePollMetaInf datePollMetaInf) {
        validationProcessAndValidationHandling(
                datePollMetaInf, metaInf -> this.datePollMetaInf = metaInf, DatePollFields.DATE_POLL_META_INF
        );
        return this;
    }

    /**
     * Setzt den Ersteller, wenn dieser die Validierung durchläuft.
     *
     * @param creator der Ersteller einer Terminfindung.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder creator(UserId creator) {
        validationProcessAndValidationHandling(
                creator, id -> this.creator = id, DatePollFields.CREATOR
        );
        return this;
    }

    /**
     * Setzt die Configuration, wenn diese die Validierung durchläuft.
     *
     * @param datePollConfig die Konfiguration.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder datePollConfig(DatePollConfig datePollConfig) {
        validationProcessAndValidationHandling(
                datePollConfig, config -> this.datePollConfig = config, DatePollFields.DATE_POLL_CONFIG
        );
        return this;
    }

    /**
     * Fügt alle validierte Otions der Vorschlägeliste hinzu.
     *
     * @param datePollOptionsDtos Vorschläge die zu dieser Terminfindung hinzugefügt werden sollen.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder datePollOptions(List<DatePollOptionDto> datePollOptionsDtos) {
        this.datePollOptions.addAll(validateAllAndGetCorrect(
                datePollOptionsDtos.stream()
                        .map(dto -> new DatePollOption(dto.getDate()))
                        .collect(Collectors.toList())
        ));
        if (!datePollOptions.isEmpty()) {
            validatedFields.add(DatePollFields.DATE_POLL_OPTIONS);
        }
        return this;
    }

    /**
     * Fügt alle validierte User der Teilnehmerliste hinzu.
     *
     * @param participants Teilnehmer die zu dieser Terminfindung hinzugefügt werden sollen.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder participants(List<UserId> participants) {
        this.participants.addAll(validateAllAndGetCorrect(participants));
        if (!this.participants.isEmpty()) {
            validatedFields.add(DatePollFields.PARTICIPANTS);
        }
        return this;
    }

    /**
     * Setzt den Link, wenn dieser die Validierung durchläuft.
     *
     * @param datePollLink der veröfentlichungs Link.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder datePollLink(DatePollLink datePollLink) {
        validationProcessAndValidationHandling(
                datePollLink, link -> this.datePollLink = link, DatePollFields.DATE_POLL_LINK
        );
        return this;
    }

    /**
     * Baut das DatePoll Objekt, wenn alle Konstruktionssteps mind. 1 mal erfolgreich waren.
     *
     * @return Ein DatePoll Objekt in einem validen State.
     */
    public DatePoll build() {
        if (validationState.hasNoErrors() && EnumSet.allOf(DatePollFields.class).equals(validatedFields)) {
            return new DatePoll(
                    datePollMetaInf, creator, datePollConfig, datePollOptions, participants, datePollLink
            );
        } else {
            throw new IllegalStateException(COULD_NOT_CREATE);
        }
    }

    private enum DatePollFields {
        DATE_POLL_CONFIG, DATE_POLL_ID, DATE_POLL_LINK, DATE_POLL_META_INF, DATE_POLL_OPTIONS, CREATOR, PARTICIPANTS;
    }

}
