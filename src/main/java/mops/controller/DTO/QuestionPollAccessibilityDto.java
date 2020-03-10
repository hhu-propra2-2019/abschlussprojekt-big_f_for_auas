package mops.controller.DTO;

import java.util.Set;
import lombok.Data;
import mops.domain.models.User.UserId;

@Data
public class QuestionPollAccessibilityDto {
  private boolean restrictedAccess;
  private Set<UserId> participants;
}
