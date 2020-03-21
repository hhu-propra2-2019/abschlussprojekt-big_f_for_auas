package mops.domain.models.repository;

import mops.domain.models.PollLink;
import mops.domain.models.questionpoll.QuestionPoll;

public interface QuestionPollRepositoryInterface {
  PollLink save(QuestionPoll questionPoll);
}
