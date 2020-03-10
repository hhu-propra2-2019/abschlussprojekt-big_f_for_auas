package mops.domain.models.QuestionPoll;

import java.util.List;
import lombok.Getter;
import mops.domain.models.User.UserId;


public class QuestionPoll {

  private QuestionPollId questionPollId;
  private List<QuestionPollEntry> entries;
  private final QuestionPollBallot ballot;
  private final QuestionPollLifecycle lifecycle;
  private final Link link;

  @Getter private final UserId ownerId;
  @Getter private final QuestionPollHeader header;
  @Getter private final QuestionPollConfig config;
  @Getter private final QuestionPollAccessibility accessor;

  public QuestionPoll(UserId pOwnerId,
                      QuestionPollHeader pHeader,
                      QuestionPollConfig pConfig,
                      QuestionPollAccessibility pAccessor,
                      List<QuestionPollEntry> pEntries,
                      QuestionPollBallot pBallot,
                      QuestionPollLifecycle pLifecycle,
                      Link pLink) {

    this.questionPollId = new QuestionPollId();
    this.ownerId = pOwnerId;
    this.header = pHeader;
    this.config = pConfig;
    this.accessor = pAccessor;
    this.entries = pEntries;
    this.ballot = pBallot;
    this.lifecycle = pLifecycle;
    this.link = pLink;
  }

}
