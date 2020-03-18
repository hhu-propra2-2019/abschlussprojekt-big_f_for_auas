package mops.domain.models;

public enum FieldErrorNames {

    DATE_POLL_IDENTIFIER(PollFields.DATE_POLL_LINK, "link.Invalid"),
    DATE_POLL_LOCATION_EMPTY(PollFields.DATE_POLL_META_INF, "metaInf.LocationEmpty"),

    DATE_POLL_IDENTIFIER_EMPTY(PollFields.DATE_POLL_LINK, "link.Invalid"),
    DATE_POLL_DESCRIPTION_TOO_LONG(PollFields.DATE_POLL_META_INF, "metaInf.DescriptionTooLong"),
    DATE_POLL_TITLE_TOO_LONG(PollFields.DATE_POLL_META_INF, "metaInf.TitleTooLong"),
    DATE_POLL_TITLE_EMPTY(PollFields.DATE_POLL_META_INF, "metaInf.TitleEmpty"),
    TIMESPAN_SWAPPED(PollFields.TIMESPAN, "timespanSwapped"),

    QUESTION_POLL_NOT_ENOUGH_PARTICIPANTS(PollFields.QUESTION_POLL_ACCESSIBILITY, ""),
    QUESTION_POLL_ENTRY_TITLE_IS_EMPTY(PollFields.QUESTION_POLL_ENTRY, ""),
    QUESTION_POLL_ENTRY_TITLE_IS_ONLY_WHITESPACE(PollFields.QUESTION_POLL_ENTRY, ""),
    QUESTION_POLL_ENTRY_TITLE_IS_TOO_LONG(PollFields.QUESTION_POLL_ENTRY, ""),
    QUESTION_POLL_ENTRY_TITLE_IS_TOO_SHORT(PollFields.QUESTION_POLL_ENTRY, ""),
    QUESTION_POLL_ENTRY_COUNT_IS_NEGATIVE(PollFields.QUESTION_POLL_ENTRY, ""),

    QUESTION_POLL_HEADER_TITLE_IS_NULL(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_HEADER_TITLE_IS_EMPTY(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_HEADER_TITLE_IS_TOO_LONG(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_HEADER_TITLE_IS_TOO_SHORT(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_QUESTION_IS_NULL(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_QUESTION_IS_EMPTY(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_QUESTION_IS_TOO_LONG(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_QUESTION_IS_TOO_SHORT(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_DESCRIPTION_IS_TOO_LONG(PollFields.QUESTION_POLL_HEADER, ""),
    QUESTION_POLL_DESCRIPTION_IS_TOO_SHORT(PollFields.QUESTION_POLL_HEADER, "");

    private final PollFields parent;
    private final String key;

    FieldErrorNames(PollFields parent, String key) {
        this.parent = parent;
        this.key = key;
    }

    public boolean isChildOf(PollFields field) {
        return this.parent == field;
    }

    public String getKey() {
        return key;
    }

}
