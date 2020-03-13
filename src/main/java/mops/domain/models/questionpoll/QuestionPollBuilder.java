package mops.domain.models.questionpoll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import mops.domain.models.user.UserId;

public class QuestionPollBuilder {

    private UserId ownerTarget;
    private QuestionPollLink linkTarget;
    private QuestionPollConfig configTarget;
    private QuestionPollHeader headerTarget;
    private QuestionPollLifecycle lifecycleTarget;
    private List<QuestionPollEntry> entriesTarget;
    private Set<UserId> participants;
    private boolean accessRestriction;

    @Getter
    private Validation validationState;
    private EnumSet<QuestionPollFields> validatedFields = EnumSet.noneOf(QuestionPollFields.class);
    private final static String INVALID_BUILDER_STATE = "COULD NOT CREATE QUESTION POLL";

    public QuestionPollBuilder() {
        this.validationState = Validation.noErrors();
    }

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
        T validateAble, Consumer<T> applyToValidated, QuestionPollFields addToFieldsAfterSuccessfulValidation) {
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
     * Setzt den Header, wenn diese die Validierung durchläufen.
     *
     * @param questionPollHeader Der Header.
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder questionPollHeader(QuestionPollHeader questionPollHeader) {
        validationProcessAndValidationHandling(
            questionPollHeader, header -> this.headerTarget = header, QuestionPollFields.QUESTION_POLL_HEADER
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
            owner, id -> this.ownerTarget = id, QuestionPollFields.OWNER
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
            questionPollConfig, config -> this.configTarget = config, QuestionPollFields.QUESTION_POLL_CONFIG
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
        if (!questionPollEntries.isEmpty()) {
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
    public QuestionPollBuilder participants(List<UserId> participants) {
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
            questionPollLink, link -> this.linkTarget = link, QuestionPollFields.QUESTION_POLL_LINK
        );
        return this;
    }

    /**
     * Setzt den Lifecycle, wenn diese die Validierung durchläuft.
     * @param questionPollLifecycle
     * @return Referenz auf diesen QuestionPollBuilder.
     */
    public QuestionPollBuilder lifecycle(QuestionPollLifecycle questionPollLifecycle) {
        validationProcessAndValidationHandling(
            questionPollLifecycle, lifecycle -> this.lifecycleTarget = lifecycle, QuestionPollFields.QUESTION_POLL_LIFECYCLE
        );
        return this;
    }

    /**
     * Baut den QuestionPoll wenn alle Konstruktionsschritte zumindest ein Mal korrekt die Validierung korrekt durchlaufen haben.
     *
     * @return der erstellte QuestionPoll
     * @throws IllegalStateException
     */
    public QuestionPoll build() throws IllegalStateException {
        if (validationState.hasNoErrors() && EnumSet.allOf(QuestionPollFields.class).equals(validatedFields)) {
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
        QUESTION_POLL_CONFIG, QUESTION_POLL_LINK, QUESTION_POLL_LIFECYCLE, QUESTION_POLL_HEADER, QUESTION_POLL_ENTRY, OWNER, PARTICIPANTS
    }
}
