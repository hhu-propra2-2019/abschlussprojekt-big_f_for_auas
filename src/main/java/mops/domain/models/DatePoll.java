package mops.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DatePoll {
    private DatePollMetaInf datePollMetaInf;
    private DatePollID datePollID;
    private User creator;
    private DatePollConfig datePollConfig;
    private List<DatePollOption> datePollOptionList;
    private List<User> participants;

    public void setPublicationTypeToPublic(boolean isPublic) {
        //
        return;
    }

    public void setParticipantsToList(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User nextParticipant) {
        this.participants.add(nextParticipant);
    }
}
