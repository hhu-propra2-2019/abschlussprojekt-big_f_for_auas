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
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDao;
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDaoKey;
import mops.infrastructure.database.daos.datepoll.PriorityTypeEnum;
import mops.infrastructure.database.daos.translator.DaoOfModel;
import mops.infrastructure.database.repositories.interfaces.DatePollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.PriorityChoiceJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
public class UserVotesForDatePollTest {
    @Autowired
    private PriorityChoiceJpaRepository priorityChoiceJpaRepository;
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
        datePollJpaRepository.save(DaoOfModel.pollDaoOf(datePoll));
    }

    @Test
    public void testUserVotesForDatePollEntry() {
        DatePollDao datePollDao = datePollJpaRepository.
                findDatePollDaoByLink(datePoll.getPollLink().getPollIdentifier());
        DatePollEntryDao datePollEntryDao = datePollDao.getEntryDaos().iterator().next();
        UserDao userDao = datePollDao.getUserDaos().iterator().next();
        datePollEntryDao.getUserVotesFor().add(userDao);
        userDao.getDatePollEntrySet().add(datePollEntryDao);
        datePollEntryJpaRepository.save(datePollEntryDao);
        userJpaRepository.save(userDao);
        Long number = userJpaRepository.countByDatePollEntrySetContaining(datePollEntryDao);
        assertThat(datePollDao).isNotNull();
        assertThat(number).isEqualTo(1);
    }
    @Test
    public void testUserPriorityForDatePollEntryIsNotAppreciated() {
        //Get DatePoll and datePollEntryDao
        DatePollDao datePollDao = datePollJpaRepository.
                findDatePollDaoByLink(datePoll.getPollLink().getPollIdentifier());
        DatePollEntryDao datePollEntryDao = datePollDao.getEntryDaos().iterator().next();
        UserDao userDao = datePollDao.getUserDaos().iterator().next();

        PriorityChoiceDao priorityChoiceDao = new PriorityChoiceDao();
        priorityChoiceDao.setDatePollEntry(datePollEntryDao);
        priorityChoiceDao.setParticipant(userDao);
        priorityChoiceDao.setDatePollPriority(PriorityTypeEnum.NOT_APPRECIATED);
        PriorityChoiceDaoKey priorityChoiceDaoKey = new PriorityChoiceDaoKey();
        priorityChoiceDao.setId(priorityChoiceDaoKey);
        priorityChoiceJpaRepository.save(priorityChoiceDao);
        priorityChoiceDao = priorityChoiceJpaRepository.getOne(priorityChoiceDaoKey);
        assertThat(priorityChoiceDao.getDatePollPriority())
                .isEqualByComparingTo(PriorityTypeEnum.NOT_APPRECIATED);
    }
}
