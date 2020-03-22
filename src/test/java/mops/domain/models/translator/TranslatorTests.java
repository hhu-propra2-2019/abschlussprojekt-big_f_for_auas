package mops.domain.models.translator;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.PollDescription;
import mops.domain.models.datepoll.DatePollLocation;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollConfigDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:WhitespaceAfter"})
public class TranslatorTests {
    @Test
    public void recordAndStatusTest() {
        LocalDateTime lastModified = LocalDateTime.of(2020, 3, 20, 12, 30);
        PollRecordAndStatusDao dao = new PollRecordAndStatusDao(lastModified);

        PollRecordAndStatus pollRecordAndStatus = ModelOfDaoUtil.pollRecordAndStatusOf(dao);

        assertThat(pollRecordAndStatus.getLastModified()).isEqualTo(lastModified);
    }

    @Test
    public void metaInfTest() {
        String title = "TestDAO";
        String description = "TestMetaInfDao";
        String location = "TestLocation";
        LocalDateTime startDate = LocalDateTime.of(2020, 3, 20, 12, 30);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 5, 5, 20);
        TimespanDao testLifeCycleDao = new TimespanDao(startDate, endDate);
        DatePollMetaInfDao dao = new DatePollMetaInfDao(
                title, description, location, testLifeCycleDao
        );

        DatePollMetaInf metaInf = ModelOfDaoUtil.metaInfOf(dao);

        assertThat(metaInf.getTitle()).isEqualTo(title);
        assertThat(metaInf.getDescription()).isEqualTo(new PollDescription(description));
        assertThat(metaInf.getLocation()).isEqualTo(new DatePollLocation(location));
        assertThat(metaInf.getTimespan().getStartDate()).isEqualTo(startDate);
        assertThat(metaInf.getTimespan().getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void userTest() {
        long userId = 1232454412L;
        UserDao dao = new UserDao();
        dao.setId(userId);

        User user = ModelOfDaoUtil.userOf(dao);

        assertThat(user.getId().getId()).isEqualTo(Long.toString(userId));
    }

    @Test
    public void configTest() {
        boolean priorityChoice = false;
        boolean anonymous = true;
        boolean openForOwnEntries = false;
        boolean open = true;
        boolean singleChoice = false;
        boolean voteIsEditable = true;
        DatePollConfigDao dao = new DatePollConfigDao(
                voteIsEditable, openForOwnEntries, singleChoice, priorityChoice, anonymous, open
        );

        DatePollConfig config = ModelOfDaoUtil.configOf(dao);

        assertThat(config.isVoteIsEditable()).isEqualTo(voteIsEditable);
        assertThat(config.isOpenForOwnEntries()).isEqualTo(openForOwnEntries);
        assertThat(config.isSingleChoice()).isEqualTo(singleChoice);
        assertThat(config.isPriorityChoice()).isEqualTo(priorityChoice);
        assertThat(config.isAnonymous()).isEqualTo(anonymous);
        assertThat(config.isOpen()).isEqualTo(open);
    }

    //TODO: add
    @Test
    public void entryTest() {
        return;
    }

    //TODO: add
    @Test
    public void ballotTest() {
        return;
    }

    @Test
    public void toDAOrecordAndStatusTest() {
        LocalDateTime lastModified = LocalDateTime.of(2020, 3, 20, 12, 30);
        PollRecordAndStatus pollRecordAndStatus = new PollRecordAndStatus();
        pollRecordAndStatus.setLastModified(lastModified);

        PollRecordAndStatusDao dao = DaoOfModelUtil.pollRecordAndStatusDaoOf(pollRecordAndStatus);

        assertThat(dao.getLastmodified()).isEqualTo(lastModified);
    }

    @Test
    public void toDAOmetaInfTest() {
        String title = "Test";
        String description = "TestMetaInf";
        String location = "TestLocation";
        LocalDateTime startDate = LocalDateTime.of(2020,3,20,12, 30);
        LocalDateTime endDate = LocalDateTime.of(2020,6,5,5, 20);
        Timespan testLifeCycle = new Timespan(
                startDate,
                endDate
        );
        DatePollMetaInf metaInf = new DatePollMetaInf(
                title, description, location, testLifeCycle
        );

        DatePollMetaInfDao metaInfDao = DaoOfModelUtil.metaInfDaoOf(metaInf);

        assertThat(metaInfDao.getTitle()).isEqualTo(title);
        assertThat(metaInfDao.getDescription()).isEqualTo(description);
        assertThat(metaInfDao.getLocation()).isEqualTo(location);
        assertThat(metaInfDao.getTimespan().getStartDate()).isEqualTo(startDate);
        assertThat(metaInfDao.getTimespan().getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void toDAOuserTest() {
        long id = 1232454412L;
        UserId userId = new UserId(Long.toString(id));

        UserDao dao = DaoOfModelUtil.userDaoOf(userId);

        assertThat(dao.getId()).isEqualTo(id);
    }

    @Test
    public void toDAOconfigTest() {
        boolean voteIsEditable = true;
        boolean openForOwnEntries = false;
        boolean singleChoice = false;
        boolean priorityChoice = false;
        boolean anonymous = true;
        boolean open = true;
        DatePollConfig config = new DatePollConfig(
                voteIsEditable, openForOwnEntries, singleChoice, priorityChoice, anonymous, open
        );

        DatePollConfigDao dao = DaoOfModelUtil.configDaoOf(config);

        assertThat(dao.isVoteIsEditable()).isEqualTo(voteIsEditable);
        assertThat(dao.isOpenForOwnEntries()).isEqualTo(openForOwnEntries);
        assertThat(dao.isSingleChoice()).isEqualTo(singleChoice);
        assertThat(dao.isPriorityChoice()).isEqualTo(priorityChoice);
        assertThat(dao.isAnonymous()).isEqualTo(anonymous);
        assertThat(dao.isOpen()).isEqualTo(open);
    }

    @Test
    public void toDAOentryTest() {
        return;
    }

    @Test
    public void toDAOballotTest() {
        return;
    }
}
