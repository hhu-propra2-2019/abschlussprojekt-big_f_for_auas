package mops.infrastructure.database;

import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatePollJpaRepository extends JpaRepository<DatePollDao, Long> {
    DatePollDao save(DatePollDao newDatePoll);
}
