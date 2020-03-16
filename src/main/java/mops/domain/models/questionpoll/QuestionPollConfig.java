package mops.domain.models.questionpoll;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

/**
 * Speichert Einstellungen ob die QuestionPoll anonym ist (usingAlias = true) und ob es sich um eine
 *  Multi-Choice Umfrage geben.
 */
@Value
@With
@AllArgsConstructor
public class QuestionPollConfig implements ValidateAble {

  private boolean usingAlias;
  private boolean usingMultiChoice;

  public QuestionPollConfig() {
    this.usingAlias = false;
    this.usingMultiChoice = false;
  }

  /**
   * validate Methode für den Builder.
   * @return Validation
   */
  @Override
  public Validation validate() {
    return Validation.noErrors();
  }
}
