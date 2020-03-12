package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Speichert eine Option über die in einem QuestionPoll abgestimmt werden kann und
 *  verfolgt wie oft für diese Option abgestimmt wurde.
 */

@AllArgsConstructor
public class QuestionPollEntry {
  private final String title;
  private long count;

}
