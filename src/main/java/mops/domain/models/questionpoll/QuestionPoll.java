package mops.domain.models.questionpoll;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@Getter
public class QuestionPoll {

    private final QuestionPollMetaInf metaInf;
    private final UserId creator;
    private final QuestionPollConfig config;
    private final Set<QuestionPollEntry> entries;

    //TODO: remove accessibility
    //private final QuestionPollAccessibility accessor;
    private final Set<UserId> participants;

    private final Set<QuestionPollBallot> ballots;
    private final PollLink pollLink;

    public static QuestionPollBuilder builder() {
        return new QuestionPollBuilder();
    }

}
