package mops.applicationService;

import mops.domain.models.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollBuilder;
import mops.domain.models.QuestionPollBallot;
import mops.domain.models.QuestionPollEntry;
import mops.domain.models.QuestionPollHeader;
import mops.domain.models.QuestionPollId;
import mops.domain.models.User;
import mops.domain.models.UserId;

public class QuestionPollService {

  QuestionPollRepository qpRepo;
  UserRepository userRepo;

  public QuestionPollBuilder startQuestionPoll(final String title, final String description, final UserId userId) {
    QuestionPollHeader qpHeader = new QuestionPollHeader(title, description);
    User user = userRepo.getById(userId);
    return QuestionPoll.builder().header(qpHeader).owner(User);
  }

  public QuestionPollBuilder addEntry(final QuestionPollBuilder qpBuilder, final QuestionPollEntry questionPollEntry) {
    return qpBuilder.questionPollEntry(questionPollEntry);
  }

  public QuestionPollBuilder setPollingMode(final QuestionPollBuilder qpBuilder, final boolean single) {
    return qpBuilder.pollingMode(single);
  }

  public QuestionPollBuilder setVisiblity(final QuestionPollBuilder qpBuilder, final boolean anonymous) {
    return qpBuilder.visibility(anonymous);
  }

  public QuestionPollBuilder setBallot(final QuestionPollBuilder qpBuilder, final QuestionPollBallot ballot) {
    return qpBuilder.ballot(ballot);
  }

  public String generateLink(final QuestionPollBuilder qpBuilder) {
    QuestionPollId qpId = qpRepo.save(qpBuilder.build());
    String url = qpRepo.getUrl(qpId);
    return url;
  }
}
