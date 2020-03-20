package mops.infrastructure.database.repositories;

import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollOption;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.infrastructure.database.daos.PollLifeCycleDao;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollConfigDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;
import mops.infrastructure.database.daos.datepoll.DatePollOptionDao;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        //DatePollDaoMetaInf
        DatePollMetaInf datePollMetaInf = datePoll.getDatePollMetaInf();
        Timespan pollTimeSpan = datePollMetaInf.getDatePollLifeCycle();
        PollLifeCycleDao lifeCycleDao = new PollLifeCycleDao(pollTimeSpan.getStartDate(), pollTimeSpan.getEndDate());
        DatePollMetaInfDao datePollMetaInfDao = new DatePollMetaInfDao(
                datePollMetaInf.getDatePollDescription().getDescription(),
                datePollMetaInf.getDatePollLocation().getLocation(), lifeCycleDao);

        //DatePollDaoConfig
        DatePollConfig datePollConfig = datePoll.getDatePollConfig();
        DatePollConfigDao datePollConfigDao = new DatePollConfigDao(
                datePollConfig.isPriorityChoice(),
                datePollConfig.isAnonymous(),
                datePollConfig.isOpenForOwnEntries(),
                datePollConfig.isOpen(),
                datePollConfig.isSingleChoice()

        );
        //DatePollDaoRecordAndStatus
        PollRecordAndStatus pollRecordAndStatus = datePoll.getDatePollRecordAndStatus();
        PollRecordAndStatusDao pollRecordAndStatusDao = new PollRecordAndStatusDao(
                pollRecordAndStatus.getLastModified()
        );

        //DatePollDaoCreator
        UserDao creator = new UserDao();
        creator.setId(Long.parseLong(datePoll.getCreator().getId()));

        //DatePollLink
        String newLink = datePoll.getDatePollLink().getDatePollIdentifier();
        //Neues DatePollDao
        DatePollDao datePollDao = new DatePollDao();
        datePollDao.setLink(newLink);
        datePollDao.setDatePollMetaInfDao(datePollMetaInfDao);
        datePollDao.setDatePollConfigDao(datePollConfigDao);
        datePollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);

        //DatePollDaoOptions
        datePollDao.setDatePollOptionSet(extractDatePollOptionDaos(datePoll, datePollDao));
        //DatePollDaoParticipants
        datePollDao.setUserSet(extractDatePollUser(datePoll, datePollDao));
        datePollJpaRepository.save(datePollDao);
    }

    private Set<DatePollOptionDao> extractDatePollOptionDaos(DatePoll datePoll, DatePollDao datePollDao) {
        List<DatePollOption> datePollOptionList = datePoll.getDatePollOptions();
        Set<DatePollOptionDao> datePollOptionDaos = new HashSet<>();
        for (DatePollOption datePollOption:datePollOptionList
             ) {
            Timespan nextPeriod = datePollOption.getSuggestedPeriod();
            PollLifeCycleDao optionLifecycle = new PollLifeCycleDao(nextPeriod.getStartDate(), nextPeriod.getEndDate());
            DatePollOptionDao currentOption = new DatePollOptionDao();
            currentOption.setDatePoll(datePollDao);
            currentOption.setPollLifeCycleDao(optionLifecycle);
            datePollOptionDaos.add(currentOption);
        }
        return datePollOptionDaos;
    }

    private Set<UserDao> extractDatePollUser(DatePoll datePoll, DatePollDao datePollDao) {
        List<UserId> userIds = datePoll.getParticipants();
        Set<UserDao> userDaoSet = new HashSet<>();
        for (UserId currentUserId:userIds
             ) {
            UserDao userDao = new UserDao();
            userDao.setId(Long.parseLong(currentUserId.getId()));
            userDaoSet.add(userDao);
        }
        return userDaoSet;
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
