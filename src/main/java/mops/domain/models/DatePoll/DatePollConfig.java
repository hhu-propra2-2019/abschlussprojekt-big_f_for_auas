package mops.domain.models.DatePoll;

import lombok.Getter;
import mops.controllers.DatePollConfigDto;

@Getter
class DatePollConfig {
    private boolean usersCanCreateOption;
    private boolean singleChoiceDatePoll;
    private boolean priorityChoice;
    private boolean datePollIsAnonymous;
    private boolean datePollIsPublic;

    DatePollConfig(final DatePollConfigDto datePollConfigDto) {

    }
}
