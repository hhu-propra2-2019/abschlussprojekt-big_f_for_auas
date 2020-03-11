package mops.controller.DTO;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.QuestionPoll.QuestionPollEntry;
import mops.domain.models.User.User;
import mops.domain.models.User.UserId;

@Data
@AllArgsConstructor
public class QuestionPollBallotDto {
  private UserId user;
  private List<QuestionPollEntryDto> vote;
}
