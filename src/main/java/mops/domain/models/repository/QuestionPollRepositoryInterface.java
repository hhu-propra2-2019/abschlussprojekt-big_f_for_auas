package mops.domain.models.repository;

import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollLink;

public interface QuestionPollRepositoryInterface {
  QuestionPollLink save(QuestionPoll questionPoll);
}
