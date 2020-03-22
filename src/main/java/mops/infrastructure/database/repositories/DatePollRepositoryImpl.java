package mops.infrastructure.database.repositories;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class DatePollRepositoryImpl implements DatePollRepository {

    private DatePollJpaRepository datePollJpaRepository;

    @Autowired
    public DatePollRepositoryImpl(DatePollJpaRepository datePollJpaRepository) {
        this.datePollJpaRepository = datePollJpaRepository;
    }

    /**
     * Lädt das DatePoll aggregat anhand seines links.
     * @param link Eindeutig identifizierender link einer Terminfindung.
     * @return An Inputlink gekoppeltes DatePoll
     */
    @Override
    public Optional<DatePoll> load(PollLink link) {
        //DatePollDao loaded = datePollJpaRepository.findDatePollDaoByLink(link.getDatePollIdentifier());
        //if (loaded != null) {
        //    return DatePollDao.to(loaded);
        //}
        return Optional.empty();
    }

    /**
     * Speichert ein DatePoll aggregat.
     * @param datePoll Zu speichernde DatePoll
     */
    @Override
    public void save(DatePoll datePoll) {
        datePollJpaRepository.save(DaoOfModelUtil.pollDaoOf(datePoll));
    }

    /**
     * Lädt alle DatePolls in denen ein Nutzer Teilnimmt.
     * @param userId
     * @return List<DatePoll>
     */
    @Override
    public List<DatePoll> getDatePollsByUserId(UserId userId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Dopplung mit load.
     * @param pollLink
     * @return DatePoll
     */
    @Override
    public DatePoll getDatePollByLink(PollLink pollLink) {
        throw new UnsupportedOperationException();
    }
}
