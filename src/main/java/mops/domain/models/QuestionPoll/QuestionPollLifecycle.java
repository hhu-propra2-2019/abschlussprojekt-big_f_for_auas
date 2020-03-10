package mops.domain.models.QuestionPoll;

import java.util.Date;
import lombok.Data;

@Data
public class QuestionPollLifecycle {
  final Date start;
  Date end;

}
