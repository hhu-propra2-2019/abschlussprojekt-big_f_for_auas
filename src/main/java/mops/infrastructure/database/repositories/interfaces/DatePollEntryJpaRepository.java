package mops.infrastructure.database.repositories.interfaces;

import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface DatePollEntryJpaRepository extends JpaRepository<DatePollEntryDao, Long> {
    Set<DatePollEntryDao> findByDatePoll(DatePollDao datePollDao);
    DatePollEntryDao findByDatePollAndAndTimespanDao(DatePollDao datePollDao, TimespanDao timespanDao);
    @Query(
        value = "select a.* from datepollentry as a, datepollentry_user_votes_for as b "
            + "where a.id = b.date_poll_entry_set_id and b.user_votes_for_id = ?1 "
            + "and a.date_poll_link = ?2",
        nativeQuery = true
    )
    Set<DatePollEntryDao> findAllDatePollEntriesUserVotesFor(String userId, String pollLink);
    @Query(
        value = "select a.* from datepollentry as a, datepollentry_user_votes_for_maybe as b "
                + "where a.id = b.date_poll_entry_set_maybe_id and b.user_votes_for_maybe_id = ?1 "
                + "and a.date_poll_link = ?2",
        nativeQuery = true
    )
    Set<DatePollEntryDao> findAllDatePollEntriesUserVotesForMaybe(String id, String linkUUIDAsString);
}
