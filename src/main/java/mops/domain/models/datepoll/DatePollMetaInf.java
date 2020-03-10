package mops.domain.models.datepoll;

import lombok.Value;

@Value
public class DatePollMetaInf {

    private String titel;
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;
    private DatePollLifeCycle datePollLifeCycle;
}
