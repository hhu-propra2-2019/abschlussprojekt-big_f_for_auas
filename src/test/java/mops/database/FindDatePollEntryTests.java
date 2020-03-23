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
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.repositories.DatePollEntryRepositoryManager;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
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
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor"})
public class FindDatePollEntryTests {
    private transient DatePoll datePoll;

    @Autowired
    private transient DatePollJpaRepository datePollJpaRepository;
    @Autowired
    private transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
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

        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participants(participants)
                .datePollLink(datePollLink)
                .build();
        datePollJpaRepository.save(DaoOfModelUtil.pollDaoOf(datePoll));
    }
    @Test
    public void findDatePollEntry() {
        final DatePollEntry datePollEntry = datePoll.getEntries().iterator().next();
        final DatePollEntryDao datePollEntryDao = datePollEntryRepositoryManager.
                findDatePollEntryDao(datePoll.getPollLink(), datePollEntry);
        assertThat(datePollEntryDao.getTimespanDao().getStartDate()).
                isEqualTo(datePollEntry.getSuggestedPeriod().getStartDate());
    }
}
