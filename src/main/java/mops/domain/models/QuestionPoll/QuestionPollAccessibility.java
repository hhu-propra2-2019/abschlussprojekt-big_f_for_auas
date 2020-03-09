package mops.domain.models.QuestionPoll;

import java.util.List;
import lombok.Value;
import mops.domain.models.User.UserId;

@Value
public class QuestionPollAccessibility {

  final boolean restrictedPoll;
  final List<UserId> participants;
}
