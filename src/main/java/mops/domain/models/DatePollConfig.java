package mops.domain.models;

import lombok.*;

@With
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DatePollConfig {
    private boolean usersCanCreateOption = false;
    private boolean singleChoiceDatePoll = true;
    private boolean priorityChoice = false;
    private boolean datePollIsAnonymous = false;
    private boolean datePollIsPublic = false;
}
