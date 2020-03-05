package mops.domain.models;

import lombok.Builder;

import java.util.List;

@Builder
public class DatePoll {
    private DatePollMetaInf datePollMetaInf;
    private DatePollID datePollID;
    private User creator;
    private boolean usersCanCreateOption;
    private boolean singleChoiceDatePoll;
    private boolean priorityChoice;
    private boolean datePollIsAnonymous;
    private List<DatePollOption> datePollOptionList;
}
