package mops.domain.models.QuestionPoll;

import java.util.Map;
import java.util.List;
import mops.domain.models.User.User;

public class QuestionPollBallot {
  private final Map<User, List<QuestionPollEntry>> votes;

  public QuestionPollBallot(Map<User, List<QuestionPollEntry>> map) {
    this.votes = map;
  }
}