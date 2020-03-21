package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollDaoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatePollStatusJpaRepository extends JpaRepository<DatePollDaoStatus, Long> {
    DatePollDaoStatus findByParticipantAndDatePoll(UserDao participant, DatePollDao datePollDao);
}
