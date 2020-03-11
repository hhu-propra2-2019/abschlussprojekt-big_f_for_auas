package mops.domain.models.QuestionPoll;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import mops.domain.models.User.UserId;

/**
 * Die Ballot (Deutsch: Wahlurne) speichert welche User für welche Einträge abstimmen.
 */
public class QuestionPollBallot {
  private final Map<UserId, List<QuestionPollEntry>> votes;

  public QuestionPollBallot() {
    this.votes = new HashMap<UserId, List<QuestionPollEntry>>();
  }
}
