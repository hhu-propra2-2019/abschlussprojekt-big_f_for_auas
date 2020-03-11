package mops.application.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mops.controllers.Dto.DatePollOptionDto;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;

import java.util.List;

@RequiredArgsConstructor
@Data
public class DatePollBuilderAndView {

    private final DatePollBuilder builder;
    private DatePollConfig config;
    private DatePollMetaInf metaInf;
    private List<DatePollOptionDto> datePollOptionDtos;
    private Validation validation;

    /**
     * Methode dient zur erstellung des DatePoll Objektes.
     *
     * @return DatePoll Objekt, welches den derzeitigen Status des Builders implementiert
     */
    public DatePoll startBuildingDatePoll() {
        return this.builder.build();
    }

}
