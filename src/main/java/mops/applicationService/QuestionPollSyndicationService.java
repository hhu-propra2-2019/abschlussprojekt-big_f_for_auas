package mops.applicationService;


import mops.domain.models.QuestionPoll.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.Repository.QuestionPollRepositoryInterface;
import org.springframework.stereotype.Service;

@Service
public class QuestionPollSyndicationService {


    QuestionPollRepositoryInterface questionPollRepo;

    public String generateLink(final QuestionPoll.QuestionPollBuilder qpBuilder) {
        QuestionPollId qpId = questionPollRepo.save(qpBuilder.build());
        String url = questionPollRepo.getUrl(qpId);
        return url;
    }

}
