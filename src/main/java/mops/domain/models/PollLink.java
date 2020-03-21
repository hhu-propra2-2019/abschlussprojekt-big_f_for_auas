package mops.domain.models.datepoll;

import java.nio.ByteBuffer;
import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

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
        final ByteBuffer buff = ByteBuffer.allocate(Long.BYTES * 2);
        buff.putLong(uuid.getLeastSignificantBits());
        buff.putLong(uuid.getMostSignificantBits());
        return encoder.encodeToString(buff.array()).substring(0, IDENTIFIER_LENGTH); //removes trailing ==
    }
}
