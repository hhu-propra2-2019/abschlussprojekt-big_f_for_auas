package mops.domain.models;

import java.nio.ByteBuffer;
import lombok.Value;

import java.util.UUID;
import java.util.Base64;
import java.util.Base64.Encoder;

@Value
public class PollLink implements ValidateAble {

    private static final int IDENTIFIER_LENGTH = 22;
    private static Encoder encoder = Base64.getUrlEncoder();

    private String pollIdentifier;

    public PollLink() {
        final UUID uuid = UUID.randomUUID();
        this.pollIdentifier = encodeUUIDtoBase64(uuid);
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
}
