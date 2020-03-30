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
import mops.domain.models.user.User;
import mops.domain.repositories.GroupRepository;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
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
import java.util.stream.IntStream;

import static mops.database.DatabaseTestUtil.createGroup;
import static mops.database.DatabaseTestUtil.createRandomUser;
import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports", "PMD.SingularFields"})
public class FindDatePollEntryTests {
    private transient DatePoll datePoll;
    @Autowired
    private transient GroupRepository groupRepository;
    @Autowired
    private transient DatePollRepositoryImpl datePollRepository;
    @SuppressWarnings({"checkstyle:DesignForExtension", "checkstyle:MagicNumber", "PMD.SingularField"})
    @BeforeEach
    public void setupDatePollRepoTest() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf("TestDatePoll", "Testing", "Uni", timespan);
        final User creator = createRandomUser();
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, 1).forEach(i -> pollEntries.add(new DatePollEntry(
                new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        final Group group = createGroup(10);
        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participatingGroups(Set.of(group.getMetaInf().getId()))
                .datePollLink(datePollLink)
                .build();

        groupRepository.save(group);
        datePollRepository.save(datePoll);
    }
    @Test
    public void findDatePollEntry() {
        final DatePollEntry datePollEntry = datePoll.getEntries().iterator().next();
        final Set<DatePollEntryDao> datePollEntryDaos = datePollRepository
                .findDatePollDaoByLink(datePoll.getPollLink().getLinkUUIDAsString())
                .getEntryDaos();
        //Funktioniert nur, da genau ein DatePollEntry im Set ist.
        final DatePollEntryDao targetDatePollEntryDao = datePollEntryDaos.iterator().next();
        assertThat(targetDatePollEntryDao .getTimespanDao().getStartDate()).
                isEqualTo(datePollEntry.getSuggestedPeriod().getStartDate());
    }
}
