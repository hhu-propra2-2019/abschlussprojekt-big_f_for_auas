package mops.domain.models.datepoll;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import mops.domain.models.PollFields;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class DatePollBuilder {

    public static final String COULD_NOT_CREATE = "The Builder contains errors and DatePoll could not be created";
    //muss nicht 1-1 im ByteCode bekannt sein.
    private transient DatePollMetaInf metaInfTarget;
    private transient UserId pollCreatorTarget;
    private transient DatePollConfig configTarget;
    private final transient Set<DatePollEntry> pollEntryTargets = new HashSet<>();
    private final transient Set<UserId> pollParticipantTargets = new HashSet<>();
    private transient DatePollLink linkTarget;
    @Getter
    private Validation validationState;
    private final transient EnumSet<PollFields> validatedFields = EnumSet.noneOf(PollFields.class);

    private static final EnumSet<PollFields> VALID_SET = EnumSet.of(
            PollFields.DATE_POLL_META_INF,
            PollFields.DATE_POLL_LINK,
            PollFields.DATE_POLL_CONFIG,
            PollFields.DATE_POLL_ENTRIES,
            PollFields.CREATOR,
            //PollFields.TIMESPAN
            PollFields.PARTICIPANTS
    );

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
    private <T extends ValidateAble> Optional<T> validationProcess(T validateAble, PollFields fields) {
        final Validation newValidation = validateAble.validate();
        validationState = validationState.removeErrors(fields);
        validationState = validationState.appendValidation(newValidation);
        Optional<T> result = Optional.empty();
        if (newValidation.hasNoErrors()) {
            result = Optional.of(validateAble);
        }
        return result;
    }

    /*
     * hier liegt keine LawOfDemeter violation vor.
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    private <T extends ValidateAble> void validationProcessAndValidationHandling(
            T validateAble, Consumer<T> applyToValidated, PollFields addToFieldsAfterSuccessfulValidation) {
        validationProcess(validateAble, addToFieldsAfterSuccessfulValidation).ifPresent(validated -> {
            applyToValidated.accept(validated);
            validatedFields.add(addToFieldsAfterSuccessfulValidation);
        });
    }

    /*
     * streams stellen keine LawOfDemeter violation dar
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> Set<T> validateAllAndGetCorrect(Set<T> mappedOptions, PollFields fields) {
        return mappedOptions.stream()
                .map((T validateAble) -> validationProcess(validateAble, fields))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    /**
     * Setzt die Metainformationen, wenn diese die Validierung durchläufen.
     *
     * @param datePollMetaInf die Metainformationen.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder datePollMetaInf(DatePollMetaInf datePollMetaInf) {
        validationProcessAndValidationHandling(
                datePollMetaInf, metaInf -> this.metaInfTarget = metaInf, PollFields.DATE_POLL_META_INF
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
                creator, id -> this.pollCreatorTarget = id, PollFields.CREATOR
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
                datePollConfig, config -> this.configTarget = config, PollFields.DATE_POLL_CONFIG
        );
        return this;
    }

    /*
     * streams stellen keine LawOfDemeter violation dar
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    /*
    public DatePollBuilder datePollOptions(Set<DatePollOptionDto> datePollOptionDtoSet) {
        this.pollOptionTargets.addAll(validateAllAndGetCorrect(
                datePollOptionDtoSet.stream()
                        .map(dto -> new DatePollEntry(new Timespan(dto.getStartDate(), dto.getEndDate())))
                        .collect(Collectors.toSet()),
                PollFields.DATE_POLL_OPTIONS
        ));
        if (!pollOptionTargets.isEmpty()) {
            validatedFields.add(PollFields.DATE_POLL_OPTIONS);
        }
        return this;
    }
    */
    public DatePollBuilder datePollEntries(Set<DatePollEntry> datePollEntrySet) {
        this.pollEntryTargets.addAll(validateAllAndGetCorrect(
                datePollEntrySet.stream()
                        .map(entry -> new DatePollEntry(new Timespan(
                                entry.getSuggestedPeriod().getStartDate(), entry.getSuggestedPeriod().getEndDate())))
                        .collect(Collectors.toSet()),
                PollFields.DATE_POLL_ENTRIES
        ));
        if (!pollEntryTargets.isEmpty()) {
            validatedFields.add(PollFields.DATE_POLL_ENTRIES);
        }
        return this;
    }



    /**
     * Fügt alle validierte User der Teilnehmerliste hinzu.
     *
     * @param participants Teilnehmer die zu dieser Terminfindung hinzugefügt werden sollen.
     * @return Referenz auf diesen DatePollBuilder.
     */
    public DatePollBuilder participants(Set<UserId> participants) {
        this.pollParticipantTargets.addAll(validateAllAndGetCorrect(participants, PollFields.PARTICIPANTS));
        if (!this.pollParticipantTargets.isEmpty()) {
            validatedFields.add(PollFields.PARTICIPANTS);
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
                datePollLink, link -> this.linkTarget = link, PollFields.DATE_POLL_LINK
        );
        return this;
    }

    /*
     * Baut das DatePoll Objekt, wenn alle Konstruktionssteps mind. 1 mal erfolgreich waren.
     *
     * @return Ein DatePoll Objekt in einem validen State.
     */
    public DatePoll build() {
        if (validationState.hasNoErrors() && validatedFields.equals(VALID_SET)) {
            return new DatePoll(
                    new DatePollRecordAndStatus(),
                    metaInfTarget, pollCreatorTarget, configTarget,
                    pollEntryTargets, pollParticipantTargets,
                    new HashSet<DatePollBallot>(), linkTarget
            );
        } else {
            throw new IllegalStateException(COULD_NOT_CREATE);
        }
    }
}
