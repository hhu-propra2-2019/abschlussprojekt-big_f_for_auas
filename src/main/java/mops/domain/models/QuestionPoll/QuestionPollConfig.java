package mops.domain.models.QuestionPoll;

import lombok.Value;

/**
 * Speichert Einstellungen ob die QuestionPoll anonym ist (usingAlias = true) und ob es sich um eine
 *  Multi-Choice Umfrage geben.
 */
@Value
public class QuestionPollConfig {

  private final boolean usingAlias;
  private final boolean usingMultiChoice;
}
