package mops.applicationServices;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mops.controllers.DatePollConfigDto;
import mops.controllers.DatePollMetaInfDto;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.DatePoll.DatePoll.DatePollBuilder;

import java.util.List;

@RequiredArgsConstructor
@Data
public class DatePollBuilderAndView {

    private final DatePollBuilder builder;
    private DatePollConfigDto configDto;
    private DatePollMetaInfDto metaInfDto;
    private List<DatePollOptionDto> datePollOptionDtos;

}
