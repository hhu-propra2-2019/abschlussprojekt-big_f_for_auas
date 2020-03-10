package mops.domain.models.DatePoll;

import lombok.Value;

@Value
public class DatePollMetaInf {

    private String titel;
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
}
