package mops.infrastructure.database.repositories.interfaces;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionPollJpaRepository extends JpaRepository<QuestionPollDao, String> {
    QuestionPollDao findQuestionPollDaoByLink(String link);
    QuestionPollDao findByCreatorUserDao(UserDao userDao);
    Set<QuestionPollDao> findQuestionPollDaoByUserDaosContaining(UserDao userDao);
}
