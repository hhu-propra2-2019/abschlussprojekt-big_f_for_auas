package mops.domain.models.QuestionPoll;

import lombok.Value;

@Value
public class QuestionPollEntry {
  private final String title;
  private long count;

}
