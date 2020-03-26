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
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.DomainGroupRepositoryImpl;
import mops.infrastructure.database.repositories.interfaces.DatePollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
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
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports"})
public class DatabaseDatePollIntegrityTest {
    private transient DatePoll datePoll;
    private transient Group group;
    private transient Set<UserId> participants;
    @Autowired
    private transient DatePollRepositoryImpl datePollRepository;
    @Autowired
    private transient DatePollJpaRepository datePollJpaRepository;
    @Autowired
    private transient UserJpaRepository userJpaRepository;
    @Autowired
    private transient DatePollEntryJpaRepository datePollEntryJpaRepository;

    @Autowired
    private transient DomainGroupRepositoryImpl domainGroupRepository;


    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupDatePollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        final UserId creator = new UserId("1234");
        final DatePollConfig datePollConfig = new DatePollConfig();

        final PollLink datePollLink = new PollLink(UUID.randomUUID());

        participants = new HashSet<>();
        IntStream.range(0, 1).forEach(i -> participants.add(new UserId(Integer.toString(i))));

        group = new Group(new GroupId("1"), participants);

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, 3).forEach(i -> pollEntries.add(new DatePollEntry(
                new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));

        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participatingGroups(Set.of(group.getId()))
                .datePollLink(datePollLink)
                .build();
        domainGroupRepository.save(group);
    }

    @Test
    public void saveOneDatePollDao() {
        datePollRepository.save(datePoll);
        final DatePollDao datepollFound = datePollJpaRepository.findDatePollDaoByLink(
                datePoll.getPollLink().getLinkUUIDAsString());
        assertThat(datePoll.getPollLink().getLinkUUIDAsString()).isEqualTo(datepollFound.getLink());
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testUsersOfDatePollPresence() {
        final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        datePollRepository.save(datePoll);
        final Set<UserDao> userDaoSet = userJpaRepository.findAllByGroupSetContaining(exampleGroup);
        assertThat(userDaoSet).hasSize(1);
    }
    @Test
    public void datePollDaoFromUserIsPresent() {
        //final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        datePollRepository.save(datePoll);
        //final Set<UserDao> userDaoSet = userJpaRepository.findAllByGroupSetContaining(exampleGroup);
        final Set<DatePoll> datePollsByUserId = datePollRepository.getDatePollByGroupMembership(
                participants.iterator().next());
        assertThat(datePollsByUserId.size()).isEqualTo(1);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testDatePollEntryPresence() {
        final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll, Set.of(exampleGroup));
        datePollRepository.save(datePoll);
        final Set<DatePollEntryDao> datePollEntryDaoSet = datePollEntryJpaRepository.findByDatePoll(datePollDao);
        assertThat(datePollEntryDaoSet).hasSize(3);
    }
}
