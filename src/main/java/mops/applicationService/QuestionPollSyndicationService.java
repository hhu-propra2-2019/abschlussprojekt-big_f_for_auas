package mops.applicationService;


import mops.domain.models.QuestionPoll.QuestionPoll;
import mops.domain.models.QuestionPoll.QuestionPollId;
import mops.domain.models.Repository.QuestionPollRepositoryInterface;
import org.springframework.stereotype.Service;

@Service
public class QuestionPollSyndicationService {


    QuestionPollRepositoryInterface qestionPollRepo;

    public String generateLink(final QuestionPoll.QuestionPollBuilder qpBuilder) {
        QuestionPollId qpId = qestionPollRepo.save(qpBuilder.build());
        String url = qestionPollRepo.getUrl(qpId);
        return url;
    }

}
