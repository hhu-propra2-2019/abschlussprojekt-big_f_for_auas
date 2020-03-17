package mops.domain.models;

public enum FieldErrorNames {
    DATE_POLL_IDENTIFIER(PollFields.DATE_POLL_LINK),
    DATE_POLL_LOCATION(PollFields.DATE_POLL_META_INF),
    DATE_POLL_LIFECYCLE(PollFields.DATE_POLL_META_INF),
    DATE_POLL_TITLE_TOO_LONG(PollFields.DATE_POLL_META_INF),
    DATE_POLL_TITLE_EMPTY(PollFields.DATE_POLL_META_INF),
    TIMESPAN_SWAPPED(PollFields.TIMESPAN),

    QUESTION_POLL_NOT_ENOUGH_PARTICIPANTS(PollFields.QUESTION_POLL_ACCESSIBILITY),
    QUESTION_POLL_ENTRY_TITLE_IS_EMPTY(PollFields.QUESTION_POLL_ENTRY),
    QUESTION_POLL_ENTRY_TITLE_IS_ONLY_WHITESPACE(PollFields.QUESTION_POLL_ENTRY),
    QUESTION_POLL_ENTRY_TITLE_IS_TOO_LONG(PollFields.QUESTION_POLL_ENTRY),
    QUESTION_POLL_ENTRY_TITLE_IS_TOO_SHORT(PollFields.QUESTION_POLL_ENTRY),
    QUESTION_POLL_ENTRY_COUNT_IS_NEGATIVE(PollFields.QUESTION_POLL_ENTRY);

    private final PollFields parent;

    FieldErrorNames(PollFields parent) {
        this.parent = parent;
    }

    public boolean isChildOf(PollFields field) {
        return this.parent == field;
    }

}
