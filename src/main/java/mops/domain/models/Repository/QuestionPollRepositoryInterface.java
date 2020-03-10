package mops.domain.models.Repository;

import mops.domain.models.QuestionPoll.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.QuestionPoll.QuestionPollLink;

public interface QuestionPollRepositoryInterface {
  QuestionPollId save(QuestionPoll questionPoll);
  QuestionPollLink getUrl(QuestionPollId questionPollId);
}
