package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollEntry;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
     * @return DatePollEntryDao Das DatePollEntry Objekt aus der Datenbank.
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
}
