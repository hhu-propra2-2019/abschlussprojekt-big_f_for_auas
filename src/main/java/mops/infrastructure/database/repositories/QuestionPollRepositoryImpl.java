package mops.infrastructure.database.repositories;

import lombok.NoArgsConstructor;
import mops.domain.models.PollLink;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.user.UserId;
import mops.domain.repositories.QuestionPollRepository;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.repositories.interfaces.QuestionPollJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class QuestionPollRepositoryImpl implements QuestionPollRepository {

    private QuestionPollJpaRepository questionPollJpaRepository;

    @Autowired
    public QuestionPollRepositoryImpl(QuestionPollJpaRepository questionPollJpaRepository) {
        this.questionPollJpaRepository = questionPollJpaRepository;
    }

    /**
     * Lädt das QuestionPoll aggregat anhand seines links.
     * @param link Eindeutig identifizierender link einer Terminfindung.
     * @return An Inputlink gekoppeltes QuestionPoll
     */
    @Override
    public Optional<QuestionPoll> load(PollLink link) {
        return Optional.empty();
    }

    /**
     * Speichert ein QuestionPoll aggregat.
     * @param questionPoll Zu speicherndes QuestionPoll
     */
    @Override
    public void save(QuestionPoll questionPoll) {
        questionPollJpaRepository.save(DaoOfModelUtil.pollDaoOf(questionPoll));
    }

    /**
     * Lädt alle QuestionPolls in denen ein Nutzer Teilnimmt.
     * @param userId
     * @return List<QuestionPoll>
     */
    public List<QuestionPoll> getQuestionPollsByUserId(UserId userId) {
        throw new UnsupportedOperationException();
    }
    /**
     * Dopplung mit load.
     * @param questionPollLink
     * @return QuestionPollDao
     */
    public QuestionPollDao getQuestionPollDao(String questionPollLink) {
        return questionPollJpaRepository.findQuestionPollDaoByLink(questionPollLink);
    }
    /**
     * Dopplung mit load.
     * @param pollLink
     * @return QuestionPoll
     */
    public QuestionPoll getQuestionPollByLink(PollLink pollLink) {
        throw new UnsupportedOperationException();
    }
}
