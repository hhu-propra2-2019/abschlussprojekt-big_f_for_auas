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

@SuppressWarnings({"checkstyle:MagicNumber", "checkstyle:WhitespaceAfter",
    "PMD.LawOfDemeter", "PMD.AtLeastOneConstructor","PMD.TooManyMethods"})
// zu tooManyMethods: lieber tests für die einzelnen translation der jeweiligen daos schreiben
public class TranslatorTests {
    @Test
    public void recordAndStatusTest() {
        final LocalDateTime lastModified = LocalDateTime.of(2020, 3, 20, 12, 30);
        final PollRecordAndStatusDao dao = new PollRecordAndStatusDao(lastModified);

        final PollRecordAndStatus pollRecordAndStatus = ModelOfDaoUtil.pollRecordAndStatusOf(dao);

        assertThat(pollRecordAndStatus.getLastModified()).isEqualTo(lastModified);
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    //da die Strings innerhalb der metainf noch gekapselt sind ist hier das testen etwas umständlicher
    public void metaInfTest() {
        final String title = "TestDAO";
        final String description = "TestMetaInfDao";
        final String location = "TestLocation";
        final LocalDateTime startDate = LocalDateTime.of(2020, 3, 20, 12, 30);
        final LocalDateTime endDate = LocalDateTime.of(2020, 6, 5, 5, 20);
        final TimespanDao testLifeCycleDao = new TimespanDao(startDate, endDate);
        final DatePollMetaInfDao dao = new DatePollMetaInfDao(
                title, description, location, testLifeCycleDao
        );

        final DatePollMetaInf metaInf = ModelOfDaoUtil.metaInfOf(dao);

        assertThat(metaInf.getTitle()).isEqualTo(title);
        assertThat(metaInf.getDescription()).isEqualTo(new PollDescription(description));
        assertThat(metaInf.getLocation()).isEqualTo(new DatePollLocation(location));
        assertThat(metaInf.getTimespan().getStartDate()).isEqualTo(startDate);
        assertThat(metaInf.getTimespan().getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void userTest() {
        final long userId = 1L;
        final UserDao dao = new UserDao();
        dao.setId(Long.toString(userId));

        final User user = ModelOfDaoUtil.userOf(dao);

        assertThat(user.getId().getId()).isEqualTo(Long.toString(userId));
    }

    @Test
    public void configTest() {
        final boolean priorityChoice = false;
        final boolean anonymous = true;
        final boolean openForOwnEntries = false;
        final boolean open = true;
        final boolean singleChoice = false;
        final boolean voteIsEditable = true;
        final DatePollConfigDao dao = new DatePollConfigDao(
                voteIsEditable, openForOwnEntries, singleChoice, priorityChoice, anonymous, open
        );

        final DatePollConfig config = ModelOfDaoUtil.configOf(dao);

        assertThat(config).isEqualToComparingFieldByField(dao);
    }

    //TODO: add
    @Test
    @SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert", "PMD.AvoidDuplicateLiterals"})
    // not yet implemented, duplicate Literal is PMD Suppression
    public void entryTest() {
        //
    }

    //TODO: add
    @Test
    @SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert"}) // not yet implemented
    public void ballotTest() {
        //
    }

    @Test
    public void recordAndStatusToDAOTest() {
        final LocalDateTime lastModified = LocalDateTime.of(2020, 3, 20, 12, 30);
        final PollRecordAndStatus pollRecordAndStatus = new PollRecordAndStatus();
        pollRecordAndStatus.setLastModified(lastModified);

        final PollRecordAndStatusDao dao = DaoOfModelUtil.pollRecordAndStatusDaoOf(pollRecordAndStatus);

        assertThat(dao.getLastmodified()).isEqualTo(lastModified);
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    public void metaInfToDAOTest() {
        final String title = "Test";
        final String description = "TestMetaInf";
        final String location = "TestLocation";
        final LocalDateTime startDate = LocalDateTime.of(2020,3,20,12, 30);
        final LocalDateTime endDate = LocalDateTime.of(2020,6,5,5, 20);
        final Timespan testLifeCycle = new Timespan(
                startDate,
                endDate
        );
        final DatePollMetaInf metaInf = new DatePollMetaInf(
                title, description, location, testLifeCycle
        );

        final DatePollMetaInfDao metaInfDao = DaoOfModelUtil.metaInfDaoOf(metaInf);

        assertThat(metaInfDao.getTitle()).isEqualTo(title);
        assertThat(metaInfDao.getDescription()).isEqualTo(description);
        assertThat(metaInfDao.getLocation()).isEqualTo(location);
        assertThat(metaInfDao.getTimespan().getStartDate()).isEqualTo(startDate);
        assertThat(metaInfDao.getTimespan().getEndDate()).isEqualTo(endDate);
    }

    @Test
    public void userToDAOTest() {
        final long id = 1L;
        final UserId userId = new UserId(Long.toString(id));

        final UserDao dao = DaoOfModelUtil.userDaoOf(userId);

        assertThat(dao.getId()).isEqualTo(id);
    }

    @Test
    public void configToDAOTest() {
        final boolean voteIsEditable = true;
        final boolean openForOwnEntries = false;
        final boolean singleChoice = false;
        final boolean priorityChoice = false;
        final boolean anonymous = true;
        final boolean open = true;
        final DatePollConfig config = new DatePollConfig(
                voteIsEditable, openForOwnEntries, singleChoice, priorityChoice, anonymous, open
        );

        final DatePollConfigDao dao = DaoOfModelUtil.configDaoOf(config);

        assertThat(dao).isEqualToComparingFieldByField(config);
    }

    @Test
    @SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert"}) // not yet implemented
    public void entryToDAOTest() {
        //
    }

    @Test
    @SuppressWarnings({"PMD.JUnitTestsShouldIncludeAssert"}) // not yet implemented
    public void ballotToDAOTest() {
        //
    }
}
