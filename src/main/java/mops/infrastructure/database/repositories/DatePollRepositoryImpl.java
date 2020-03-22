package mops.infrastructure.database.repositories;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        DatePollDao loaded = datePollJpaRepository.findDatePollDaoByLink(link.getPollIdentifier());
        DatePoll targetDatePoll = ModelOfDaoUtil.pollOf(loaded);
        return Optional.of(targetDatePoll);
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
    public Set<DatePoll> getDatePollsByUserId(UserId userId) {
        UserDao targetUser = DaoOfModelUtil.userDaoOf(userId);
        Set<DatePollDao> datePollDaosFromUser = datePollJpaRepository.findDatePollDaoByUserDaosContaining(targetUser);
        Set<DatePoll> targetDatePolls = new HashSet<>();
        datePollDaosFromUser.forEach(
                datePollDao -> targetDatePolls.add(ModelOfDaoUtil.pollOf(datePollDao)));
        return targetDatePolls;
    }
}
