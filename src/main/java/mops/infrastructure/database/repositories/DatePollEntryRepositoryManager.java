package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.repositories.interfaces.DatePollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"PMD.LawOfDemeter"})
@Repository
public class DatePollEntryRepositoryManager {
    private final transient DatePollJpaRepository datePollJpaRepository;
    private final transient DatePollEntryJpaRepository datePollEntryJpaRepository;
    private final transient UserJpaRepository userJpaRepository;
    @Autowired
    public DatePollEntryRepositoryManager(
            DatePollJpaRepository datePollJpaRepository,
            DatePollEntryJpaRepository datePollEntryJpaRepository,
            UserJpaRepository userJpaRepository) {
        this.datePollJpaRepository = datePollJpaRepository;
        this.datePollEntryJpaRepository = datePollEntryJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }
    /**
     * Die Methode soll ein DatePollEntryDao Objekt anhand des pollLinks und des Terminvorschlags
     * heraussuchen. Als Key wird zuerst der pollLink benutzt, um alle zugehoerigen DatePollEntryDaos
     * zu generieren, danach wird anhand des TimespanObjektes nach dem zugehoerigen Terminvorschlag gesucht.
     * @param pollLink Das DatePoll Objekt um das es geht.
     * @param datePollEntry Der zugehörige Terminvorschlag.
     * @return DatePollEntryDao Das DatePollEntryDao Objekt aus der Datenbank.
     */
     public DatePollEntryDao findDatePollEntryDao(
             PollLink pollLink,
             DatePollEntry datePollEntry
     ) {
         final DatePollDao currentDatePollDao = datePollJpaRepository.
                 findDatePollDaoByLink(pollLink.getPollIdentifier());
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
    public void userVotesForDatePollEntry(UserId userId, PollLink pollLink, DatePollEntry datePollEntry) {
        final DatePollEntryDao targetDatePollEntryDao = findDatePollEntryDao(pollLink, datePollEntry);
        final UserDao currentUserDao = userJpaRepository.getOne(Long.parseLong(userId.getId()));
        targetDatePollEntryDao.getUserVotesFor().add(currentUserDao);
        currentUserDao.getDatePollEntrySet().add(targetDatePollEntryDao);
        userJpaRepository.save(currentUserDao);
        datePollEntryJpaRepository.save(targetDatePollEntryDao);
    }
    /**
     *Die Methode gibt die Anzahl der Stimmen (votes) fuer einen Abstimmungsvorschlag zurueck.
     * @param pollLink zugehoeriger DatePoll.
     * @param datePollEntry Vorschlag fuer den abgestimmt wurde.
     * @return votes Die Anzahl an "yes" Stimmen fuer die jeweilige Abstimmung.
     */
    public Long getVotesForDatePollEntry(PollLink pollLink, DatePollEntry datePollEntry) {
        final DatePollEntryDao datePollEntryDao = findDatePollEntryDao(pollLink, datePollEntry);
        return userJpaRepository.countByDatePollEntrySetContaining(datePollEntryDao);
    }
}