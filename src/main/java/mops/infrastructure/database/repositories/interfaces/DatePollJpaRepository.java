package mops.infrastructure.database.repositories.interfaces;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface DatePollJpaRepository extends JpaRepository<DatePollDao, String> {
    DatePollDao findDatePollDaoByLink(String link);
    DatePollDao findByCreatorUserDao(UserDao userDao);
    Set<DatePollDao> findByGroupDaosContaining(GroupDao targetGroup);
    @Query(
            value = "select a.* from datepoll as a, datepoll_group_daos b, usergroup_user_daos c "
                    + "where a.link = b.date_poll_daos_link and b.group_daos_id = c.group_set_id "
                    + "and c.user_daos_id = ?1",
            nativeQuery = true
    )
    Set<DatePollDao> findAllDatePollsOfTargetUser(String userId);
}
