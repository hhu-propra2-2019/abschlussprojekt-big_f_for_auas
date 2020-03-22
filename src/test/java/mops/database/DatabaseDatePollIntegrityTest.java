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
import mops.infrastructure.database.daos.translator.DaoOfModel;
import mops.infrastructure.database.repositories.DatePollEntryJpaRepository;
import mops.infrastructure.database.repositories.DatePollJpaRepository;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.UserJpaRepository;
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
        Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        UserId creator = new UserId("1234");
        DatePollConfig datePollConfig = new DatePollConfig();
        Set<UserId> participants = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            UserId newUser = new UserId(Integer.toString(i));
            participants.add(newUser);
        }
        PollLink datePollLink = new PollLink();
        Set<DatePollEntry> pollEntries = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            DatePollEntry entry = new DatePollEntry(
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
        System.out.println("[+] UserId: " + creator.getId());
        System.out.println("[+] Created DatePoll: " + datePoll.getPollLink().getPollIdentifier());
    }
    @Test
    public void saveOneDatePollDao() {
        DatePollDao datePollDao = DaoOfModel.pollDaoOf(datePoll);
        System.out.println("[+] UserId of DatePollDao: " + datePollDao.getCreatorUserDao().getId());
        String link = datePollDao.getLink();
        System.out.println("Output Link:" + datePollDao.getLink());
        datePollJpaRepository.save(datePollDao);
        DatePollDao datepollFound = datePollJpaRepository.findDatePollDaoByLink(link);
        System.out.println("[+] Found DatePoll: " + datepollFound.getLink());
        System.out.println("[+] Found DatePoll: " + datepollFound.getMetaInfDao().getLocation());
        assertThat(datepollFound.getLink()).isEqualTo(datepollFound.getLink());
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testUsersOfDatePollPresence() {
        DatePollDao datePollDao = DaoOfModel.pollDaoOf(datePoll);
        datePollJpaRepository.save(datePollDao);
        Set<UserDao> userDaoSet = userJpaRepository.findByDatePollSetContains(datePollDao);
        userDaoSet.forEach(userDao -> System.out.println("[+] Found User: " + userDao.getId()));
        assertThat(userDaoSet).hasSize(3);
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testDatePollEntryPresence() {
        DatePollDao datePollDao = DaoOfModel.pollDaoOf(datePoll);
        datePollJpaRepository.save(datePollDao);
        Set<DatePollEntryDao> datePollEntryDaoSet = datePollEntryJpaRepository.findByDatePoll(datePollDao);
        for (DatePollEntryDao datePollEntryDao : datePollEntryDaoSet) {
            System.out.println("[+] Found DatePollEntry: " + datePollEntryDao.getId());
        }
        assertThat(datePollEntryDaoSet).hasSize(3);
    }

    @Test
    public void testVotesForDatePollEntryAreZero() {
        DatePollDao datePollDao = DaoOfModel.pollDaoOf(datePoll);
        datePollJpaRepository.save(datePollDao);
        DatePollEntryDao datePollEntry = datePollDao.getEntryDaos().iterator().next();
        System.out.println("[+] DatePollEntryId: " + datePollEntry.getId());

        assertThat(datePollEntry.getUserVotesFor().size()).isEqualTo(0);
    }
}
