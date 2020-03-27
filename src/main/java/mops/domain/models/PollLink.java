package mops.domain.models;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

import java.util.UUID;
import mops.utils.UUIDEncoderUtil;

@SuppressFBWarnings(value = "DM_DEFAULT_ENCODING",
        justification = "Die Anwendung laeuft in einem Docker-Container und ist daher Platform-Unabhaengig.")
public class PollLink implements ValidateAble {
    @Getter
    private final transient String pollIdentifier;
    public PollLink() {
        final UUID uuid = UUID.randomUUID();
        this.pollIdentifier = UUIDEncoderUtil.encode(uuid);
    }
    /**
     * Gibt die UUID dekodiert zurueck.
     * @return UUID die dekodierte UUID.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    public String getLinkUUIDAsString() {
        return UUIDEncoderUtil.decode(this.pollIdentifier).toString();
    }

    public PollLink(UUID inputUuid) {
        this.pollIdentifier = UUIDEncoderUtil.encode(inputUuid);
    }

    // TODO: Stattdessen Formatter schreiben und registrieren
    public PollLink(String pollIdentifier) {
        this.pollIdentifier = pollIdentifier;
    }

    /**
     * ...
     * @return ...
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if ("".equals(pollIdentifier)) {
            validation = new Validation(FieldErrorNames.DATE_POLL_IDENTIFIER_EMPTY);
        }
        return validation;
    }
}
