package mops.domain.models.datepoll;

import lombok.Getter;
import mops.controllers.dtos.DatePollOptionDto;
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
    //muss nicht 1-1 im ByteCode bekannt sein.
    private transient DatePollMetaInf metaInfTarget;
    private transient UserId pollCreatorTarget;
    private transient DatePollConfig configTarget;
    private final transient List<DatePollOption> pollOptionTargets = new ArrayList<>();
    private final transient List<UserId> pollParticipantTargets = new ArrayList<>();
    private transient DatePollLink linkTarget;
    @Getter
    private Validation validationState;
    private final transient EnumSet<DatePollFields> validatedFields = EnumSet.noneOf(DatePollFields.class);

    public DatePollBuilder() {
        validationState = Validation.noErrors();
    }

    /*
     * dataflowAnomaly sollte nicht auftreten,
     * LawOfDemeter violation ist akzeptabel, denn die Validierung muss auf dem validateable Objekt aufgerufen werden
     * und wir wollen die Validierungsauswerdung in einem Validierungsobjekt Kapseln, so haben die ValidierungsObkete
     * weniger Responsebilities
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.LawOfDemeter"})//NOPMD
    private <T extends ValidateAble> Optional<T> validationProcess(T validateAble) {
        final Validation newValidation = validateAble.validate();
        validationState.appendValidation(newValidation);
        Optional<T> result = Optional.empty();
        if (newValidation.hasNoErrors()) {
            result = Optional.of(validateAble);
        }
        return result;
    }

    /*
     * hier liegt keine LawOfDemeter violation vor.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> void validationProcessAndValidationHandling(
            T validateAble, Consumer<T> applyToValidated, DatePollFields addToFieldsAfterSuccessfulValidation) {
        validationProcess(validateAble).ifPresent(validated -> {
            applyToValidated.accept(validated);
            validatedFields.add(addToFieldsAfterSuccessfulValidation);
        });
    }

    /*
     * streams stellen keine LawOfDemeter violation dar
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
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
                datePollMetaInf, metaInf -> this.metaInfTarget = metaInf, DatePollFields.DATE_POLL_META_INF
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
                creator, id -> this.pollCreatorTarget = id, DatePollFields.CREATOR
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
                datePollConfig, config -> this.configTarget = config, DatePollFields.DATE_POLL_CONFIG
        );
        return this;
    }

    /**
     * Fügt alle validierte Otions der Vorschlägeliste hinzu.
     *
     * @param datePollOptionsDtos Vorschläge die zu dieser Terminfindung hinzugefügt werden sollen.
     * @return Referenz auf diesen DatePollBuilder.
     */
    /*
     * kann keine violation feststellen.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public DatePollBuilder datePollOptions(List<DatePollOptionDto> datePollOptionsDtos) {
        pollOptionTargets.addAll(validateAllAndGetCorrect(
                datePollOptionsDtos.stream()
                        .map(dto -> new DatePollOption(dto.getStartDate(), dto.getEndDate()))
                        .collect(Collectors.toList())
        ));
        if (!pollOptionTargets.isEmpty()) {
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
        this.pollParticipantTargets.addAll(validateAllAndGetCorrect(participants));
        if (!this.pollParticipantTargets.isEmpty()) {
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
                datePollLink, link -> this.linkTarget = link, DatePollFields.DATE_POLL_LINK
        );
        return this;
    }

    /**
     * Baut das DatePoll Objekt, wenn alle Konstruktionssteps mind. 1 mal erfolgreich waren.
     *
     * @return Ein DatePoll Objekt in einem validen State.
     */
    public DatePoll build() {
        if (validationState.hasNoErrors() && validatedFields.equals(EnumSet.allOf(DatePollFields.class))) {
            return new DatePoll(
                    new PollRecordAndStatus(),
                    metaInfTarget, pollCreatorTarget, configTarget,
                    pollOptionTargets, pollParticipantTargets, linkTarget
            );
        } else {
            throw new IllegalStateException(COULD_NOT_CREATE);
        }
    }

    enum DatePollFields {
        DATE_POLL_CONFIG, DATE_POLL_LINK, DATE_POLL_META_INF, DATE_POLL_OPTIONS, CREATOR, PARTICIPANTS
    }

}
