package mops.infrastructure.database.repositories.interfaces;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface DatePollJpaRepository extends JpaRepository<DatePollDao, String> {
    DatePollDao findDatePollDaoByLink(String link);
    DatePollDao findByCreatorUserDao(UserDao userDao);
    Set<DatePollDao> findDatePollDaoByUserDaosContaining(UserDao userDao);
}
