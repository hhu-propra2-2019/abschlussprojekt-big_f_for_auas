package mops.domain.models;

import java.util.List;
import lombok.Builder;
import lombok.Singular;

@Builder
public class QuestionPoll {
  private User owner;

  private final QuestionPollHeader header;

  private boolean visibility;
  private boolean pollingMode;

  @Singular("questionPollEntry")
  private List<QuestionPollEntry> entries;

  @Singular("voter")
  private final List<User> allowedVoters;

  private final QuestionPollBallot ballot;
}
