package mops.infrastructure.database.repositories;

import mops.controllers.dtos.DatePollOptionDto;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserJpaRepository extends JpaRepository<UserDao, Long> {
    Set<UserDao> findByDatePollSetContains(DatePollDao datePollDao);
    //Anzahl aller user die fuer die datepolloption gestimmt haben
    Long countByDatePollOptionSetContains(DatePollOptionDto datePollOptionDto);
}
