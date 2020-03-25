package mops.infrastructure.database.repositories.interfaces;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface DatePollJpaRepository extends JpaRepository<DatePollDao, String> {
    DatePollDao findDatePollDaoByLink(String link);
    Set<DatePollDao> findByCreatorUserDao(UserDao userDao);
    Set<DatePollDao> findByGroupDaosContaining(GroupDao targetGroup);

    @Query(
        value = "select datePoll.* from datepoll as datePoll, datepollstatus "
            + "where datePoll.link = datepollstatus.datepoll_link and datepollstatus.user_id = ?1",
        nativeQuery = true
    )
    Set<DatePollDao> findDatePollDaoWhereUserHasStatus(UserId userId);
}
