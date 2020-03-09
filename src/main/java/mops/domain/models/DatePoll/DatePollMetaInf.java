package mops.domain.models.DatePoll;

import lombok.Value;
import mops.controllers.DatePollMetaInfDto;

@Value
class DatePollMetaInf {

    private String titel;
    private DatePollDescription datePollDescription;
    private DatePollLocation datePollLocation;

    DatePollMetaInf(final DatePollMetaInfDto datePollMetaInfDto) {
    }
}
