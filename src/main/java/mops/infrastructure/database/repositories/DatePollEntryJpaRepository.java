package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DatePollEntryJpaRepository extends JpaRepository<DatePollEntryDao, Long> {
    Set<DatePollEntryDao> findByDatePoll(DatePollDao datePollDao);
    DatePollEntryDao findByDatePollAndAndTimespanDao(DatePollDao datePollDao, TimespanDao timespanDao);
}
