package mops.database;

import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.*;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.translator.DatePollDaoOfDatePoll;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
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
//import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
public class DatabaseIntegrityTest {
    @Autowired
    private DatePollRepositoryImpl datePollRepository;
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
        DatePollLink datePollLink = new DatePollLink("poll/link");
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
    }
    @Test
    public void saveOneDatePollDao() {
        DatePollDao datePollDao = DatePollDaoOfDatePoll.datePollDaoOf(datePoll);
        String link = datePollDao.getLink();
        System.out.println("Output Link:" + datePollDao.getLink());
        /*datePollRepository.save(datePoll);
        DatePollDao datepollFound = datePollRepository.getDatePollDao(link);
        System.out.println("[+] Found DatePoll: " + datepollFound.getLink());
        System.out.println("[+] Found DatePoll: " + datepollFound.getDatePollMetaInfDao().getLocation());
        assertThat(datepollFound.getLink()).isEqualTo("poll/link");*/
    }
}
