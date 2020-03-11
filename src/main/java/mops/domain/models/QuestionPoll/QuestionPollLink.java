package mops.domain.models.QuestionPoll;

import java.util.UUID;
import lombok.Getter;

/**
 * Speichert die Url der QuestionPoll.
 */
public class QuestionPollLink {
  @Getter
  private final UUID questionLinkId questionLinkId;

  public questionLinkId() {
    this.questionLinkId = UUID.randomUUID();
  }
}
