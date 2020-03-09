package mops.controllers;

import lombok.Value;
import lombok.With;

@Value
@With
public class DatePollConfigDto {

    private boolean usersCanCreateOption;
    private boolean singleChoiceDatePoll;
    private boolean priorityChoice;
    private boolean datePollIsAnonymous;
    private boolean datePollIsPublic;
}
