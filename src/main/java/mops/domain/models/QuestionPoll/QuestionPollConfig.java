package mops.domain.models.QuestionPoll;

import lombok.Value;

@Value
public class QuestionPollConfig {

  private final boolean usingAlias;
  private final boolean usingMultiChoice;
}
