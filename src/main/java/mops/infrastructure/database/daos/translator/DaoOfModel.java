package mops.infrastructure.database.daos.translator;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.pollstatus.PollRecordAndStatus;
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

public class DaoOfModel {
    @SuppressWarnings("checkstyle:RegexpSingleline")
    public static DatePollDao datePollDaoOf(DatePoll datePoll) {
        DatePollMetaInfDao datePollMetaInfDao =
                DaoOfModel.metaInfDaoOf(datePoll.getDatePollMetaInf());

        DatePollConfigDao datePollConfigDao =
                DaoOfModel.configDaoOf(datePoll.getDatePollConfig());

        PollRecordAndStatusDao pollRecordAndStatusDao =
                DaoOfModel.pollRecordAndStatusDaoOf(datePoll.getDatePollRecordAndStatus());

        String newLink = datePoll.getPollLink().getPollIdentifier();

        DatePollDao datePollDao = new DatePollDao();
        datePollDao.setLink(newLink);
        datePollDao.setDatePollMetaInfDao(datePollMetaInfDao);
        datePollDao.setDatePollConfigDao(datePollConfigDao);
        datePollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        //TODO: datePollDao.setCreatorUserDao(datePoll.getCreator());
        datePollDao.setDatePollEntrySet(extractDatePollEntryDaos(datePoll.getDatePollEntries(), datePollDao));
        datePollDao.setUserSet(extractDatePollUser(datePoll));
        return datePollDao;
    }

    private static DatePollEntryDao entryDaoOf(DatePollEntry datePollOption, DatePollDao datePollDao) {
        DatePollEntryDao entry = new DatePollEntryDao();
        entry.setDatePoll(datePollDao);
        entry.setTimespanDao(TimespanDao.of(datePollOption.getSuggestedPeriod()));
        return entry;
    }

    private static UserDao userDaoOf(UserId userId) {
        UserDao user = new UserDao();
        user.setId(Long.parseLong(userId.getId()));
        return user;
    }

    public static DatePollConfigDao configDaoOf(DatePollConfig datePollConfig) {
        return new DatePollConfigDao(
                datePollConfig.isVoteIsEditable(),
                datePollConfig.isOpenForOwnEntries(),
                datePollConfig.isSingleChoice(),
                datePollConfig.isPriorityChoice(),
                datePollConfig.isAnonymous(),
                datePollConfig.isOpen()
        );
    }

    private static PollRecordAndStatusDao pollRecordAndStatusDaoOf(PollRecordAndStatus pollRecordAndStatus) {
        return new PollRecordAndStatusDao(
                pollRecordAndStatus.getLastModified()
        );
    }

    public static DatePollMetaInfDao metaInfDaoOf(DatePollMetaInf datePollMetaInf) {
        TimespanDao lifeCycleDao = TimespanDao.of(datePollMetaInf.getDatePollLifeCycle());
        return new DatePollMetaInfDao(
                datePollMetaInf.getTitle(),
                datePollMetaInf.getDatePollDescription().getDescription(),
                datePollMetaInf.getDatePollLocation().getLocation(), lifeCycleDao);
    }

    private static Set<DatePollEntryDao> extractDatePollEntryDaos(Set<DatePollEntry> datePollEntries, DatePollDao datePollDao) {
        Set<DatePollEntryDao> datePollEntryDaos = new HashSet<>();
        for (DatePollEntry datePollEntry : datePollEntries) {
            DatePollEntryDao currentEntry = DaoOfModel.entryDaoOf(datePollEntry, datePollDao);
            datePollEntryDaos.add(currentEntry);
        }
        return datePollEntryDaos;
    }

    private static Set<UserDao> extractDatePollUser(DatePoll datePoll) {
        Set<UserId> userIds = datePoll.getParticipants();
        Set<UserDao> userDaoSet = new HashSet<>();
        for (UserId currentUserId : userIds) {
            UserDao currentUser = DaoOfModel.userDaoOf(currentUserId);
            userDaoSet.add(currentUser);
        }
        return userDaoSet;
    }
}
