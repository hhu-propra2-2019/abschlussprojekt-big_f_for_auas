package mops.controllers;

import lombok.Value;
import lombok.With;
//TODO how to init? -> Default values?
@Value
@With
public class DatePollConfigDto {

    private boolean usersCanCreateOption;
    private boolean singleChoiceDatePoll;
    private boolean priorityChoice;
    private boolean datePollIsAnonymous;
    private boolean datePollIsPublic;
}
