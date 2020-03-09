package mops.controller.DTO;

import java.util.List;
import lombok.Data;
import mops.domain.models.User.UserId;

@Data
public class QuestionPollAccessibilityDto {
  public boolean restrictedAccesss;
  public List<UserId> participants;
}
