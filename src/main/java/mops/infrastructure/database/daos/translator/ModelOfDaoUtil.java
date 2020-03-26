package mops.infrastructure.database.daos.translator;

import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.datepoll.DatePollRecordAndStatus;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBallot;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import mops.domain.models.questionpoll.QuestionPollRecordAndStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.TimespanDao;
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
import java.util.stream.Collectors;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveImports", "PMD.LawOfDemeter",
        "PMD.DataflowAnomalyAnalysis"})
public final class ModelOfDaoUtil {
    public static DatePoll pollOf(DatePollDao pollDao) {
        final DatePollRecordAndStatus pollRecordAndStatus =
                ModelOfDaoUtil.datePollRecordAndStatusOf(pollDao.getPollRecordAndStatusDao());

        final DatePollMetaInf metaInf =
                ModelOfDaoUtil.metaInfOf(pollDao.getMetaInfDao());

        final User creator = ModelOfDaoUtil.userOf(pollDao.getCreatorUserDao());

        final DatePollConfig config =
                ModelOfDaoUtil.configOf(pollDao.getConfigDao());

        final Set<DatePollEntry> entries = extractDatePollEntries(pollDao.getEntryDaos());

        final Set<GroupId> groupIds = extractGroupIds(pollDao.getGroupDaos());
        //final Set<User> participants = extractUser(pollDao.getUserDaos());
        //final Set<UserId> participantIds = extractIds(participants);

        final Set<DatePollBallot> ballots = new HashSet<>();

        final PollLink newLink = ModelOfDaoUtil.linkOf(pollDao.getLink());

        return new DatePoll(
                pollRecordAndStatus,
                metaInf,
                creator.getId(),
                config,
                entries,
                groupIds,
                ballots,
                newLink
        );
    }

    public static Group groupOf(GroupDao dao) {
        return new Group(new GroupId(
                dao.getId()), dao.getTitle(), dao.getVisibility(), extractUserIds(extractUser(dao.getUserDaos())));
    }

    public static Set<Group> extractGroup(Set<GroupDao> groupDaos) {
        return groupDaos.stream()
                .map(ModelOfDaoUtil::groupOf)
                .collect(Collectors.toSet());
    }

    private static Set<UserId> extractUserIds(Set<User> user) {
        return user.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    public static QuestionPoll pollOf(QuestionPollDao pollDao) {
        final QuestionPollRecordAndStatus pollRecordAndStatus =
                ModelOfDaoUtil.questionPollRecordAndStatusOf(pollDao.getPollRecordAndStatusDao());

        final QuestionPollMetaInf metaInf =
                ModelOfDaoUtil.metaInfOf(pollDao.getMetaInfDao());

        final User creator = ModelOfDaoUtil.userOf(pollDao.getCreatorUserDao());

        final QuestionPollConfig config =
                ModelOfDaoUtil.configOf(pollDao.getConfigDao());

        final Set<QuestionPollEntry> entries = extractQuestionPollEntries(pollDao.getEntryDaos());

        //final Set<User> participants = extractUser(pollDao.getUserDaos());
        //final Set<UserId> participantIds = extractIds(participants);
        final Set<GroupId> groupIds = extractGroupIds(pollDao.getGroupDaos());

        final Set<QuestionPollBallot> ballots = new HashSet<>();

        final PollLink newLink = ModelOfDaoUtil.linkOf(pollDao.getLink());

        return new QuestionPoll(
                pollRecordAndStatus,
                metaInf,
                creator.getId(),
                config,
                entries,
                groupIds,
                ballots,
                newLink
        );
    }

    private static Set<GroupId> extractGroupIds(Set<GroupDao> groupDaos) {
        final Set<Group> groups = extractGroup(groupDaos);
        return groups.stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
    }

    public static DatePollRecordAndStatus datePollRecordAndStatusOf(PollRecordAndStatusDao dao) {
        final DatePollRecordAndStatus pollRecordAndStatus = new DatePollRecordAndStatus();
        pollRecordAndStatus.setLastModified(dao.getLastmodified());
        return pollRecordAndStatus;
    }
    public static QuestionPollRecordAndStatus questionPollRecordAndStatusOf(PollRecordAndStatusDao dao) {
        final QuestionPollRecordAndStatus pollRecordAndStatus = new QuestionPollRecordAndStatus();
        pollRecordAndStatus.setLastModified(dao.getLastmodified());
        return pollRecordAndStatus;
    }

    public static DatePollMetaInf metaInfOf(DatePollMetaInfDao dao) {
        final TimespanDao timespanDao = dao.getTimespan();
        return new DatePollMetaInf(
                dao.getTitle(),
                dao.getDescription(),
                dao.getLocation(),
                ModelOfDaoUtil.timespanOf(timespanDao)
        );
    }

    public static QuestionPollMetaInf metaInfOf(QuestionPollMetaInfDao dao) {
        final TimespanDao timespanDao = dao.getTimespan();
        return new QuestionPollMetaInf(
                dao.getTitle(),
                dao.getQuestion(),
                dao.getDescription(),
                ModelOfDaoUtil.timespanOf(timespanDao)
        );
    }

    public static User userOf(UserDao dao) {
        final UserId userId = new UserId(dao.getId());
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
        final Set<DatePollEntry> datePollEntries = new HashSet<>();
        for (final DatePollEntryDao daoEntry : daoEntries) {
            final DatePollEntry currentEntry = ModelOfDaoUtil.entryOf(daoEntry);
            datePollEntries.add(currentEntry);
        }
        return datePollEntries;
    }

    private static Set<QuestionPollEntry> extractQuestionPollEntries(Set<QuestionPollEntryDao> daoEntries) {
        final Set<QuestionPollEntry> questionPollEntries = new HashSet<>();
        for (final QuestionPollEntryDao daoEntry : daoEntries) {
            final QuestionPollEntry currentEntry = ModelOfDaoUtil.entryOf(daoEntry);
            questionPollEntries.add(currentEntry);
        }
        return questionPollEntries;
    }

    public static DatePollEntry entryOf(DatePollEntryDao dao) {
        final Timespan entryTimespan = timespanOf(dao.getTimespanDao());
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
        final Set<User> user = new HashSet<>();
        for (final UserDao dao : daoUser) {
            final User currentUser = ModelOfDaoUtil.userOf(dao);
            user.add(currentUser);
        }
        return user;
    }

    public static PollLink linkOf(String link) {
        return new PollLink(UUID.fromString(link));
    }

    /**
     * Wird nie instanziiert da utility Klasse.
     */
    private ModelOfDaoUtil() {
    }
}
