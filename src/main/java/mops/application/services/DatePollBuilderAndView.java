package mops.application.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.UserId;

import java.util.List;

@RequiredArgsConstructor
@Data
public class DatePollBuilderAndView {

    private final DatePollBuilder builder;
    private DatePollConfig config;
    private DatePollMetaInf metaInf;
    private List<DatePollOptionDto> datePollOptionDtos;
    private List<UserId> participantsIdList;

    /**
     * Methode dient zur erstellung des DatePoll Objektes.
     * @return DatePoll Objekt, welches den derzeitigen Status des Builders implementiert
     */
    public DatePoll startBuildingDatePoll() {
        return this.builder.build();
    }

    /**
     * Einzelne User zur Terminfindung hinzufuegen.
     * @param nextParticipantId UserId des Users, der hinzugefuegt werden soll
     */
    public void addSingleParticipant(final UserId nextParticipantId) {
        participantsIdList.add(nextParticipantId);
    }
}
