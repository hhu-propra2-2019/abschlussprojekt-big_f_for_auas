package mops.domain.models.QuestionPoll;

import java.util.List;
import lombok.Value;
import mops.domain.models.User.UserId;

@Value
public class QuestionPollAccessibility {

  final boolean restrictedAccess;
  final List<UserId> participants;
}
