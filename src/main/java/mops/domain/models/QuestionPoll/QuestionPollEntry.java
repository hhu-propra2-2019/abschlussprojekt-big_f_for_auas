package mops.domain.models.QuestionPoll;

import lombok.Value;

/**
 * Speichert eine Option über die in einem QuestionPoll abgestimmt werden kann und
 *  verfolgt wie oft für diese Option abgestimmt wurde.
 */
@Value
public class QuestionPollEntry {
  private final String title;
  private long count;

}
