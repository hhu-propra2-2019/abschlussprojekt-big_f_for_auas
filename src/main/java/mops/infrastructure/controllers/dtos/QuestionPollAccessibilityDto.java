package mops.infrastructure.controllers.dtos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.user.UserId;

@Data
@AllArgsConstructor
public class QuestionPollAccessibilityDto {
  private boolean restrictedAccess;
  private Set<UserId> participants;
}
