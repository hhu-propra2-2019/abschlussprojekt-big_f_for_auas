package mops.infrastructure.database.repositories.interfaces;

import mops.infrastructure.database.daos.datepoll.DatePollStatusDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DatePollStatusJpaRepository extends JpaRepository<DatePollStatusDao, Long> {
    @Query(
            value = "select a.* from datepollstatus as a "
                    + "where a.datepoll_link = ?2 and a.participant_id = ?1 ",
            nativeQuery = true
    )
    DatePollStatusDao findByParticipantAndDatePoll(String userId, String datePollLink);
}
