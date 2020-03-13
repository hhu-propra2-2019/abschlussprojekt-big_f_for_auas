package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

/**
 * Speichert eine Option über die in einem QuestionPoll abgestimmt werden kann und
 *  verfolgt wie oft für diese Option abgestimmt wurde.
 */

@AllArgsConstructor
public class QuestionPollEntry implements ValidateAble {
  private final String title;
  private long count;

  @Override
  public Validation validate() {
    return null;
  }
}
