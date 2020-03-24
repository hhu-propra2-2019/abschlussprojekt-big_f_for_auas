package mops.infrastructure.database.repositories.interfaces;

import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

@SuppressWarnings("PMD.MissingOverride")
public interface UserJpaRepository extends JpaRepository<UserDao, String> {
    /*Set<UserDao> findByDatePollSetContains(DatePollDao datePollDao);
    Set<UserDao> findByQuestionPollSetContains(QuestionPollDao questionPollDao);*/
    //Anzahl aller user die fuer die datepolloption gestimmt haben
    Long countByDatePollEntrySetContaining(DatePollEntryDao datePollEntryDao);
    Long countByQuestionPollEntrySetContaining(QuestionPollEntryDao questionPollEntryDao);
    Optional<UserDao> findById(String id);
    Set<UserDao> findAllByGroupSetContaining(GroupDao targetGroup);
}
