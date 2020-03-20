package mops.infrastructure.database.daos.translator;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
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

public class DatePollDaoOfDatePoll {
    @SuppressWarnings("checkstyle:RegexpSingleline")
    public static DatePollDao datePollDaoOf(DatePoll datePoll) {
        DatePollMetaInfDao datePollMetaInfDao =
                DatePollDaoOfDatePoll.metaInfDaoOf(datePoll.getDatePollMetaInf());

        DatePollConfigDao datePollConfigDao =
                DatePollDaoOfDatePoll.configDaoOf(datePoll.getDatePollConfig());

        PollRecordAndStatusDao pollRecordAndStatusDao =
                DatePollDaoOfDatePoll.pollRecordAndStatusDaoOf(datePoll.getDatePollRecordAndStatus());

        String newLink = datePoll.getDatePollLink().getDatePollIdentifier();

        DatePollDao datePollDao = new DatePollDao();
        datePollDao.setLink(newLink);
        datePollDao.setDatePollMetaInfDao(datePollMetaInfDao);
        datePollDao.setDatePollConfigDao(datePollConfigDao);
        datePollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        //TODO: datePollDao.setCreatorUserDao(datePoll.getCreator());
        datePollDao.setDatePollEntrySet(extractDatePollEntryDaos(datePoll, datePollDao));
        datePollDao.setUserSet(extractDatePollUser(datePoll));
        return datePollDao;
    }

    public static DatePollEntryDao entryDaoOf(DatePollEntry datePollOption, DatePollDao datePollDao) {
        DatePollEntryDao currentOption = new DatePollEntryDao();
        currentOption.setDatePoll(datePollDao);
        currentOption.setPollLifeCycleDao(PollLifeCycleDao.of(datePollOption.getSuggestedPeriod()));
        return currentOption;
    }

    public static UserDao userDaoOf(UserId userId) {
        UserDao user = new UserDao();
        user.setId(Long.parseLong(userId.getId()));
        return user;
    }

    public static DatePollConfigDao configDaoOf(DatePollConfig datePollConfig) {
        return new DatePollConfigDao(
                datePollConfig.isPriorityChoice(),
                datePollConfig.isAnonymous(),
                datePollConfig.isOpenForOwnEntries(),
                datePollConfig.isOpen(),
                datePollConfig.isSingleChoice(),
                datePollConfig.isVoteIsEditable()
        );
    }

    public static PollRecordAndStatusDao pollRecordAndStatusDaoOf(PollRecordAndStatus pollRecordAndStatus) {
        return new PollRecordAndStatusDao(
                pollRecordAndStatus.getLastModified()
        );
    }

    public static DatePollMetaInfDao metaInfDaoOf(DatePollMetaInf datePollMetaInf) {
        PollLifeCycleDao lifeCycleDao = PollLifeCycleDao.of(datePollMetaInf.getDatePollLifeCycle());
        return new DatePollMetaInfDao(
                datePollMetaInf.getDatePollDescription().getDescription(),
                datePollMetaInf.getDatePollLocation().getLocation(), lifeCycleDao);
    }

    private static Set<DatePollEntryDao> extractDatePollEntryDaos(DatePoll datePoll, DatePollDao datePollDao) {
        Set<DatePollEntry> datePollEntries = datePoll.getDatePollEntries();
        Set<DatePollEntryDao> datePollEntryDaos = new HashSet<>();
        for (DatePollEntry datePollEntry:datePollEntries) {
            DatePollEntryDao currentOption = DatePollDaoOfDatePoll.entryDaoOf(datePollEntry, datePollDao);
            datePollEntryDaos.add(currentOption);
        }
        return datePollEntryDaos;
    }

    private static Set<UserDao> extractDatePollUser(DatePoll datePoll) {
        Set<UserId> userIds = datePoll.getParticipants();
        Set<UserDao> userDaoSet = new HashSet<>();
        for (UserId currentUserId:userIds) {
            userDaoSet.add(DatePollDaoOfDatePoll.userDaoOf(currentUserId));
        }
        return userDaoSet;
    }
}
