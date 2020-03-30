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
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.domain.repositories.GroupRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDao;
import mops.infrastructure.database.daos.datepoll.PriorityChoiceDaoKey;
import mops.infrastructure.database.daos.datepoll.PriorityTypeEnum;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.DatePollEntryRepositoryManager;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
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
    private transient Group group;
    private transient PollLink targetPollLink;
    @Autowired
    private transient GroupRepository groupRepository;
    @Autowired
    private transient PriorityChoiceJpaRepository priorityChoiceJpaRepository;
    @Autowired
    private transient DatePollJpaRepository datePollJpaRepository;
    @Autowired
    private transient UserJpaRepository userJpaRepository;
    @Autowired
    private transient DatePollRepositoryImpl datePollRepository;
    @Autowired
    private transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupDatePollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        final User creator = new User(new UserId("1234"));
        final DatePollConfig datePollConfig = new DatePollConfig();
        targetPollLink = new PollLink();
        final Set<User> participants = new HashSet<>();
        IntStream.range(0, 3).forEach(i -> participants.add(new User(new UserId(Integer.toString(i)))));
        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, 1).forEach(i -> pollEntries.add(new DatePollEntry(
            new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        group = new Group(
                new GroupMetaInf(new GroupId("1"), "Testgruppe", GroupVisibility.PRIVATE), participants);
        groupRepository.save(group);
        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participatingGroups(Set.of(group.getMetaInf().getId()))
                .datePollLink(targetPollLink)
                .build();
        datePollJpaRepository.save(DaoOfModelUtil.pollDaoOf(datePoll,
                DaoOfModelUtil.extractGroups(Set.of(group))));
    }
    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    public void testUserVotesForDatePollEntry() {
        final DatePollDao datePollDao = datePollJpaRepository.
                findDatePollDaoByLink(datePoll.getPollLink().getLinkUUIDAsString());
        //Funktioniert nur, da genau ein DatePollEntry im Set ist, ansonsten koennen die Keys verschieden sein.
        final DatePollEntryDao targetDatePollEntryDao = datePollDao.getEntryDaos().iterator().next();
        final GroupDao targetGroup = datePollDao.getGroupDaos().iterator().next();
        final UserDao targetUser = targetGroup.getUserDaos().iterator().next();
        final UserId targetUserId = ModelOfDaoUtil.userOf(targetUser).getId();
        datePollEntryRepositoryManager.userVotesForDatePollEntry(
                targetUserId,
                targetPollLink,
                ModelOfDaoUtil.entryOf(targetDatePollEntryDao));
        datePollEntryRepositoryManager.save(targetDatePollEntryDao);
        userJpaRepository.save(targetUser);
        final Long number = datePollEntryRepositoryManager.getVotesForDatePollEntry(
                targetPollLink,
                ModelOfDaoUtil.entryOf(targetDatePollEntryDao));

        assertThat(datePollDao).isNotNull();
        assertThat(number).isEqualTo(1);
    }
    @Test
    public void testUserPriorityForDatePollEntryIsNotAppreciated() {
        final DatePollDao datePollDao = datePollJpaRepository.
                findDatePollDaoByLink(datePoll.getPollLink().getLinkUUIDAsString());
        final DatePollEntryDao targetDatePollEntryDao = datePollDao.getEntryDaos().iterator().next();
        final GroupDao targetGroup = datePollDao.getGroupDaos().iterator().next();
        final UserDao targetUser = targetGroup.getUserDaos().iterator().next();
        PriorityChoiceDao priorityChoiceDao = new PriorityChoiceDao();
        priorityChoiceDao.setDatePollEntry(targetDatePollEntryDao);
        priorityChoiceDao.setParticipant(targetUser);
        priorityChoiceDao.setDatePollPriority(PriorityTypeEnum.NOT_APPRECIATED);
        final PriorityChoiceDaoKey priorityChoiceDaoKey = new PriorityChoiceDaoKey();
        priorityChoiceDao.setId(priorityChoiceDaoKey);
        priorityChoiceJpaRepository.save(priorityChoiceDao);
        priorityChoiceDao = priorityChoiceJpaRepository.getOne(priorityChoiceDaoKey);
        assertThat(priorityChoiceDao.getDatePollPriority())
                .isEqualByComparingTo(PriorityTypeEnum.NOT_APPRECIATED);
    }
    @Test
    public void testVotesForDatePollEntryAreZero() {
        final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll, Set.of(exampleGroup));

        datePollRepository.save(datePoll);

        final DatePollEntryDao datePollEntry = datePollDao.getEntryDaos().iterator().next();
        assertThat(datePollEntry.getUserVotesFor().size()).isEqualTo(0);
    }
}
