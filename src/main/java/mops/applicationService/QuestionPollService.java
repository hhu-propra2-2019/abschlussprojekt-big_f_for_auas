package mops.applicationService;

import mops.domain.models.QuestionPollEntry;
import mops.domain.models.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollBuilder;
import mops.domain.models.User;

public class QuestionPollService {

  QuestionPollRepository qpRepo;
  UserRepository userRepo;

  public QuestionPollBuilder startQuestionPoll(final String title, final String description, final UserId userId) {
    User user = userRepo.getById(userId);
    return QuestionPoll.builder().title(title).description(description).owner(User);
  }

  public void addEntry(final QuestionPollBuilder qpBuilder, final QuestionPollEntry questionPollEntry) {
    qpBuilder.choice(questionPollEntry);
    return;
  }

  public void setPollingMode(final QuestionPollBuilder qpFactory, final boolean single) {
    qpFactory.setPollingMode(single);
    return;
  }

  public void setVisiblity(final QuestionPollFactory qpFactory, final boolean anonymous) {
    qpFactory.setVisibility(anonymous);
    return;
  }

  public String generateLink(final QuestionPollFactory qpFactory) {
    questionPollId = qpRepo.save(qpFactory.build());
    String url = qpRepo.getUrl(QuestionPollId);
    return url;
  }
}
