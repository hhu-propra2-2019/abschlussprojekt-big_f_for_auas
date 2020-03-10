package mops.applicationService;


import mops.domain.models.QuestionPoll.QuestionPollFactory;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.QuestionPoll.QuestionPollLink;
import mops.domain.models.Repository.QuestionPollRepositoryInterface;
import mops.domain.models.User.UserId;
import org.springframework.stereotype.Service;

@Service
public class QuestionPollSyndicationService {

    QuestionPollRepositoryInterface questionPollRepo;

    public QuestionPollLink generateLink(final QuestionPollFactory factory) {
        QuestionPollId questionPollId = questionPollRepo.save(factory.build());
        QuestionPollLink link = questionPollRepo.getUrl(questionPollId);
        return link;
    }

    public void addUserId(final QuestionPollFactory factory , UserId... userId) {
        factory.accessibilityAddUser(userId);
    }
}
