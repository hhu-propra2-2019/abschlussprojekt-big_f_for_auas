package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.user.UserId;
import mops.domain.repositories.QuestionPollRepository;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.QuestionPollJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class QuestionPollRepositoryImpl implements QuestionPollRepository {

    private final transient QuestionPollJpaRepository questionPollJpaRepository;

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
        final QuestionPollDao questionPollDao = questionPollJpaRepository.
                findQuestionPollDaoByLink(link.getPollIdentifier());
        return Optional.of(ModelOfDaoUtil.pollOf(questionPollDao));
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
     * @param userId Der User, welcher an den QuestionPolls teilnimmt.
     * @return Set<QuestionPoll> Die entsprechenden QuestionPolls.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public Set<QuestionPoll> getQuestionPollsByUserId(UserId userId) {
        final UserDao targetUser = DaoOfModelUtil.userDaoOf(userId);
        final Set<QuestionPollDao> questionPollDaosFromUser = questionPollJpaRepository.
                findQuestionPollDaoByUserDaosContaining(targetUser);
        final Set<QuestionPoll> targetQuestionPolls = new HashSet<>();
        questionPollDaosFromUser.forEach(
                datePollDao -> targetQuestionPolls.add(ModelOfDaoUtil.pollOf(datePollDao)));
        return targetQuestionPolls;
    }
}
