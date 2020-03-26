package mops.database;

import java.util.stream.IntStream;
import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.repositories.DomainGroupRepositoryImpl;
import mops.infrastructure.database.repositories.interfaces.QuestionPollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.QuestionPollJpaRepository;
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
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports"})
public class DatabaseQuestionPollIntegrityTest {
    private transient QuestionPoll questionPoll;
    private transient Group group;
    @Autowired
    private transient QuestionPollJpaRepository questionPollJpaRepository;
    @Autowired
    private transient UserJpaRepository userJpaRepository;
    @Autowired
    private transient QuestionPollEntryJpaRepository questionPollEntryJpaRepository;
    @Autowired
    private transient DomainGroupRepositoryImpl domainGroupRepository;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupQuestionPollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final QuestionPollMetaInf questionPollMetaInf = new QuestionPollMetaInf("TestQuestionPoll",
            "Testing is useful?", "Testdescription", timespan);
        final UserId creator = new UserId("1234");
        final QuestionPollConfig questionPollConfig = new QuestionPollConfig();
        final PollLink questionPollLink = new PollLink();
        final Set<UserId> participants = new HashSet<>();
        IntStream.range(0, 3).forEach(i -> participants.add(new UserId(Integer.toString(i))));
        final Set<QuestionPollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, 3).forEach(i -> pollEntries.add(new QuestionPollEntry("title" + i)));
        group = new Group(new GroupId("1"), "Testgruppe", Group.GroupVisibility.PRIVATE, participants);
        questionPoll = new QuestionPollBuilder()
                .questionPollMetaInf(questionPollMetaInf)
                .creator(creator)
                .questionPollConfig(questionPollConfig)
                .questionPollEntries(pollEntries)
                .participatingGroups(Set.of(group.getId()))
                .pollLink(questionPollLink)
                .build();
        domainGroupRepository.save(group);
    }

    @Test
    public void saveOneQuestionPollDao() {
        final GroupDao exampleGroup = new GroupDao();
        exampleGroup.setId("1");
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll, Set.of(exampleGroup));
        final String link = questionPollDao.getLink();
        questionPollJpaRepository.save(questionPollDao);
        final QuestionPollDao questionpollFound = questionPollJpaRepository.findQuestionPollDaoByLink(link);
        assertThat(questionpollFound.getLink()).isEqualTo(questionpollFound.getLink());
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testUsersOfQuestionPollPresence() {
        final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll, Set.of(exampleGroup));
        questionPollJpaRepository.save(questionPollDao);
        final Set<UserDao> userDaoSet = userJpaRepository.findAllByGroupSetContaining(exampleGroup);
        assertThat(userDaoSet).hasSize(3);
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testQuestionPollEntryPresence() {
        final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll, Set.of(exampleGroup));
        questionPollJpaRepository.save(questionPollDao);
        final Set<QuestionPollEntryDao> questionPollEntryDaoSet = questionPollEntryJpaRepository
            .findByQuestionPoll(questionPollDao);
        assertThat(questionPollEntryDaoSet).hasSize(3);
    }

    @Test
    public void testVotesForQuestionPollEntryAreZero() {
        final GroupDao exampleGroup = DaoOfModelUtil.groupDaoOf(group);
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll, Set.of(exampleGroup));
        questionPollJpaRepository.save(questionPollDao);
        final QuestionPollEntryDao questionPollEntryDao = questionPollDao.getEntryDaos().iterator().next();
        assertThat(questionPollEntryDao.getUserVotesFor().size()).isEqualTo(0);
    }
}
