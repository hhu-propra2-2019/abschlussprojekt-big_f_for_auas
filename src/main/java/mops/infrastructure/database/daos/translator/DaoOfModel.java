package mops.infrastructure.database.daos.translator;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollConfigDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollConfigDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollMetaInfDao;

import java.util.HashSet;
import java.util.Set;

public class DaoOfModel {
    @SuppressWarnings("checkstyle:RegexpSingleline")
    public static DatePollDao pollDaoOf(DatePoll poll) {
        DatePollMetaInfDao metaInfDao =
                DaoOfModel.metaInfDaoOf(poll.getMetaInf());

        DatePollConfigDao configDao =
                DaoOfModel.configDaoOf(poll.getConfig());

        PollRecordAndStatusDao pollRecordAndStatusDao =
                DaoOfModel.pollRecordAndStatusDaoOf(poll.getRecordAndStatus());

        String newLink = DaoOfModel.linkDaoOf(poll.getPollLink());

        UserDao creator = userDaoOf(poll.getCreator());

        DatePollDao datePollDao = new DatePollDao();
        datePollDao.setLink(newLink);
        datePollDao.setMetaInfDao(metaInfDao);
        datePollDao.setConfigDao(configDao);
        datePollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        datePollDao.setCreatorUserDao(creator);
        datePollDao.setEntryDaos(extractDatePollEntryDaos(poll.getEntries(), datePollDao));
        datePollDao.setUserDaos(extractUser(poll.getParticipants()));
        return datePollDao;
    }

    public static QuestionPollDao pollDaoOf(QuestionPoll poll) {
        QuestionPollMetaInfDao metaInfDao =
                DaoOfModel.metaInfDaoOf(poll.getMetaInf());

        QuestionPollConfigDao configDao =
                DaoOfModel.configDaoOf(poll.getConfig());

        //TODO: pollRecordAndStatus?
        //PollRecordAndStatusDao pollRecordAndStatusDao =
        //        DaoOfModel.pollRecordAndStatusDaoOf(poll.getRecordAndStatus());

        String newLink = DaoOfModel.linkDaoOf(poll.getPollLink());

        UserDao creator = userDaoOf(poll.getCreator());

        QuestionPollDao questionPollDao = new QuestionPollDao();
        questionPollDao.setLink(newLink);
        questionPollDao.setMetaInfDao(metaInfDao);
        questionPollDao.setConfigDao(configDao);
        //questionPollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        questionPollDao.setCreatorUserDao(creator);
        questionPollDao.setEntryDaos(extractQuestionPollEntryDaos(poll.getEntries(), questionPollDao));
        //TODO: add participants
        //questionPollDao.setUserDaos(extractUser(poll.getParticipants()));
        return questionPollDao;
    }

    private static DatePollEntryDao entryDaoOf(DatePollEntry datePollEntry, DatePollDao datePollDao) {
        DatePollEntryDao entry = new DatePollEntryDao();
        entry.setDatePoll(datePollDao);
        entry.setTimespanDao(TimespanDao.of(datePollEntry.getSuggestedPeriod()));
        return entry;
    }

    private static QuestionPollEntryDao entryDaoOf(QuestionPollEntry questionPollEntry, QuestionPollDao questionPollDao) {
        QuestionPollEntryDao entry = new QuestionPollEntryDao();
        //TODO: set questionPoll?
        entry.setEntryName(questionPollEntry.getTitle());
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

    public static QuestionPollConfigDao configDaoOf(QuestionPollConfig questionPollConfig) {
        return new QuestionPollConfigDao(
                questionPollConfig.isSingleChoice(),
                questionPollConfig.isAnonymous(),
                questionPollConfig.isOpen()
        );
    }

    private static PollRecordAndStatusDao pollRecordAndStatusDaoOf(PollRecordAndStatus pollRecordAndStatus) {
        return new PollRecordAndStatusDao(
                pollRecordAndStatus.getLastModified()
        );
    }

    public static DatePollMetaInfDao metaInfDaoOf(DatePollMetaInf metaInf) {
        TimespanDao timespanDao = TimespanDao.of(metaInf.getTimespan());
        return new DatePollMetaInfDao(
                metaInf.getTitle(),
                metaInf.getDescription().getDescriptionText(),
                metaInf.getLocation().getLocation(),
                timespanDao);
    }

    public static QuestionPollMetaInfDao metaInfDaoOf(QuestionPollMetaInf metaInf) {
        TimespanDao timespanDao = TimespanDao.of(metaInf.getTimespan());
        return new QuestionPollMetaInfDao(
                metaInf.getTitle(),
                metaInf.getQuestion(),
                metaInf.getDescription().getDescriptionText(),
                timespanDao);
    }

    private static Set<DatePollEntryDao> extractDatePollEntryDaos(Set<DatePollEntry> datePollEntries, DatePollDao datePollDao) {
        Set<DatePollEntryDao> datePollEntryDaos = new HashSet<>();
        for (DatePollEntry datePollEntry : datePollEntries) {
            DatePollEntryDao currentEntry = DaoOfModel.entryDaoOf(datePollEntry, datePollDao);
            datePollEntryDaos.add(currentEntry);
        }
        return datePollEntryDaos;
    }

    private static Set<QuestionPollEntryDao> extractQuestionPollEntryDaos(Set<QuestionPollEntry> questionPollEntries, QuestionPollDao questionPollDao) {
        Set<QuestionPollEntryDao> questionPollEntryDaos = new HashSet<>();
        for (QuestionPollEntry questionPollEntry : questionPollEntries) {
            QuestionPollEntryDao currentEntry = DaoOfModel.entryDaoOf(questionPollEntry, questionPollDao);
            questionPollEntryDaos.add(currentEntry);
        }
        return questionPollEntryDaos;
    }

    private static Set<UserDao> extractUser(Set<UserId> userIds) {
        Set<UserDao> userDaoSet = new HashSet<>();
        for (UserId currentUserId : userIds) {
            UserDao currentUser = DaoOfModel.userDaoOf(currentUserId);
            userDaoSet.add(currentUser);
        }
        return userDaoSet;
    }

    public static String linkDaoOf(PollLink link) {
        return link.getPollIdentifier();
    }
}
