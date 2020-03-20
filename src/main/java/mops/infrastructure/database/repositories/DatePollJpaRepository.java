package mops.infrastructure.database.repositories;

import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatePollJpaRepository extends JpaRepository<DatePollDao, Long> {
    @Override
    <S extends DatePollDao> S save(S entity);
    DatePollDao findDatePollDaoByLink(String link);
    DatePollDao findDatepollByLink(String link);
}
