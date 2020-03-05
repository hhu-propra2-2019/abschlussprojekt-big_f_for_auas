package mops.applicationService;

import mops.domain.models.Choice;
import mops.domain.models.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollBuilder;
    
public class QuestionPollService {

  QuestionPollRepository qpRepo;
  UserRepository userRepo;

  public QuestionPollBuilder startQuestionPoll(final String title, final String description, final UserId userId) {
    QuestionPollBuilder qpBuilder = QuestionPoll.builder();
    qpBuilder.title(title);
    qpBuilder.description(description);
    User user = userRepo.getById(userId);
    qpBuilder.owner(User);
    return qpBuilder;
  }

  public void addChoice(final QuestionPollBuilder qpBuilder, final Choice choice) {
    qpBuilder.choice(choice);
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
