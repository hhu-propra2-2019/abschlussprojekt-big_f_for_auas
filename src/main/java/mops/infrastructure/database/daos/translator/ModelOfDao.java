package mops.infrastructure.database.daos.translator;

import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.datepoll.DatePollRecordAndStatus;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBallot;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import mops.domain.models.user.User;
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
import java.util.UUID;

public class ModelOfDao {
    public static DatePoll pollOf(DatePollDao pollDao){
        PollRecordAndStatus pollRecordAndStatus =
                ModelOfDao.pollRecordAndStatusOf(pollDao.getPollRecordAndStatusDao());

        DatePollMetaInf metaInf =
                ModelOfDao.metaInfOf(pollDao.getMetaInfDao());

        User creator = ModelOfDao.userOf(pollDao.getCreatorUserDao());

        DatePollConfig config =
                ModelOfDao.configOf(pollDao.getConfigDao());

        Set<DatePollEntry> entries = extractDatePollEntries(pollDao.getEntryDaos());

        Set<User> participants = extractUser(pollDao.getUserDaos());
        Set<UserId> participantIds = extractIds(participants);

        //TODO: add
        Set<DatePollBallot> ballots = null;

        PollLink newLink = ModelOfDao.linkOf(pollDao.getLink());

        DatePoll datePoll = new DatePoll(
                (DatePollRecordAndStatus) pollRecordAndStatus,
                metaInf,
                creator.getId(),
                config,
                entries,
                participantIds,
                ballots,
                newLink
        );

        return datePoll;
    }
    public static QuestionPoll pollOf(QuestionPollDao pollDao) {
        PollRecordAndStatus pollRecordAndStatus =
                ModelOfDao.pollRecordAndStatusOf((pollDao.getPollRecordAndStatusDao()));

        QuestionPollMetaInf metaInf =
                ModelOfDao.metaInfOf(pollDao.getMetaInfDao());

        User creator = ModelOfDao.userOf(pollDao.getCreatorUserDao());

        QuestionPollConfig config =
                ModelOfDao.configOf(pollDao.getConfigDao());

        Set<QuestionPollEntry> entries = extractQuestionPollEntries(pollDao.getEntryDaos());

        Set<User> participants = extractUser(pollDao.getUserDaos());
        Set<UserId> participantIds = extractIds(participants);

        //TODO: add (same for Datepoll and Questionpoll ???)
        Set<QuestionPollBallot> ballots = null;

        //TODO: need method to set specific link
        PollLink newLink = ModelOfDao.linkOf(pollDao.getLink());

        //TODO: add pollRecordAndStatus
        QuestionPoll questionPoll = new QuestionPoll(
                pollRecordAndStatus,
                metaInf,
                creator.getId(),
                config,
                entries,
                participantIds,
                ballots,
                newLink
        );

        return questionPoll;
    }

    public static PollRecordAndStatus pollRecordAndStatusOf(PollRecordAndStatusDao dao) {
        PollRecordAndStatus pollRecordAndStatus = new PollRecordAndStatus();
        pollRecordAndStatus.setLastModified(dao.getLastmodified());
        return pollRecordAndStatus;
    }

    public static DatePollMetaInf metaInfOf(DatePollMetaInfDao dao) {
        TimespanDao timespanDao = dao.getTimespan();
        return new DatePollMetaInf(
                dao.getTitle(),
                dao.getDescription(),
                dao.getLocation(),
                ModelOfDao.timespanOf(timespanDao)
        );
    }

    public static QuestionPollMetaInf metaInfOf(QuestionPollMetaInfDao dao) {
        TimespanDao timespanDao = dao.getTimespan();
        return new QuestionPollMetaInf(
                dao.getTitle(),
                dao.getQuestion(),
                dao.getDescription(),
                ModelOfDao.timespanOf(timespanDao)
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

    public static QuestionPollConfig configOf(QuestionPollConfigDao dao) {
        return new QuestionPollConfig(
                dao.isSingleChoice(),
                dao.isAnonymous(),
                dao.isOpen()
        );
    }

    private static Set<DatePollEntry> extractDatePollEntries(Set<DatePollEntryDao> daoEntries) {
        Set<DatePollEntry> datePollEntries = new HashSet<>();
        for (DatePollEntryDao daoEntry : daoEntries) {
            DatePollEntry currentEntry = ModelOfDao.entryOf(daoEntry);
            datePollEntries.add(currentEntry);
        }
        return datePollEntries;
    }

    private static Set<QuestionPollEntry> extractQuestionPollEntries(Set<QuestionPollEntryDao> daoEntries) {
        Set<QuestionPollEntry> questionPollEntries = new HashSet<>();
        for (QuestionPollEntryDao daoEntry : daoEntries) {
            QuestionPollEntry currentEntry = ModelOfDao.entryOf(daoEntry);
            questionPollEntries.add(currentEntry);
        }
        return questionPollEntries;
    }

    public static DatePollEntry entryOf(DatePollEntryDao dao) {
        Timespan entryTimespan = timespanOf(dao.getTimespanDao());
        //TODO: add votes
        return new DatePollEntry(entryTimespan);
    }

    public static QuestionPollEntry entryOf(QuestionPollEntryDao dao) {
        return new QuestionPollEntry(dao.getEntryName());
    }

    public static Timespan timespanOf(TimespanDao dao) {
        return new Timespan(dao.getStartDate(), dao.getEndDate());
    }

    private static Set<User> extractUser(Set<UserDao> daoUser) {
        Set<User> user = new HashSet<>();
        for (UserDao dao : daoUser) {
            User currentUser = ModelOfDao.userOf(dao);
            user.add(currentUser);
        }
        return user;
    }

    private static Set<UserId> extractIds(Set<User> user) {
        Set<UserId> userIds = new HashSet<>();
        for (User u : user) {
            UserId id = u.getId();
            userIds.add(id);
        }
        return userIds;
    }

    public static PollLink linkOf(String link) {
        return new PollLink(UUID.fromString(link));
    }

}
