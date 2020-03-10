package mops.controller.DTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.User.UserId;

@Data
@AllArgsConstructor
public class QuestionPollAccessibilityDto {
  private boolean restrictedAccess;
  private Set<UserId> participants;
}
