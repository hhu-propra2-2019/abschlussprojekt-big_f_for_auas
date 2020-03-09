package mops.database;

import mops.domain.models.DatePoll;
import mops.domain.models.DatePollID;

/**
 * Repository interface für datePolls.
 */
public interface DatePollRepository {
    /**
     * Methodenkopf für die DatePoll-Lademethode.
     * @param datePollID Id der zu ladenden DatePoll
     * @return DatePoll;
     */
    DatePoll getDatePollById(DatePollID datePollID);

    /**
     * Methodenkopf für die DatePoll-Speichermethode.
     * @param datePoll Zu speichernde DatePoll
     */
    void addDatePoll(DatePoll datePoll);
}
