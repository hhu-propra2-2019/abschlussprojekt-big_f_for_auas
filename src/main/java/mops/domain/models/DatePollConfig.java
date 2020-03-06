package mops.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.With;

@With
@RequiredArgsConstructor
@AllArgsConstructor
public class DatePollConfig {
    private boolean usersCanCreateOption = false;
    private boolean singleChoiceDatePoll = true;
    private boolean priorityChoice = false;
    private boolean datePollIsAnonymous = false;
    private boolean datePollIsPublic = false;
}
