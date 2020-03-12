package mops.domain.models.datepoll;

import lombok.Getter;
import mops.controllers.dtos.DatePollOptionDto;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.UserId;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class DatePollBuilder {

    public static final String COULD_NOT_CREATE = "The Builder contains errors and DatePoll could not be created";
    //muss nicht 1-1 im ByteCode bekannt sein.
    private transient DatePollMetaInf metaInf;
    private transient UserId pollCreator;
    private transient DatePollConfig config;
    private final transient List<DatePollOption> pollOptions = new ArrayList<>();
    private final transient List<UserId> pollParticipants = new ArrayList<>();
    private transient DatePollLink link;
    @Getter
    private transient Validation validationState;
    private final transient EnumSet<DatePollFields> validatedFields = EnumSet.noneOf(DatePollFields.class);

    public DatePollBuilder() {
        validationState = Validation.noErrors();
    }

    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    private <T extends ValidateAble> Optional<T> validationProcess(T validateAble) {
        final Validation newValidation = validateAble.validate();
        validationState.appendValidation(newValidation);
        Optional<T> result = Optional.empty();
        if (newValidation.hasNoErrors()) {
            result = Optional.of(validateAble);
        }
        return result;
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
                datePollMetaInf, metaInf -> this.metaInf = metaInf, DatePollFields.DATE_POLL_META_INF
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
                creator, id -> this.pollCreator = id, DatePollFields.CREATOR
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
                datePollConfig, config -> this.config = config, DatePollFields.DATE_POLL_CONFIG
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
        this.pollOptions.addAll(validateAllAndGetCorrect(
                datePollOptionsDtos.stream()
                        .map(dto -> new DatePollOption(dto.getStartDate(), dto.getEndDate()))
                        .collect(Collectors.toList())
        ));
        if (!pollOptions.isEmpty()) {
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
        this.pollParticipants.addAll(validateAllAndGetCorrect(participants));
        if (!this.pollParticipants.isEmpty()) {
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
                datePollLink, link -> this.link = link, DatePollFields.DATE_POLL_LINK
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
                    new PollRecordAndStatus(),
                    metaInf, pollCreator, config, pollOptions, pollParticipants, link
            );
        } else {
            throw new IllegalStateException(COULD_NOT_CREATE);
        }
    }

    private enum DatePollFields {
        DATE_POLL_CONFIG, DATE_POLL_LINK, DATE_POLL_META_INF, DATE_POLL_OPTIONS, CREATOR, PARTICIPANTS
    }

}
