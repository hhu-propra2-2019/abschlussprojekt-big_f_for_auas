package mops.database;

import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.repositories.interfaces.DatePollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
public class DatabaseDatePollIntegrityTest {
    @Autowired
    private DatePollRepositoryImpl datePollRepository;
    @Autowired
    private DatePollJpaRepository datePollJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DatePollEntryJpaRepository datePollEntryJpaRepository;
    private DatePoll datePoll;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupDatePollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        final UserId creator = new UserId("1234");
        final DatePollConfig datePollConfig = new DatePollConfig();
        final Set<UserId> participants = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            final UserId newUser = new UserId(Integer.toString(i));
            participants.add(newUser);
        }
        final PollLink datePollLink = new PollLink();
        final Set<DatePollEntry> pollEntries = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            final DatePollEntry entry = new DatePollEntry(
                    new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
            );
            pollEntries.add(entry);
        }

        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participants(participants)
                .datePollLink(datePollLink)
                .build();
    }

    @Test
    public void saveOneDatePollDao() {
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll);
        final String link = datePollDao.getLink();
        datePollJpaRepository.save(datePollDao);
        final DatePollDao datepollFound = datePollJpaRepository.findDatePollDaoByLink(link);

        assertThat(datepollFound.getLink()).isEqualTo(datepollFound.getLink());
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testUsersOfDatePollPresence() {
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll);
        datePollJpaRepository.save(datePollDao);
        final Set<UserDao> userDaoSet = userJpaRepository.findByDatePollSetContains(datePollDao);

        assertThat(userDaoSet).hasSize(3);
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testDatePollEntryPresence() {
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll);
        datePollJpaRepository.save(datePollDao);
        final Set<DatePollEntryDao> datePollEntryDaoSet = datePollEntryJpaRepository.findByDatePoll(datePollDao);

        assertThat(datePollEntryDaoSet).hasSize(3);
    }

    @Test
    public void testVotesForDatePollEntryAreZero() {
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll);
        datePollJpaRepository.save(datePollDao);
        final DatePollEntryDao datePollEntry = datePollDao.getEntryDaos().iterator().next();

        assertThat(datePollEntry.getUserVotesFor().size()).isEqualTo(0);
    }
}
