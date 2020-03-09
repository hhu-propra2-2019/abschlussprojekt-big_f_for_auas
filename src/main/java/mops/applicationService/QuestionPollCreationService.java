package mops.applicationService;

import mops.domain.models.QuestionPoll.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPoll.QuestionPollBuilder;
import mops.domain.models.QuestionPoll.QuestionPollBallot;
import mops.domain.models.QuestionPoll.QuestionPollEntry;
import mops.domain.models.QuestionPoll.QuestionPollHeader;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.Repository.QuestionPollRepositoryInterface;
import mops.domain.models.Repository.UserRepositoryInterface;
import mops.domain.models.User.User;


public class QuestionPollCreationService {

  QuestionPollRepositoryInterface qpRepo;
  UserRepositoryInterface userRepo;

  public QuestionPollBuilder startQuestionPoll(final UserId userId) {
    User user = userRepo.getById(userId);
    return QuestionPoll.builder().owner(user);
  }

  public QuestionPollBuilder addHeader(QuestionPollBuilder qpBuilder, final String title, final String description) {
    QuestionPollHeader header = new QuestionPollHeader(title, description);
    return qpBuilder.header(header);
  }

  public QuestionPollBuilder addEntry(final QuestionPollBuilder qpBuilder, final QuestionPollEntry questionPollEntry) {
    return qpBuilder.questionPollEntry(questionPollEntry);
  }

  public QuestionPollBuilder setPollingMode(final QuestionPollBuilder qpBuilder, final boolean single) {
    return qpBuilder.pollingMode(single);
  }

  public QuestionPollBuilder setVisibility(final QuestionPollBuilder qpBuilder, final boolean anonymous) {
    return qpBuilder.visibility(anonymous);
  }

  public QuestionPollBuilder setBallot(final QuestionPollBuilder qpBuilder, final QuestionPollBallot ballot) {
    return qpBuilder.ballot(ballot);
  }

}