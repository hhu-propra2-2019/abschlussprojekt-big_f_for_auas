package mops.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.User.UserId;

@Data
@AllArgsConstructor
public class QuestionPollBallotDto {
  private UserId user;
  private List<QuestionPollEntryDto> vote;
}
