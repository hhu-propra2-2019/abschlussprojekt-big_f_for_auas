package mops.domain.models.questionpoll;

import java.util.UUID;
import lombok.Getter;

/**
 * Speichert die Url der QuestionPoll.
 */
public class QuestionPollLink {
  @Getter
  private final UUID questionLinkId;

  public QuestionPollLink() {
    this.questionLinkId = UUID.randomUUID();
  }
}
