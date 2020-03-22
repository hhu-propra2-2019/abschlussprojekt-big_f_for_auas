package mops.database;

import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.questionpoll.QuestionPollBuilder;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.repositories.interfaces.QuestionPollEntryJpaRepository;
import mops.infrastructure.database.repositories.interfaces.QuestionPollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import mops.infrastructure.database.repositories.QuestionPollRepositoryImpl;
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
public class DatabaseQuestionPollIntegrityTest {
    @Autowired
    private QuestionPollRepositoryImpl questionPollRepository;
    @Autowired
    private QuestionPollJpaRepository questionPollJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private QuestionPollEntryJpaRepository questionPollEntryJpaRepository;
    private QuestionPoll questionPoll;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber"})
    @BeforeEach
    public void setupQuestionPollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final QuestionPollMetaInf questionPollMetaInf = new QuestionPollMetaInf("TestQuestionPoll",
            "Testing is useful?", "Testdescription", timespan);
        final UserId creator = new UserId("1234");
        final QuestionPollConfig questionPollConfig = new QuestionPollConfig();
        final Set<UserId> participants = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            final UserId newUser = new UserId(Integer.toString(i));
            participants.add(newUser);
        }
        final PollLink questionPollLink = new PollLink();
        final Set<QuestionPollEntry> pollEntries = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            final QuestionPollEntry entry = new QuestionPollEntry(
                   "title" + i
            );
            pollEntries.add(entry);
        }

        questionPoll = new QuestionPollBuilder()
                .questionPollMetaInf(questionPollMetaInf)
                .creator(creator)
                .questionPollConfig(questionPollConfig)
                .questionPollEntries(pollEntries)
                .participants(participants)
                .pollLink(questionPollLink)
                .build();
    }

    @Test
    public void saveOneQuestionPollDao() {
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        final String link = questionPollDao.getLink();
        questionPollJpaRepository.save(questionPollDao);
        final QuestionPollDao questionpollFound = questionPollJpaRepository.findQuestionPollDaoByLink(link);

        assertThat(questionpollFound.getLink()).isEqualTo(questionpollFound.getLink());
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testUsersOfQuestionPollPresence() {
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        questionPollJpaRepository.save(questionPollDao);
        final Set<UserDao> userDaoSet = userJpaRepository.findByQuestionPollSetContains(questionPollDao);
        userDaoSet.forEach(userDao -> System.out.println("[+] Found User: " + userDao.getId()));
        assertThat(userDaoSet).hasSize(3);
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testQuestionPollEntryPresence() {
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        questionPollJpaRepository.save(questionPollDao);
        final Set<QuestionPollEntryDao> questionPollEntryDaoSet = questionPollEntryJpaRepository
            .findByQuestionPoll(questionPollDao);
        assertThat(questionPollEntryDaoSet).hasSize(3);
    }

    @Test
    public void testVotesForQuestionPollEntryAreZero() {
        final QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        questionPollJpaRepository.save(questionPollDao);
        final QuestionPollEntryDao questionPollEntryDao = questionPollDao.getEntryDaos().iterator().next();

        assertThat(questionPollEntryDao.getUserVotesFor().size()).isEqualTo(0);
    }
}
