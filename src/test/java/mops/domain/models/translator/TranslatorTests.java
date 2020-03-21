package mops.domain.models.translator;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollDescription;
import mops.domain.models.datepoll.DatePollLocation;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollConfigDao;
import mops.infrastructure.database.daos.datepoll.DatePollMetaInfDao;
import mops.infrastructure.database.daos.translator.DaoOfModel;
import mops.infrastructure.database.daos.translator.ModelOfDao;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

public class TranslatorTests {
    //TODO: add
    @Test
    public void recordAndStatusTest() {
        return;
    }

    @Test
    public void metaInfTest() {
        String title = "TestDAO";
        String description = "TestMetaInfDao";
        String location = "TestLocation";
        LocalDateTime startDate = LocalDateTime.of(2020,3,20,12, 30);
        LocalDateTime endDate = LocalDateTime.of(2020,6,5,5, 20);
        TimespanDao testLifeCycleDao = new TimespanDao(
                startDate,
                endDate
        );
        DatePollMetaInfDao dao = new DatePollMetaInfDao(
                title, description, location, testLifeCycleDao
        );

        DatePollMetaInf metaInf = ModelOfDao.metaInfOf(dao);

        assertThat(metaInf.getTitle()).isEqualTo(title);
        assertThat(metaInf.getDatePollDescription()).isEqualTo(new DatePollDescription(description));
        assertThat(metaInf.getDatePollLocation()).isEqualTo(new DatePollLocation(location));
        assertThat(metaInf.getDatePollLifeCycle().getStartDate()).isEqualTo(startDate);
        assertThat(metaInf.getDatePollLifeCycle().getEndDate()).isEqualTo(endDate);
    }

    //TODO: geht so nicht wegen Id !!!
    @Test
    public void userTest() {
        long userId = 1232454412L;
        UserDao dao = new UserDao();
        dao.setId(userId);

        User user = ModelOfDao.userOf(dao);

        assertThat(user.getId()).isEqualTo(new UserId(Long.toString(userId)));
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

        DatePollConfig config = ModelOfDao.configOf(dao);

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
    public void toDAO_recordAndStatusTest() {
        return;
    }

    @Test
    public void toDAO_metaInfTest() {
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

        DatePollMetaInfDao metaInfDao = DaoOfModel.metaInfDaoOf(metaInf);

        assertThat(metaInfDao.getTitle()).isEqualTo(title);
        assertThat(metaInfDao.getDescription()).isEqualTo(description);
        assertThat(metaInfDao.getLocation()).isEqualTo(location);
        assertThat(metaInfDao.getTimespan().getStartDate()).isEqualTo(startDate);
        assertThat(metaInfDao.getTimespan().getEndDate()).isEqualTo(endDate);
    }

    //TODO: geht so nicht wegen Id !!!
    @Test
    public void toDAO_userTest() {
        return;
    }

    @Test
    public void toDAO_configTest() {
        boolean voteIsEditable = true;
        boolean openForOwnEntries = false;
        boolean singleChoice = false;
        boolean priorityChoice = false;
        boolean anonymous = true;
        boolean open = true;
        DatePollConfig config = new DatePollConfig(
                voteIsEditable, openForOwnEntries, singleChoice, priorityChoice, anonymous, open
        );

        DatePollConfigDao dao = DaoOfModel.configDaoOf(config);

        assertThat(dao.isVoteIsEditable()).isEqualTo(voteIsEditable);
        assertThat(dao.isOpenForOwnEntries()).isEqualTo(openForOwnEntries);
        assertThat(dao.isSingleChoice()).isEqualTo(singleChoice);
        assertThat(dao.isPriorityChoice()).isEqualTo(priorityChoice);
        assertThat(dao.isAnonymous()).isEqualTo(anonymous);
        assertThat(dao.isOpen()).isEqualTo(open);
    }

    @Test
    public void toDAO_entryTest() {
        return;
    }

    @Test
    public void toDAO_ballotTest() {
        return;
    }
}
