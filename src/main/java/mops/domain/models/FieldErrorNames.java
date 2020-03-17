package mops.domain.models;

public enum FieldErrorNames {
    DATE_POLL_IDENTIFIER(DatePollFields.DATE_POLL_LINK),
    DATE_POLL_LOCATION(DatePollFields.DATE_POLL_META_INF),
    DATE_POLL_LIFECYCLE(DatePollFields.DATE_POLL_META_INF),
    DATE_POLL_TITLE_TOO_LONG(DatePollFields.DATE_POLL_META_INF),
    DATE_POLL_TITLE_EMPTY(DatePollFields.DATE_POLL_META_INF),
    TIMESPAN_SWAPPED(DatePollFields.TIMESPAN);


    private final DatePollFields parent;

    FieldErrorNames(DatePollFields parent) {
        this.parent = parent;
    }

    public boolean isChildOf(DatePollFields field) {
        return this.parent == field;
    }

}
