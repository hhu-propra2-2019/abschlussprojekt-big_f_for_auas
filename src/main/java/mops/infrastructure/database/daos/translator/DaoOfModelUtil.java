package mops.infrastructure.database.daos.translator;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.group.Group;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
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
import java.util.stream.Collectors;

@SuppressWarnings({"PMD.TooManyMethods", "PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
public final class DaoOfModelUtil {
    @SuppressWarnings("checkstyle:RegexpSingleline")
    public static DatePollDao pollDaoOf(DatePoll poll, Set<GroupDao> groups) {
        final DatePollMetaInfDao metaInfDao =
                DaoOfModelUtil.metaInfDaoOf(poll.getMetaInf());

        final DatePollConfigDao configDao =
                DaoOfModelUtil.configDaoOf(poll.getConfig());

        final PollRecordAndStatusDao pollRecordAndStatusDao =
                DaoOfModelUtil.pollRecordAndStatusDaoOf(poll.getRecordAndStatus());

        final String newLink = DaoOfModelUtil.linkDaoOf(poll.getPollLink());

        final UserDao creator = DaoOfModelUtil.userDaoOf(poll.getCreator());

        final DatePollDao datePollDao = new DatePollDao();
        datePollDao.setLink(newLink);
        datePollDao.setMetaInfDao(metaInfDao);
        datePollDao.setConfigDao(configDao);
        datePollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        datePollDao.setCreatorUserDao(creator);
        datePollDao.setEntryDaos(extractDatePollEntryDaos(poll.getEntries(), datePollDao));
        //datePollDao.setUserDaos(extractUser(poll.getParticipants()));
        datePollDao.setGroupDaos(groups);
        return datePollDao;
    }

    public static QuestionPollDao pollDaoOf(QuestionPoll poll, Set<GroupDao> groupDaos) {
        final QuestionPollMetaInfDao metaInfDao =
                DaoOfModelUtil.metaInfDaoOf(poll.getMetaInf());

        final QuestionPollConfigDao configDao =
                DaoOfModelUtil.configDaoOf(poll.getConfig());

        final PollRecordAndStatusDao pollRecordAndStatusDao =
                DaoOfModelUtil.pollRecordAndStatusDaoOf(poll.getRecordAndStatus());

        final String newLink = DaoOfModelUtil.linkDaoOf(poll.getPollLink());

        final UserDao creator = userDaoOf(poll.getCreator());

        final QuestionPollDao questionPollDao = new QuestionPollDao();
        questionPollDao.setLink(newLink);
        questionPollDao.setMetaInfDao(metaInfDao);
        questionPollDao.setConfigDao(configDao);
        questionPollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        questionPollDao.setCreatorUserDao(creator);
        questionPollDao.setEntryDaos(extractQuestionPollEntryDaos(poll.getEntries(), questionPollDao));
        //questionPollDao.setUserDaos(extractUser(poll.getParticipants()));
        questionPollDao.setGroupDaos(groupDaos);
        return questionPollDao;
    }

    public static GroupDao groupDaoOf(Group group) {
        final GroupDao groupDao = new GroupDao();
        groupDao.setId(group.getId().getId());
        groupDao.setUserDaos(extractUser(group.getUser()));
        return groupDao;
    }

    public static Set<GroupDao> extractGroups(Set<Group> groups) {
        return groups.stream()
                .map(DaoOfModelUtil::groupDaoOf)
                .collect(Collectors.toSet());
    }

    private static DatePollEntryDao entryDaoOf(DatePollEntry datePollEntry, DatePollDao datePollDao) {
        final DatePollEntryDao entry = new DatePollEntryDao();
        entry.setDatePoll(datePollDao);
        entry.setTimespanDao(TimespanDao.of(datePollEntry.getSuggestedPeriod()));
        return entry;
    }

    private static QuestionPollEntryDao entryDaoOf(QuestionPollEntry questionPollEntry,
                                                   QuestionPollDao questionPollDao) {
        final QuestionPollEntryDao entry = new QuestionPollEntryDao();
        entry.setQuestionPoll(questionPollDao);
        entry.setEntryName(questionPollEntry.getTitle());
        return entry;
    }

    public static UserDao userDaoOf(UserId userId) {
        final UserDao user = new UserDao();
        user.setId(userId.getId());
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

    public static PollRecordAndStatusDao pollRecordAndStatusDaoOf(PollRecordAndStatus pollRecordAndStatus) {
        return new PollRecordAndStatusDao(
                pollRecordAndStatus.getLastModified()
        );
    }

    public static DatePollMetaInfDao metaInfDaoOf(DatePollMetaInf metaInf) {
        final TimespanDao timespanDao = TimespanDao.of(metaInf.getTimespan());
        return new DatePollMetaInfDao(
                metaInf.getTitle(),
                metaInf.getDescription().getDescriptionText(),
                metaInf.getLocation().getLocation(),
                timespanDao);
    }

    public static QuestionPollMetaInfDao metaInfDaoOf(QuestionPollMetaInf metaInf) {
        final TimespanDao timespanDao = TimespanDao.of(metaInf.getTimespan());
        return new QuestionPollMetaInfDao(
                metaInf.getTitle(),
                metaInf.getQuestion(),
                metaInf.getDescription().getDescriptionText(),
                timespanDao);
    }

    private static Set<DatePollEntryDao> extractDatePollEntryDaos(Set<DatePollEntry> datePollEntries,
                                                                  DatePollDao datePollDao) {
        final Set<DatePollEntryDao> datePollEntryDaos = new HashSet<>();
        for (final DatePollEntry datePollEntry : datePollEntries) {
            final DatePollEntryDao currentEntry = DaoOfModelUtil.entryDaoOf(datePollEntry, datePollDao);
            datePollEntryDaos.add(currentEntry);
        }
        return datePollEntryDaos;
    }

    private static Set<QuestionPollEntryDao> extractQuestionPollEntryDaos(
            Set<QuestionPollEntry> questionPollEntries, QuestionPollDao questionPollDao) {
        final Set<QuestionPollEntryDao> questionPollEntryDaos = new HashSet<>();
        for (final QuestionPollEntry questionPollEntry : questionPollEntries) {
            final QuestionPollEntryDao currentEntry = DaoOfModelUtil
                    .entryDaoOf(questionPollEntry, questionPollDao);
            questionPollEntryDaos.add(currentEntry);
        }
        return questionPollEntryDaos;
    }

    private static Set<UserDao> extractUser(Set<UserId> userIds) {
        final Set<UserDao> userDaoSet = new HashSet<>();
        for (final UserId currentUserId : userIds) {
            final UserDao currentUser = DaoOfModelUtil.userDaoOf(currentUserId);
            userDaoSet.add(currentUser);
        }
        return userDaoSet;
    }

    public static String linkDaoOf(PollLink link) {
        return link.getPollIdentifier();
    }

    /**
     * Wird nie instanziiert da util klasse.
     */
    private DaoOfModelUtil() {

    }
}
