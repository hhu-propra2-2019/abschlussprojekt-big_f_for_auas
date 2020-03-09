package mops.domain.models.QuestionPoll;

import java.util.List;
import mops.domain.models.User.User;

public class QuestionPoll {
  private User owner;
  private QuestionPollId questionPollId;

  private final QuestionPollHeader header;
  private final QuestionPollConfig config;
  private final QuestionPollAccessibility accessor;
  private List<QuestionPollEntry> entries;

  private final QuestionPollBallot ballot;
}
