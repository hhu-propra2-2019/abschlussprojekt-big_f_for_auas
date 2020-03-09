package mops.domain.models.QuestionPoll;

import static lombok.AccessLevel.PACKAGE;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(PACKAGE)
@RequiredArgsConstructor(access = PACKAGE)
public class QuestionPollConfig {

  private final boolean usingUserAlias;
  private final boolean usingMultiChoice;
}
