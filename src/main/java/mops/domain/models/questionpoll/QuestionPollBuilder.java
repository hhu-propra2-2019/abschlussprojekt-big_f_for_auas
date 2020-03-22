package mops.domain.models.questionpoll;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.Getter;

import mops.domain.models.PollFields;
import mops.domain.models.PollLink;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.UserId;

@SuppressWarnings({"PMD.TooManyMethods"})
public class QuestionPollBuilder {

    private transient UserId creatorTarget;
    private transient PollLink linkTarget;
    private transient QuestionPollConfig configTarget;
    private transient QuestionPollMetaInf metaInfTarget;
    private final transient Set<QuestionPollEntry> entriesTarget = new HashSet<>();
    private final transient Set<UserId> participants = new HashSet<>();

    @Getter
    private static final EnumSet<PollFields> VALIDSET = EnumSet.of(
            PollFields.QUESTION_POLL_METAINF,
            PollFields.POLL_LINK,
            PollFields.QUESTION_POLL_CONFIG,
            PollFields.QUESTION_POLL_ENTRY,
            PollFields.CREATOR,
            PollFields.PARTICIPANTS
    );

    private static final int MIN_ENTRIES = 2;

    @Getter
    private Validation validationState;
    private final transient EnumSet<PollFields> validatedFields = EnumSet.noneOf(PollFields.class);
    private static final String INVALID_BUILDER_STATE = "COULD NOT CREATE QUESTION POLL";

    public QuestionPollBuilder() {
        this.validationState = Validation.noErrors();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> Optional<T> validationProcess(T validateAble, PollFields fields) {
        final Validation newValidation = validateAble.validate();
        validationState = validationState.removeErrors(fields);
        validationState = validationState.appendValidation(newValidation);
        return newValidation.hasNoErrors() ? Optional.of(validateAble) : Optional.empty();
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    private <T extends ValidateAble> void validationProcessAndValidationHandling(
        T validateAble, Consumer<T> applyToValidated, PollFields addToFieldsAfterSuccessfulValidation) {
        validationProcess(validateAble, addToFieldsAfterSuccessfulValidation).ifPresent(validated -> {
            applyToValidated.accept(validated);
            validatedFields.add(addToFieldsAfterSuccessfulValidation);
        });
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> Set<T> validateAllAndGetCorrect(Set<T> mappedOptions, PollFields fields) {
        return mappedOptions.stream()
            .map((T validateAble) -> validationProcess(validateAble, fields))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());
    }

    /**
     * Setzt den Header, wenn diese die Validierung durchläufen.
     *
     * @param questionPollMetaInf Der Header.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollMetaInf(QuestionPollMetaInf questionPollMetaInf) {
        validationProcessAndValidationHandling(
                questionPollMetaInf, header -> this.metaInfTarget = header, PollFields.QUESTION_POLL_METAINF
        );
        return this;
    }

    /**
     * Setzt den Ersteller, wenn dieser die Validierung durchläuft.
     *
     * @param creator der Ersteller einer Umfrage.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder creator(UserId creator) {
        validationProcessAndValidationHandling(
            creator, id -> this.creatorTarget = id, PollFields.CREATOR
        );
        return this;
    }

    /**
     * Setzt die Konfiguration, wenn diese die Validierung durchläuft.
     * @param questionPollConfig Konfiguration einer Umfrage.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollConfig(QuestionPollConfig questionPollConfig) {
        validationProcessAndValidationHandling(
            questionPollConfig, config -> this.configTarget = config, PollFields.QUESTION_POLL_CONFIG
        );
        return this;
    }

    /**
     * Fügt alle validierte Einträge der Eintragsliste hinzu.
     *
     * @param questionPollEntries Vorschläge die zu dieser Umfrage hinzugefügt werden sollen.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollEntries(Set<QuestionPollEntry> questionPollEntries) {
        this.entriesTarget.addAll(validateAllAndGetCorrect(questionPollEntries, PollFields.QUESTION_POLL_ENTRY));
        if (this.entriesTarget.size() >= MIN_ENTRIES) {
            validatedFields.add(PollFields.QUESTION_POLL_ENTRY);
        } else {
            validatedFields.remove(PollFields.QUESTION_POLL_ENTRY);
        }
        return this;
    }

    /**
     * Fügt alle validierte User der Teilnehmerliste hinzu.
     *
     * @param participants Teilnehmer die zu dieser Terminfindung hinzugefügt werden sollen.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder participants(Set<UserId> participants) {
        this.participants.addAll(validateAllAndGetCorrect(participants, PollFields.PARTICIPANTS));
        if (!this.participants.isEmpty()) {
            validatedFields.add(PollFields.PARTICIPANTS);
        }
        return this;
    }

    /**
     * Setzt den Link, wenn dieser die Validierung durchläuft.
     * @param pollLink
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder pollLink(PollLink pollLink) {
        validationProcessAndValidationHandling(
            pollLink, link -> this.linkTarget = link, PollFields.POLL_LINK
        );
        return this;
    }

    /**
     * Baut den QuestionPoll wenn alle Konstruktionsschritte zumindest
     * ein Mal korrekt die Validierung korrekt durchlaufen haben.
     *
     * @return der erstellte QuestionPoll
     * @throws IllegalStateException
     */
    public QuestionPoll build() {
        if (validationState.hasNoErrors() && validatedFields.equals(VALIDSET)) {
            return new QuestionPoll(
                    new PollRecordAndStatus(),
                    metaInfTarget,
                    creatorTarget,
                    configTarget,
                    entriesTarget,
                    participants,
                    new HashSet<QuestionPollBallot>(),
                    linkTarget
            );
        } else {
            EnumSet<FieldErrorNames> errorNames = validationState.getErrorMessages();
            for (FieldErrorNames error : errorNames) {
                System.out.println(error);
            }
            throw new IllegalStateException(INVALID_BUILDER_STATE);
        }
    }
}
