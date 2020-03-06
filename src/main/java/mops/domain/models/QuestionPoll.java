package mops.domain.models;

import lombok.Builder;
import lombok.Singular;
import org.springframework.lang.Nullable;

@Builder
public class QuestionPoll {
  private final String title;
  private final User owner;

  @Nullable
  private final String description;

  private boolean visibility;
  private boolean pollingMode;

  @Singular("choice")
  private List<QuestionPollEntry> choices;
}
