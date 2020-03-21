package mops.domain.models.questionpoll;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.user.UserId;

@AllArgsConstructor
@Getter
public class QuestionPoll {

    private final PollLink questionPollLink;
    private final List<QuestionPollEntry> entries;
    private final List<QuestionPollBallot> questionPollBallots;
    private final Timespan lifecycle;

    private final UserId ownerId;
    private final QuestionPollHeader header;
    private final QuestionPollConfig config;
    private final QuestionPollAccessibility accessor;

    public static QuestionPollBuilder builder() {
        return new QuestionPollBuilder();
    }

}
