package mops.applicationService;


import mops.domain.models.QuestionPoll.QuestionPollFactory;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.QuestionPoll.QuestionPollLink;
import mops.domain.models.Repository.QuestionPollRepositoryInterface;
import org.springframework.stereotype.Service;

@Service
public class QuestionPollSyndicationService {

    QuestionPollRepositoryInterface questionPollRepo;

    public QuestionPollLink generateLink(final QuestionPollFactory factory) {
        QuestionPollId factory = questionPollRepo.save(factory.build());
        QuestionPollLink link = questionPollRepo.getUrl(qpId);
        return link;
    }

}
