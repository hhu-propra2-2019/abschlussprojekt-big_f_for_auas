package mops.infrastructure.database.repositories.interfaces;

import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionPollEntryJpaRepository extends JpaRepository<QuestionPollEntryDao, Long> {
    Set<QuestionPollEntryDao> findByQuestionPoll(QuestionPollDao questionPollDao);
}
