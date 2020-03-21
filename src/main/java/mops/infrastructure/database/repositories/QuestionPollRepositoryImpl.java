package mops.infrastructure.database.repositories;

import lombok.NoArgsConstructor;
import mops.domain.models.PollLink;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.repository.QuestionPollRepositoryInterface;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
public class QuestionPollRepositoryImpl implements QuestionPollRepositoryInterface {
    /**
     * Speichert ein QuestionPoll aggregat.
     * @param questionPoll ...
     * @return QuestionPollLink ...
     */
    @Override
    public PollLink save(QuestionPoll questionPoll) {
        throw new UnsupportedOperationException();
    }
}
