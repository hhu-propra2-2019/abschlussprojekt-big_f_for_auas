package mops.domain.models;

public enum FieldErrorNames {
    DATE_POLL_IDENTIFIER(PollFields.DATE_POLL_LINK),
    DATE_POLL_LOCATION(PollFields.DATE_POLL_META_INF),
    DATE_POLL_LIFECYCLE(PollFields.DATE_POLL_META_INF),
    DATE_POLL_TITLE_TOO_LONG(PollFields.DATE_POLL_META_INF),
    DATE_POLL_TITLE_EMPTY(PollFields.DATE_POLL_META_INF),
    TIMESPAN_SWAPPED(PollFields.TIMESPAN);


    private final PollFields parent;

    FieldErrorNames(PollFields parent) {
        this.parent = parent;
    }

    public boolean isChildOf(PollFields field) {
        return this.parent == field;
    }

}
