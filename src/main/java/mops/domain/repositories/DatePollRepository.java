package mops.domain.repositories;

import mops.domain.models.DatePoll.DatePoll;
import mops.domain.models.DatePoll.DatePollId;

/**
 * Repository interface für datePolls.
 */
public interface DatePollRepository {
    /**
     * Methodenkopf für die DatePoll-Lademethode.
     * @param datePollId Id der zu ladenden DatePoll
     * @return DatePoll;
     */
    DatePoll load(DatePollId datePollId);

    /**
     * Methodenkopf für die DatePoll-Speichermethode.
     * @param datePoll Zu speichernde DatePoll
     */
    void save(DatePoll datePoll);
}
