package mops.domain.models.QuestionPoll;

import java.util.List;

import io.micrometer.shaded.org.pcollections.PBag;
import mops.domain.models.User.UserId;


public class QuestionPoll {
  private UserId ownerId;
  private QuestionPollId questionPollId;

  private final QuestionPollHeader header;
  private final QuestionPollConfig config;
  private final QuestionPollAccessibility accessor;
  private List<QuestionPollEntry> entries;

  private final QuestionPollBallot ballot;

  public QuestionPoll(UserId pOwnerId,
                      QuestionPollHeader pHeader,
                      QuestionPollConfig pConfig,
                      QuestionPollAccessibility pAccessor,
                      List<QuestionPollEntry> pEntries,
                      QuestionPollBallot pBallot) {

    this.questionPollId = new QuestionPollId();
    this.ownerId = pOwnerId;
    this.header = pHeader;
    this.config = pConfig;
    this.accessor = pAccessor;
    this.entries = pEntries;
    this.ballot = pBallot;

  }

}
