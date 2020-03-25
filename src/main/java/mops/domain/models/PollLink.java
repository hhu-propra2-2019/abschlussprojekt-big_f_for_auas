package mops.domain.models;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

import java.util.Base64;
import java.util.UUID;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@SuppressFBWarnings(value = "DM_DEFAULT_ENCODING",
        justification = "Die Anwendung laeuft in einem Docker-Container und ist daher Platform-Unabhaengig.")
public class PollLink implements ValidateAble {
    private static Encoder encoder = Base64.getUrlEncoder();
    private static Decoder decoder = Base64.getUrlDecoder();
    @Getter
    private final transient String pollIdentifier;
    public PollLink() {
        final UUID uuid = UUID.randomUUID();
        this.pollIdentifier = encodeUUIDtoBase64(uuid);
    }
    /**
     * Gibt die UUID dekodiert zurueck.
     * @return UUID die dekodierte UUID.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public String getLinkUUIDAsString() {
        return decodeBase64toUUID(this.pollIdentifier);
    }
    public PollLink(UUID inputUuid) {
        this.pollIdentifier = encodeUUIDtoBase64(inputUuid);
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

    @SuppressWarnings("PMD.LawOfDemeter")
    private static String encodeUUIDtoBase64(final UUID uuid) {
        return encoder.encodeToString(uuid.toString().getBytes());
    }

    private static String decodeBase64toUUID(final String uuid) {
        final byte[] decode = decoder.decode(uuid);
        return new String(decode);
    }
}
