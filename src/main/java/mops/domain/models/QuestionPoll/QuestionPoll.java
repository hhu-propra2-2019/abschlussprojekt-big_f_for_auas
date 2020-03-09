package mops.domain.models.QuestionPoll;

import java.util.List;
import lombok.Builder;
import lombok.Singular;
import mops.domain.models.User.User;

@Builder
public class QuestionPoll {
  private User owner;
  private QuestionPollId questionPollId;

  private final QuestionPollHeader header;
  private final QuestionPollConfig config;

  @Singular("questionPollEntry")
  private List<QuestionPollEntry> entries;

  @Singular("voter")
  private final List<User> allowedVoters;

  private final QuestionPollBallot ballot;
}
