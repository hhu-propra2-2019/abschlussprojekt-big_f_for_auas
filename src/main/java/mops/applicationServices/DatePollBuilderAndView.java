package mops.applicationServices;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.DatePoll.DatePoll;
import mops.domain.models.DatePoll.DatePoll.DatePollBuilder;
import mops.domain.models.DatePoll.DatePollConfig;
import mops.domain.models.DatePoll.DatePollMetaInf;

import java.util.List;

@RequiredArgsConstructor
@Data
public class DatePollBuilderAndView {

    private final DatePollBuilder builder;
    private DatePollConfig config;
    private DatePollMetaInf metaInf;
    private List<DatePollOptionDto> datePollOptionDtos;

    DatePoll startBuildingDatePoll() {
        return this.builder.build();
    }
}
