package mops.infrastructure.database.daos.translator;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.*;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.PollLifeCycleDao;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollConfigDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;

import java.util.HashSet;
import java.util.Set;

public class DatePollOfDatePollDao {
    public static DatePoll datePollOf(DatePollDao datePollDao){
        PollRecordAndStatus pollRecordAndStatus =
                DatePollOfDatePollDao.pollRecordAndStatusOf(datePollDao.getPollRecordAndStatusDao());

        DatePollMetaInf datePollMetaInf =
                DatePollOfDatePollDao.metaInfOf(datePollDao.getDatePollMetaInfDao());

        UserId creator = userOf(datePollDao.getCreatorUserDao());

        DatePollConfig datePollConfig =
                DatePollOfDatePollDao.configOf(datePollDao.getDatePollConfigDao());

        Set<DatePollEntry> datePollEntries = extractDatePollEntries(datePollDao.getDatePollEntrySet());

        Set<UserId> participants = extractDatePollUser(datePollDao.getUserSet());

        //TODO: add
        Set<DatePollBallot> datePollBallots = null;

        DatePollLink newLink = new DatePollLink(datePollDao.getLink());

        /*
        DatePoll datePoll = new DatePoll(
                pollRecordAndStatus,
                datePollMetaInf,
                creator,
                datePollConfig,
                datePollEntries,
                participants,
                datePollBallots,
                newLink
        );
        */

        return null;
    }

    private static PollRecordAndStatus pollRecordAndStatusOf(PollRecordAndStatusDao dao) {
        /*return new PollRecordAndStatus(
                dao.getLastmodified()
        );*/
        return null;
    }

    public static DatePollMetaInf metaInfOf(DatePollMetaInfDao dao) {
        PollLifeCycleDao pollLifeCycleDao = dao.getPollLifeCycleDao();
        return new DatePollMetaInf(
                dao.getTitle(),
                dao.getDescription(),
                dao.getLocation(),
                new Timespan(pollLifeCycleDao.getStartdate(), pollLifeCycleDao.getEnddate())
        );
    }

    private static UserId userOf(UserDao dao) {
        return new UserId(Long.toString(dao.getId()));
    }

    private static DatePollConfig configOf(DatePollConfigDao dao) {
        return new DatePollConfig(
                dao.isPrioritychoice(),
                dao.isAnonymous(),
                dao.isOpenforownentries(),
                dao.isVisible(),
                dao.isSinglechoice(),
                dao.isVoteIsEditable()
        );
    }

    private static Set<DatePollEntry> extractDatePollEntries(Set<DatePollEntryDao> daoEntries){
        Set<DatePollEntry> datePollEntries = new HashSet<>();
        for (DatePollEntryDao daoEntry : daoEntries) {
            DatePollEntry currentEntry = DatePollOfDatePollDao.entryOf(daoEntry);
            datePollEntries.add(currentEntry);
        }
        return datePollEntries;
    }

    private static DatePollEntry entryOf(DatePollEntryDao dao) {
        Timespan entryTimespan = timespanOf(dao.getPollLifeCycleDao());
        //TODO: add votes
        return new DatePollEntry(entryTimespan);
    }

    private static Timespan timespanOf(PollLifeCycleDao dao) {
        return new Timespan(dao.getStartdate(), dao.getEnddate());
    }

    private static Set<UserId> extractDatePollUser(Set<UserDao> daoUser) {
        Set<UserId> datePollUser = new HashSet<>();
        for (UserDao dao : daoUser) {
            UserId currentUser = DatePollOfDatePollDao.userOf(dao);
            datePollUser.add(currentUser);
        }
        return datePollUser;
    }

}
