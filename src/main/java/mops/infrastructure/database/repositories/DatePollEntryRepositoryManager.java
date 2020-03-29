package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.repositories.interfaces.DatePollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@SuppressWarnings({"PMD.LawOfDemeter"})
@Repository
public class DatePollEntryRepositoryManager {
    private final transient DatePollJpaRepository datePollJpaRepository;
    private final transient DatePollEntryJpaRepository datePollEntryJpaRepository;
    private final transient UserRepositoryImpl userRepository;
    @Autowired
    public DatePollEntryRepositoryManager(
            DatePollJpaRepository datePollJpaRepository,
            DatePollEntryJpaRepository datePollEntryJpaRepository,
        UserRepositoryImpl userRepository) {
        this.datePollJpaRepository = datePollJpaRepository;
        this.datePollEntryJpaRepository = datePollEntryJpaRepository;
        this.userRepository = userRepository;
    }
    /**
     * @param datePollEntryDao ...
     */
    public void save(DatePollEntryDao datePollEntryDao) {
        datePollEntryJpaRepository.save(datePollEntryDao);
    }
    /**
     * Die Methode soll ein DatePollEntryDao Objekt anhand des pollLinks und des Terminvorschlags
     * heraussuchen. Als Key wird zuerst der pollLink benutzt, um alle zugehoerigen DatePollEntryDaos
     * zu generieren, danach wird anhand des TimespanObjektes nach dem zugehoerigen Terminvorschlag gesucht.
     * @param pollLink Das DatePoll Objekt um das es geht.
     * @param datePollEntry Der zugehörige Terminvorschlag.
     * @return DatePollEntryDao Das DatePollEntryDao Objekt aus der Datenbank.
     */
     private DatePollEntryDao loadDatePollEntryDao(PollLink pollLink, DatePollEntry datePollEntry) {
         final DatePollDao currentDatePollDao = datePollJpaRepository.
                 findDatePollDaoByLink(pollLink.getLinkUUIDAsString());
         final Timespan periodOfDatePollEntry = datePollEntry.getSuggestedPeriod();
         final TimespanDao timespanDao = TimespanDao.of(periodOfDatePollEntry);
         return datePollEntryJpaRepository.findByDatePollAndAndTimespanDao(currentDatePollDao, timespanDao);
     }
    /**
     * Die Methode dient dazu ein "Yes" vote fuer ein DatePollEntry abgeben zu koennen.
     * Es wird das zugehoerige DatePollDao geladen.
     * Es wird das zugehoerige DatePollEntryDao geladen.
     * Es wird der zugehoerige User geladen.
     * @param userId Der User der abstimmt.
     * @param pollLink zugehoeriger DatePoll.
     * @param datePollEntry Vorschlag fuer den abgestimmt wird.
     */
    @Transactional
    public void userVotesForDatePollEntry(UserId userId, PollLink pollLink, DatePollEntry datePollEntry) {
        final DatePollEntryDao targetDatePollEntryDao = loadDatePollEntryDao(pollLink, datePollEntry);
        userRepository.saveUserIfNotPresent(new User(userId));
        final UserDao userDao = userRepository.loadDao(userId).orElseThrow();
        targetDatePollEntryDao.getUserVotesFor().add(userDao);
        userDao.getDatePollEntrySet().add(targetDatePollEntryDao);
        userRepository.updateUser(userDao);
        datePollEntryJpaRepository.save(targetDatePollEntryDao);
    }
    /**
     *Die Methode gibt die Anzahl der Stimmen (votes) fuer einen Abstimmungsvorschlag zurueck.
     * @param pollLink zugehoeriger DatePoll.
     * @param datePollEntry Vorschlag fuer den abgestimmt wurde.
     * @return votes Die Anzahl an "yes" Stimmen fuer die jeweilige Abstimmung.
     */
    public Long getVotesForDatePollEntry(PollLink pollLink, DatePollEntry datePollEntry) {
        final DatePollEntryDao datePollEntryDao = loadDatePollEntryDao(pollLink, datePollEntry);
        return userRepository.getAllYesVotesForDatePollEntry(datePollEntryDao);
    }

    /**
     * Gibt alle DatePollEntries zum zugehoerigen DatePoll zurueck.
     * @param datePollDao Das DatePoll Objekt.
     * @return Set<DatePollEntryDao> Die zugehoerigen Enries.
     */
    @SuppressWarnings({"PMD.CommentDefaultAccessModifier", "PMD.DefaultPackage"})
    Set<DatePollEntryDao> findAllDatePollEntriesByDatePollDao(DatePollDao datePollDao) {
        return datePollEntryJpaRepository.findByDatePoll(datePollDao);
    }
    /**
     * Die Methode gibt alle "Yes" Votes eines Users fuer einen DatePoll zurueck.
     * @param userId zugehoeriger User.
     * @param datePoll zugehoeriger DatePoll.
     * @return Set<DatePollEntryDao> alle EntryDaos fuer die der Jeweilige User gestimmt hat.
     */
    @Transactional
    public Set<DatePollEntryDao> findAllDatePollEntriesUserVotesFor(UserId userId, DatePoll datePoll) {
        return datePollEntryJpaRepository.findAllDatePollEntriesUserVotesFor(
                userId.getId(), datePoll.getPollLink().getLinkUUIDAsString());
    }

    @SuppressWarnings("checkstyle:DesignForExtension") //NOPMD
    void userVotesMaybeForDatePollEntry(UserId userId, PollLink pollLink, DatePollEntry datePollEntry) { //NOPMD
        final DatePollEntryDao targetDatePollEntryDao = loadDatePollEntryDao(pollLink, datePollEntry);
        userRepository.saveUserIfNotPresent(new User(userId));
        final UserDao userDao = userRepository.loadDao(userId).orElseThrow();
        targetDatePollEntryDao.getUserVotesForMaybe().add(userDao);
        userDao.getDatePollEntrySetMaybe().add(targetDatePollEntryDao);
        userRepository.updateUser(userDao);
        datePollEntryJpaRepository.save(targetDatePollEntryDao);
    }
    @Transactional
    @SuppressWarnings("checkstyle:DesignForExtension")
    public Set<DatePollEntryDao> findAllDatePollEntriesUserVotesForMaybe(UserId userId, DatePoll datePoll) {
        return datePollEntryJpaRepository.findAllDatePollEntriesUserVotesForMaybe(
                userId.getId(), datePoll.getPollLink().getLinkUUIDAsString());
    }
}
