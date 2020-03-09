package mops.domain.models.DatePoll;

import lombok.EqualsAndHashCode;
import lombok.Value;
import mops.controllers.DatePollMetaInfDto;

@Value
@EqualsAndHashCode
class DatePollMetaInf {

    private String titel;
    private Beschreibung beschreibung;
    private Ort ort;

    DatePollMetaInf(final DatePollMetaInfDto datePollMetaInfDto) {
    }
}
