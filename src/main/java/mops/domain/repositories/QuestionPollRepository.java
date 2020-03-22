package mops.domain.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.user.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface für questionPolls
 */
public interface QuestionPollRepository {
    /**
     * Methodenkopf für die QuestionPoll-Lademethode.
     * @param link Eindeutig identifizierender Link einer Abstimmung.
     * @return QuestionPoll;
     */
    Optional<QuestionPoll> load(PollLink link);

    /**
     * Methodenkopf für die QuestionPoll-Speichermethode.
     * @param questionPoll Zu speicherndes QuestionPoll
     */
    void save(QuestionPoll questionPoll);

    List<QuestionPoll> getQuestionPollsByUserId(UserId userId);
    QuestionPoll getQuestionPollByLink(PollLink pollLink);
}
