package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserJpaRepository extends JpaRepository<UserDao, Long> {
    Set<UserDao> findByDatePollSetContains(DatePollDao datePollDao);
    //Anzahl aller user die fuer die datepolloption gestimmt haben
    Long countByDatePollEntrySetContains(DatePollEntryDao datePollEntryDao);
}
