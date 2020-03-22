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
        Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        QuestionPollMetaInf questionPollMetaInf = new QuestionPollMetaInf("TestQuestionPoll",
            "Testing is useful?", "Testdescription", timespan);
        UserId creator = new UserId("1234");
        QuestionPollConfig questionPollConfig = new QuestionPollConfig();
        Set<UserId> participants = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            UserId newUser = new UserId(Integer.toString(i));
            participants.add(newUser);
        }
        PollLink questionPollLink = new PollLink();
        Set<QuestionPollEntry> pollEntries = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            QuestionPollEntry entry = new QuestionPollEntry(
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
        System.out.println("[+] UserId: " + creator.getId());
        System.out.println("[+] Created QuestionPoll: " + questionPoll.getPollLink().getPollIdentifier());
    }
    @Test
    public void saveOneQuestionPollDao() {
        QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        System.out.println("[+] UserId of QuestionPollDao: " + questionPollDao.getCreatorUserDao().getId());
        String link = questionPollDao.getLink();
        System.out.println("Output Link:" + questionPollDao.getLink());
        questionPollJpaRepository.save(questionPollDao);
        QuestionPollDao questionpollFound = questionPollJpaRepository.findQuestionPollDaoByLink(link);
        System.out.println("[+] Found QuestionPoll: " + questionpollFound.getLink());
        System.out.println("[+] Found QuestionPoll: " + questionpollFound.getMetaInfDao().getQuestion());
        assertThat(questionpollFound.getLink()).isEqualTo(questionpollFound.getLink());
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testUsersOfQuestionPollPresence() {
        QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        questionPollJpaRepository.save(questionPollDao);
        Set<UserDao> userDaoSet = userJpaRepository.findByQuestionPollSetContains(questionPollDao);
        userDaoSet.forEach(userDao -> System.out.println("[+] Found User: " + userDao.getId()));
        assertThat(userDaoSet).hasSize(3);
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void testQuestionPollEntryPresence() {
        QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        questionPollJpaRepository.save(questionPollDao);
        Set<QuestionPollEntryDao> questionPollEntryDaoSet = questionPollEntryJpaRepository
            .findByQuestionPoll(questionPollDao);
        for (QuestionPollEntryDao questionPollEntryDao : questionPollEntryDaoSet) {
            System.out.println("[+] Found QuestionPollEntry: " + questionPollEntryDao.getId());
        }
        assertThat(questionPollEntryDaoSet).hasSize(3);
    }

    @Test
    public void testVotesForQuestionPollEntryAreZero() {
        QuestionPollDao questionPollDao = DaoOfModelUtil.pollDaoOf(questionPoll);
        questionPollJpaRepository.save(questionPollDao);
        QuestionPollEntryDao questionPollEntryDao = questionPollDao.getEntryDaos().iterator().next();
        System.out.println("[+] QuestionPollEntryId: " + questionPollEntryDao.getId());

        assertThat(questionPollEntryDao.getUserVotesFor().size()).isEqualTo(0);
    }
}
