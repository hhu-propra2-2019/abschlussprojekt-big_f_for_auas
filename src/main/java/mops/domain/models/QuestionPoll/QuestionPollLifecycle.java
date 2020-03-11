package mops.domain.models.QuestionPoll;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Speichert wann eine QuestionPoll über welchen Zeitraum die QuestionPoll offen für Abstimmungen ist.
 */
@Data
@AllArgsConstructor
public class QuestionPollLifecycle {
  private final Date start;
  private Date end;

}
