package mops.domain.models.questionpoll;

import java.util.List;
import lombok.Getter;
import mops.domain.models.user.UserId;


public class QuestionPoll {

    private final QuestionPollLink questionPollLink;
    private List<QuestionPollEntry> entries;
    private final List<QuestionPollBallot> questionPollBallots;
    private final QuestionPollLifecycle lifecycle;

    @Getter private final UserId ownerId;
    @Getter private final QuestionPollHeader header;
    @Getter private final QuestionPollConfig config;
    @Getter private final QuestionPollAccessibility accessor;

    public QuestionPoll(UserId pOwnerId,
        QuestionPollHeader pHeader,
        QuestionPollConfig pConfig,
        QuestionPollAccessibility pAccessor,
        List<QuestionPollEntry> pEntries,
        List<QuestionPollBallot> pBallot,
        QuestionPollLifecycle pLifecycle) {
        this.questionPollLink = new QuestionPollLink();
        this.ownerId = pOwnerId;
        this.header = pHeader;
        this.config = pConfig;
        this.accessor = pAccessor;
        this.entries = pEntries;
        this.questionPollBallots = pBallot;
        this.lifecycle = pLifecycle;
    }

}
