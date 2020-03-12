package mops.domain.models.questionpoll;

import java.util.Collections;
import java.util.List;
import lombok.Value;
import mops.domain.models.user.UserId;

/**
 * Die Ballot (Deutsch: Wahlzettel) speichert welche User für welche Einträge abstimmen.
 */
@Value
public class QuestionPollBallot {
  private final UserId user;
  private final List<QuestionPollEntry> selectedEntries;

    /**
     * Konstruktor.
     * @param qpUserId
     * @param selectedEntries
     */
  public QuestionPollBallot(UserId qpUserId, List<QuestionPollEntry> selectedEntries) {
    this.user = qpUserId;
    this.selectedEntries = Collections.unmodifiableList(selectedEntries);
  }
}
