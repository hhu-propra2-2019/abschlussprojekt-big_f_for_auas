package mops.domain.models;

public enum FieldErrorNames {
    DATE_POLL_IDENTIFIER(DatePollFields.DATE_POLL_LINK, "link.Invalid"),
    DATE_POLL_LOCATION_EMPTY(DatePollFields.DATE_POLL_META_INF, "metaInf.LocationEmpty"),
    DATE_POLL_TITLE_TOO_LONG(DatePollFields.DATE_POLL_META_INF, "metaInf.TitleTooLong"),
    DATE_POLL_TITLE_EMPTY(DatePollFields.DATE_POLL_META_INF, "metaInf.TitleEmpty"),
    TIMESPAN_SWAPPED(DatePollFields.TIMESPAN, "timespanSwapped");


    private final DatePollFields parent;
    private final String key;

    FieldErrorNames(DatePollFields parent, String key) {
        this.parent = parent;
        this.key = key;
    }

    public boolean isChildOf(DatePollFields field) {
        return this.parent == field;
    }

    public String getKey() {
        return key;
    }

}
