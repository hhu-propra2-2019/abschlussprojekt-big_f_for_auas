package mops.domain.models.questionpoll;

import lombok.Value;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

/**
 * Speichert Einstellungen ob die QuestionPoll anonym ist (usingAlias = true) und ob es sich um eine
 *  Multi-Choice Umfrage geben.
 */
@Value
public class QuestionPollConfig implements ValidateAble {

  private final boolean usingAlias;
  private final boolean usingMultiChoice;

  /**
   * validate Methode für den Builder.
   * @return
   */
  @Override
  public Validation validate() {
    return Validation.noErrors();
  }
}
