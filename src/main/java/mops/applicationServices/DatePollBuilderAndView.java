package mops.applicationServices;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import mops.controllers.DatePollOptionDto;
import mops.domain.models.DatePoll.DatePoll;
import mops.domain.models.DatePoll.DatePoll.DatePollBuilder;
import mops.domain.models.DatePoll.DatePollConfig;
import mops.domain.models.DatePoll.DatePollMetaInf;
import mops.domain.models.User.UserId;

import java.util.List;

@RequiredArgsConstructor
@Data
public class DatePollBuilderAndView {

    private final DatePollBuilder builder;
    private DatePollConfig config;
    private DatePollMetaInf metaInf;
    private List<DatePollOptionDto> datePollOptionDtos;
    private List<UserId> participantsIdList;

    DatePoll startBuildingDatePoll() {
        return this.builder.build();
    }

    public void addSingleParticipant(final UserId nextParticipantId) {
        participantsIdList.add(nextParticipantId);
    }
}
