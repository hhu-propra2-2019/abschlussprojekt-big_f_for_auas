package mops.domain.models.QuestionPoll;

import java.util.UUID;

public class QuestionPollId {

  final UUID questionPollId;
  QuestionPollId() {
    this.questionPollId = UUID.randomUUID();
  }

}
