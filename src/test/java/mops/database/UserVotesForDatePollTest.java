package mops.database;

import java.util.stream.IntStream;
import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DomainGroupRepository;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDao;
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDaoKey;
import mops.infrastructure.database.daos.datepoll.PriorityTypeEnum;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
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
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports"})
public class UserVotesForDatePollTest {
    private transient DatePoll datePoll;
    @Autowired
    private transient DomainGroupRepository domainGroupRepository;
    @Autowired
    private transient PriorityChoiceJpaRepository priorityChoiceJpaRepository;
    @Autowired
    private transient DatePollJpaRepository datePollJpaRepository;
    @Autowired
    private transient UserJpaRepository userJpaRepository;
    @Autowired
    private transient DatePollEntryJpaRepository datePollEntryJpaRepository;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupDatePollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        final UserId creator = new UserId("1234");
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();
        final Set<UserId> participants = new HashSet<>();
        IntStream.range(0, 3).forEach(i -> participants.add(new UserId(Integer.toString(i))));
        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, 3).forEach(i -> pollEntries.add(new DatePollEntry(
            new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        final Group group = new Group(new GroupId("1"), participants);
        domainGroupRepository.save(group);
        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participatingGroups(Set.of(group.getId()))
                .datePollLink(datePollLink)
                .build();
        datePollJpaRepository.save(DaoOfModelUtil.pollDaoOf(datePoll,
                DaoOfModelUtil.extractGroups(Set.of(group))));
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    public void testUserVotesForDatePollEntry() {
        final DatePollDao datePollDao = datePollJpaRepository.
                findDatePollDaoByLink(datePoll.getPollLink().getPollIdentifier());
        final DatePollEntryDao datePollEntryDao = datePollDao.getEntryDaos().iterator().next();
        datePollDao.getGroupDaos().iterator().next();
        datePollEntryDao.getUserVotesFor().add(userDao);
        userDao.getDatePollEntrySet().add(datePollEntryDao);
        datePollEntryJpaRepository.save(datePollEntryDao);
        userJpaRepository.save(userDao);
        final Long number = userJpaRepository.countByDatePollEntrySetContaining(datePollEntryDao);

        assertThat(datePollDao).isNotNull();
        assertThat(number).isEqualTo(1);
    }

    @Test
    public void testUserPriorityForDatePollEntryIsNotAppreciated() {
        //Get DatePoll and datePollEntryDao
        final DatePollDao datePollDao = datePollJpaRepository.
                findDatePollDaoByLink(datePoll.getPollLink().getPollIdentifier());
        final DatePollEntryDao datePollEntryDao = datePollDao.getEntryDaos().iterator().next();
        final UserDao userDao = datePollDao.getUserDaos().iterator().next();

        PriorityChoiceDao priorityChoiceDao = new PriorityChoiceDao();
        priorityChoiceDao.setDatePollEntry(datePollEntryDao);
        priorityChoiceDao.setParticipant(userDao);
        priorityChoiceDao.setDatePollPriority(PriorityTypeEnum.NOT_APPRECIATED);
        final PriorityChoiceDaoKey priorityChoiceDaoKey = new PriorityChoiceDaoKey();
        priorityChoiceDao.setId(priorityChoiceDaoKey);
        priorityChoiceJpaRepository.save(priorityChoiceDao);
        priorityChoiceDao = priorityChoiceJpaRepository.getOne(priorityChoiceDaoKey);
        assertThat(priorityChoiceDao.getDatePollPriority())
                .isEqualByComparingTo(PriorityTypeEnum.NOT_APPRECIATED);
    }
}
