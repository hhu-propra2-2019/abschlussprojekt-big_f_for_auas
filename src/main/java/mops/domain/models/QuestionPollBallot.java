package mops.domain.models;

import java.util.Map;
import java.util.List;

public class QuestionPollBallot {
  private final Map<User, List<QuestionPollEntry>> votes;

  public QuestionPollBallot(Map<User, List<QuestionPollEntry>> map) {
    this.votes = map;
  }
}
