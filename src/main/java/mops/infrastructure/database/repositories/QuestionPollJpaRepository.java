package mops.infrastructure.database.repositories;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionPollJpaRepository extends JpaRepository<QuestionPollDao, String> {
    @Override
    <S extends QuestionPollDao> S save(S entity);
    QuestionPollDao findQuestionPollDaoByLink(String link);
}
