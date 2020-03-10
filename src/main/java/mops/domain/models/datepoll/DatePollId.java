package mops.domain.models.datepoll;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class DatePollId {

    private final UUID id;

    /**
     * Erzeugt einen neuen Identifier.
     */
    public DatePollId() {
        this.id = UUID.randomUUID();
    }

    /**
     * Lade einen bereits existierenden Identifier.
     *
     * @param uuid bereitsexistierender Identifier.
     */
    public DatePollId(final UUID uuid) {
        this.id = uuid;
    }
}
