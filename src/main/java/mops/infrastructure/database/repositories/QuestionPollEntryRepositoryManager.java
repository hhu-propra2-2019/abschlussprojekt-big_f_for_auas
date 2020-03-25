package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import mops.infrastructure.database.repositories.interfaces.QuestionPollEntryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionPollEntryRepositoryManager {
    private final transient QuestionPollEntryJpaRepository questionPollEntryJpaRepository;
    @Autowired
    public QuestionPollEntryRepositoryManager(
            QuestionPollEntryJpaRepository questionPollEntryJpaRepository
            ) {
        this.questionPollEntryJpaRepository = questionPollEntryJpaRepository;
    }
    /**
     * @param questionPollEntryDaoPollEntryDao ...
     */
    public void save(QuestionPollEntryDao questionPollEntryDaoPollEntryDao) {
        questionPollEntryJpaRepository.save(questionPollEntryDaoPollEntryDao);
    }
}
