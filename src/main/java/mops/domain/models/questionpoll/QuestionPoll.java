package mops.domain.models.questionpoll;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.PollLink;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@Getter
public class QuestionPoll {

    private final PollRecordAndStatus recordAndStatus;
    private final QuestionPollMetaInf metaInf;
    private final UserId creator;
    private final QuestionPollConfig config;
    private final Set<QuestionPollEntry> entries;

    private final Set<UserId> participants;

    private final Set<QuestionPollBallot> ballots;
    private final PollLink pollLink;

    public static QuestionPollBuilder builder() {
        return new QuestionPollBuilder();
    }

}
