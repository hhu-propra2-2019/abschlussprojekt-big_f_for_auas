package mops.infrastructure.database.daos.translator;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.infrastructure.database.daos.PollLifeCycleDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;

public class DatePollOfDatePollDao {
    public static DatePollMetaInf metaInfOf(DatePollMetaInfDao datePollMetaInfDao) {
        PollLifeCycleDao pollLifeCycleDao = datePollMetaInfDao.getPollLifeCycleDao();
        DatePollMetaInf datePollMetaInf = new DatePollMetaInf(
                datePollMetaInfDao.getTitle(),
                datePollMetaInfDao.getDescription(),
                datePollMetaInfDao.getLocation(),
                new Timespan(pollLifeCycleDao.getStartdate(), pollLifeCycleDao.getEnddate())
        );
        return datePollMetaInf;
    }
    //terminated or not and lastmodified
    public static PollRecordAndStatus recordAndStatusOf(DatePollDao datePollDao) {
        return null;
    }
}
