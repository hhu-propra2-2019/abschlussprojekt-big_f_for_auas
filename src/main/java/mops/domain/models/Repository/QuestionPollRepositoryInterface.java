package mops.domain.models.Repository;

import mops.domain.models.QuestionPoll.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollLink;

public interface QuestionPollRepositoryInterface {
  QuestionPollLink save(QuestionPoll questionPoll);
}
