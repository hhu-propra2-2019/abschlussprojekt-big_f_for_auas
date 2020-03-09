package mops.domain.models.DatePoll;

import java.util.UUID;

public class DatePollId {

    private final UUID id;

    public DatePollId() {
        this.id = UUID.randomUUID();
    }

    public DatePollId(final UUID uuid) {
        this.id = uuid;
    }
}
