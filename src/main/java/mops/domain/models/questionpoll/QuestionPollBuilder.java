package mops.domain.models.questionpoll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.Getter;
import mops.domain.models.Timespan;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

@SuppressWarnings({"PMD.TooManyMethods"})
public class QuestionPollBuilder {

    private transient UserId ownerTarget;
    private transient QuestionPollLink linkTarget;
    private transient QuestionPollConfig configTarget;
    private transient QuestionPollHeader headerTarget;
    private transient Timespan lifecycleTarget;
    private final transient List<QuestionPollEntry> entriesTarget = new ArrayList<>();
    private final transient Set<UserId> participants = new HashSet<>();
    private transient boolean accessRestriction;

    @Getter
    private final Validation validationState;
    private final transient EnumSet<QuestionPollFields> validatedFields = EnumSet.noneOf(QuestionPollFields.class);
    private static final String INVALID_BUILDER_STATE = "COULD NOT CREATE QUESTION POLL";

    public QuestionPollBuilder() {
        this.validationState = Validation.noErrors();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> Optional<T> validationProcess(T validateAble) {
        final Validation newValidation = validateAble.validate();
        validationState.appendValidation(newValidation);
        return newValidation.hasNoErrors() ? Optional.of(validateAble) : Optional.empty();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> void validationProcessAndValidationHandling(
        T validateAble, Consumer<T> applyToValidated, QuestionPollFields addToFieldsAfterSuccessfulValidation) {
        validationProcess(validateAble).ifPresent(validated -> {
            applyToValidated.accept(validated);
            validatedFields.add(addToFieldsAfterSuccessfulValidation);
        });
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private <T extends ValidateAble> List<T> validateAllAndGetCorrect(List<T> mappedOptions) {
        return mappedOptions.stream()
            .map(this::validationProcess)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    /**
     * Setzt den Header, wenn diese die Validierung durchläufen.
     *
     * @param questionPollHeader Der Header.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollHeader(QuestionPollHeader questionPollHeader) {
        validationProcessAndValidationHandling(
            questionPollHeader, header -> this.headerTarget = header,
            QuestionPollFields.QUESTION_POLL_HEADER
        );
        return this;
    }

    /**
     * Setzt den Ersteller, wenn dieser die Validierung durchläuft.
     *
     * @param owner der Ersteller einer Umfrage.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder owner(UserId owner) {
        validationProcessAndValidationHandling(
            owner, id -> this.ownerTarget = id,
            QuestionPollFields.OWNER
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
            questionPollConfig, config -> this.configTarget = config,
            QuestionPollFields.QUESTION_POLL_CONFIG
        );
        return this;
    }

    /**
     * Fügt alle validierte Einträge der Eintragsliste hinzu.
     *
     * @param questionPollEntries Vorschläge die zu dieser Umfrage hinzugefügt werden sollen.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollEntries(List<QuestionPollEntry> questionPollEntries) {
        this.entriesTarget.addAll(validateAllAndGetCorrect(questionPollEntries));
        if (this.entriesTarget.size() >= 2) {
            validatedFields.add(QuestionPollFields.QUESTION_POLL_ENTRY);
        }
        return this;
    }

    /**
     * Fügt alle validierte User der Teilnehmerliste hinzu.
     *
     * @param participants Teilnehmer die zu dieser Terminfindung hinzugefügt werden sollen.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollParticipants(List<UserId> participants) {
        this.participants.addAll(validateAllAndGetCorrect(participants));
        if (!this.participants.isEmpty()) {
            validatedFields.add(QuestionPollFields.PARTICIPANTS);
        }
        return this;
    }

    /**
     * Setzt ob die Umfrage offen oder geschlossen ist.
     * Da es sich hier um einen Primitive handelt findet keine Validierung statt.
     * @param restrictAccess boolean
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder restrictAccess(final boolean restrictAccess) {
        this.accessRestriction = restrictAccess;
        return this;
    }

    /**
     * Setzt den Link, wenn dieser die Validierung durchläuft.
     * @param questionPollLink
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollLink(QuestionPollLink questionPollLink) {
        validationProcessAndValidationHandling(
            questionPollLink, link -> this.linkTarget = link,
            QuestionPollFields.QUESTION_POLL_LINK
        );
        return this;
    }

    /**
     * Setzt den Lifecycle, wenn diese die Validierung durchläuft.
     * @param questionPollLifecycle
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollLifecycle(Timespan questionPollLifecycle) {
        validationProcessAndValidationHandling(
            questionPollLifecycle, lifecycle -> this.lifecycleTarget = lifecycle,
            QuestionPollFields.QUESTION_POLL_LIFECYCLE
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
        if (validationState.hasNoErrors() && validatedFields.equals(EnumSet.allOf(QuestionPollFields.class))) {
            return new QuestionPoll(
                linkTarget,
                Collections.unmodifiableList(entriesTarget),
                new ArrayList<QuestionPollBallot>(),
                lifecycleTarget,
                ownerTarget,
                headerTarget,
                configTarget,
                new QuestionPollAccessibility(accessRestriction, participants)
            );
        } else {
            throw new IllegalStateException(INVALID_BUILDER_STATE);
        }
    }

    private enum QuestionPollFields {
        QUESTION_POLL_CONFIG, QUESTION_POLL_LINK, QUESTION_POLL_LIFECYCLE,
        QUESTION_POLL_HEADER, QUESTION_POLL_ENTRY, OWNER, PARTICIPANTS
    }
}
