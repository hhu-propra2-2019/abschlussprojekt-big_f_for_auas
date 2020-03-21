package mops.infrastructure.database.daos.translator;

import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.*;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollConfigDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;

import java.util.HashSet;
import java.util.Set;

public class ModelOfDao {
    public static DatePoll datePollOf(DatePollDao datePollDao){
        PollRecordAndStatus pollRecordAndStatus =
                ModelOfDao.pollRecordAndStatusOf(datePollDao.getPollRecordAndStatusDao());

        DatePollMetaInf datePollMetaInf =
                ModelOfDao.metaInfOf(datePollDao.getDatePollMetaInfDao());

        User creator = userOf(datePollDao.getCreatorUserDao());

        DatePollConfig datePollConfig =
                ModelOfDao.configOf(datePollDao.getDatePollConfigDao());

        Set<DatePollEntry> datePollEntries = extractDatePollEntries(datePollDao.getDatePollEntrySet());

        //TODO: add extract UserId Set from User Set method
        Set<User> participants = extractDatePollUser(datePollDao.getUserSet());

        //TODO: add
        Set<DatePollBallot> datePollBallots = null;

        PollLink newLink = new PollLink(datePollDao.getLink());

        /*
        DatePoll datePoll = new DatePoll(
                pollRecordAndStatus,
                datePollMetaInf,
                creator.getId(),
                datePollConfig,
                datePollEntries,
                participants,
                datePollBallots,
                newLink
        );
        */

        return null;
    }

    public static PollRecordAndStatus pollRecordAndStatusOf(PollRecordAndStatusDao dao) {
        /*return new PollRecordAndStatus(
                dao.getLastmodified()
        );*/
        return null;
    }

    public static DatePollMetaInf metaInfOf(DatePollMetaInfDao dao) {
        TimespanDao timespanDao = dao.getTimespan();
        return new DatePollMetaInf(
                dao.getTitle(),
                dao.getDescription(),
                dao.getLocation(),
                new Timespan(timespanDao.getStartDate(), timespanDao.getEndDate())
        );
    }

    //TODO: geht so nicht wegen Id !!!
    public static User userOf(UserDao dao) {
        UserId userId = new UserId(Long.toString(dao.getId()));
        return new User(userId);
    }

    public static DatePollConfig configOf(DatePollConfigDao dao) {
        return new DatePollConfig(
                dao.isVoteIsEditable(),
                dao.isOpenForOwnEntries(),
                dao.isSingleChoice(),
                dao.isPriorityChoice(),
                dao.isAnonymous(),
                dao.isOpen()
        );
    }

    private static Set<DatePollEntry> extractDatePollEntries(Set<DatePollEntryDao> daoEntries){
        Set<DatePollEntry> datePollEntries = new HashSet<>();
        for (DatePollEntryDao daoEntry : daoEntries) {
            DatePollEntry currentEntry = ModelOfDao.entryOf(daoEntry);
            datePollEntries.add(currentEntry);
        }
        return datePollEntries;
    }

    public static DatePollEntry entryOf(DatePollEntryDao dao) {
        Timespan entryTimespan = timespanOf(dao.getTimespanDao());
        //TODO: add votes
        return new DatePollEntry(entryTimespan);
    }

    public static Timespan timespanOf(TimespanDao dao) {
        return new Timespan(dao.getStartDate(), dao.getEndDate());
    }

    private static Set<User> extractDatePollUser(Set<UserDao> daoUser) {
        Set<User> datePollUser = new HashSet<>();
        for (UserDao dao : daoUser) {
            User currentUser = ModelOfDao.userOf(dao);
            datePollUser.add(currentUser);
        }
        return datePollUser;
    }

}
