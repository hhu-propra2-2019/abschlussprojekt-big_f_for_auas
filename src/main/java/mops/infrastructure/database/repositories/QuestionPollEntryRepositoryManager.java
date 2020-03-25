package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import mops.infrastructure.database.repositories.interfaces.QuestionPollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.QuestionPollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionPollEntryRepositoryManager {
    private final transient QuestionPollEntryJpaRepository questionPollEntryJpaRepository;
    private final transient QuestionPollJpaRepository questionPollJpaRepository;
    private final transient UserJpaRepository userJpaRepository;
    @Autowired
    public QuestionPollEntryRepositoryManager(
            QuestionPollEntryJpaRepository questionPollEntryJpaRepository,
            QuestionPollJpaRepository questionPollJpaRepository,
            UserJpaRepository userJpaRepository) {
        this.questionPollEntryJpaRepository = questionPollEntryJpaRepository;
        this.questionPollJpaRepository = questionPollJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }
    /**
     * Die Methode speichert ein QuestionPollEntryDao in die Datenbank.
     * @param questionPollEntryDao Das zugehoerige QuestionPollEntryDao.
     */
    public void save(QuestionPollEntryDao questionPollEntryDao) {
        questionPollEntryJpaRepository.save(questionPollEntryDao);
    }

    /**
     * Die Methode dient dazu ein "Yes" vote fuer ein QuestionPollEntry abgeben zu koennen.
     * Es wird das zugehoerige QuestionPollDao geladen.
     * Es wird das zugehoerige QuestionPollEntryDao geladen.
     * Es wird der zugehoerige User geladen.
     * @param userId Der User der abstimmt.
     * @param pollLink zugehoeriger QuestionPoll.
     * @param questionPollEntry Vorschlag fuer den abgestimmt wird.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void userVotesForQuestionPollEntry(UserId userId, PollLink pollLink, QuestionPollEntry questionPollEntry) {
        final QuestionPollEntryDao targetQuestionPollEntryDao = loadQuestionPollEntryDao(pollLink, questionPollEntry);
        final UserDao currentUserDao = userJpaRepository.getOne(userId.getId());
        targetQuestionPollEntryDao.getUserVotesFor().add(currentUserDao);
        currentUserDao.getQuestionPollEntrySet().add(targetQuestionPollEntryDao);
        userJpaRepository.save(currentUserDao);
        questionPollEntryJpaRepository.save(targetQuestionPollEntryDao);
    }

    private QuestionPollEntryDao loadQuestionPollEntryDao(PollLink pollLink, QuestionPollEntry questionPollEntry) {
        final QuestionPollDao targetQuestionPollDao = questionPollJpaRepository
                .findQuestionPollDaoByLink(pollLink.getLinkUUIDAsString());
        return questionPollEntryJpaRepository.findByQuestionPollAndEntryName(
                targetQuestionPollDao, questionPollEntry.getTitle());
    }
}
