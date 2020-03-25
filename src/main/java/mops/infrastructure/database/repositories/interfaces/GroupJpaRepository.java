package mops.infrastructure.database.repositories.interfaces;

import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupJpaRepository extends JpaRepository<GroupDao, String> {
    Set<GroupDao> findAllByDatePollDaosContaining(DatePollDao datePollDao);
    Set<GroupDao> findAllByUserDaosContaining(UserDao userDao);
}
