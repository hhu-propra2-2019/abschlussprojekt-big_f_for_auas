package mops.infrastructure.database;

import lombok.NoArgsConstructor;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollLink;
import mops.domain.models.repository.QuestionPollRepositoryInterface;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
public class QuestionPollRepositoryImpl implements QuestionPollRepositoryInterface {
    /**
     * Speichert ein QuestionPoll aggregat.
     * @param questionPoll
     * @return
     */
    @Override
    public QuestionPollLink save(QuestionPoll questionPoll) {
        throw new UnsupportedOperationException();
    }
}
