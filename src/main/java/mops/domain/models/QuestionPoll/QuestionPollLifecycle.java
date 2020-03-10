package mops.domain.models.QuestionPoll;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionPollLifecycle {
  final Date start;
  Date end;

}
