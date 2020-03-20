package mops.infrastructure.database.repositories;

import lombok.NoArgsConstructor;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class DatePollRepositoryImpl implements DatePollRepository {

    private DatePollJpaRepository datePollJpaRepository;

    public DatePollRepositoryImpl(DatePollJpaRepository datePollJpaRepository) {
        this.datePollJpaRepository = datePollJpaRepository;
    }

    /**
     * Lädt das DatePoll aggregat anhand seines links.
     * @param link Eindeutig identifizierender link einer Terminfindung.
     * @return
     */
    @Override
    public Optional<DatePoll> load(DatePollLink link) {
        throw new UnsupportedOperationException();
    }

    /**
     * Speichert ein DatePoll aggregat.
     * @param datePoll Zu speichernde DatePoll
     */
    @Override
    public void save(DatePoll datePoll) {
        datePollJpaRepository.save(DatePollDao.of(datePoll));
    }

    /**
     * Lädt alle DatePolls in denen ein Nutzer Teilnimmt.
     * @param userId
     * @return
     */
    @Override
    public List<DatePoll> getDatePollsByUserId(UserId userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Dopplung mit load.
     * @param datePollLink
     * @return
     */
    @Override
    public DatePoll getDatePollByLink(DatePollLink datePollLink) {
        throw new UnsupportedOperationException();
    }
}
