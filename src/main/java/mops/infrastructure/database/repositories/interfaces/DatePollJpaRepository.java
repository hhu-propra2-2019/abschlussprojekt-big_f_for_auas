package mops.infrastructure.database.repositories.interfaces;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Set;

public interface DatePollJpaRepository extends JpaRepository<DatePollDao, String> {
    DatePollDao findDatePollDaoByLink(String link);
    Set<DatePollDao> findByCreatorUserDao(UserDao userDao);
    Set<DatePollDao> findByGroupDaosContaining(GroupDao targetGroup);
    @Query(
            value = "select a.* from datepoll as a, datepoll_group_daos b, usergroup_user_daos c "
                    + "where a.link = b.date_poll_daos_link and b.group_daos_id = c.group_set_id "
                    + "and c.user_daos_id = ?1",
            nativeQuery = true
    )
    Set<DatePollDao> findAllDatePollsOfTargetUser(String userId);


    Set<DatePollDao> findDistinctByGroupDaosIn(Set<GroupDao> groupDaos);

    @Query(
            value = "select a.* from datepoll as a where a.end_date <= ?1",
            nativeQuery = true
    )
    Set<DatePollDao> findAllTerminatedDatePollsByEndDate(LocalDate endDate);
//    @Query(
//        value = "select datePoll.* from datepoll as datePoll, datepollstatus "
//            + "where datePoll.link = datepollstatus.datepoll_link and datepollstatus.user_id = ?1",
//        nativeQuery = true
//    )
//    Set<DatePollDao> findDatePollDaoWhereUserHasStatus(UserId userId);
}
