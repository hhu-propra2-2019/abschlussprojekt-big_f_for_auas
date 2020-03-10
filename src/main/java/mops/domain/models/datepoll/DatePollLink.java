package mops.domain.models.datepoll;

public class DatePollLink {
    private final String hostname = "mops.cs.hhu.de/";
    private String datePollIdentifier;

    DatePollLink(final String newDatePollIdentifier) {
        this.datePollIdentifier = newDatePollIdentifier;
    }
}
