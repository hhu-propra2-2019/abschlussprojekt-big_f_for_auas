package mops.domain.models.DatePoll;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class DatePollId {

    private final UUID id;

    public DatePollId() {
        this.id = UUID.randomUUID();
    }

    public DatePollId(final UUID uuid) {
        this.id = uuid;
    }
}
