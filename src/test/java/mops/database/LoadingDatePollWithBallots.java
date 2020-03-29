package mops.database;

import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.group.Group;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.domain.repositories.GroupRepository;
import mops.infrastructure.database.repositories.DatePollEntryRepositoryManager;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static mops.database.DatabaseTestUtil.createGroup;
import static mops.database.DatabaseTestUtil.createRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports",
        "PMD.DataflowAnomalyAnalysis"})
public class LoadingDatePollWithBallots {
    @Autowired
    private transient DatePollRepositoryImpl datePollRepository;
    @Autowired
    private transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
    @Autowired
    private transient GroupRepository groupRepository;

    @SuppressWarnings({"checkstyle:MagicNumber", "PMD.JUnitTestContainsTooManyAsserts"})
    @Test
    public void loadSavedDatePoll() {
        final DatePoll targetDatePoll = createDatePoll(5, 5, "test");
        final DatePollEntry targetEntry = targetDatePoll.getEntries().iterator().next();
        datePollRepository.save(targetDatePoll);
        datePollEntryRepositoryManager.userVotesForDatePollEntry(new UserId("1"),
                targetDatePoll.getPollLink(),
                targetEntry);
        final Optional<DatePoll> loaded = datePollRepository.load(targetDatePoll.getPollLink());
        DatePoll loadedDatePoll = null;

        //TODO: Refactoring des Tests.
        assertThat(loaded.isPresent());
        if (loaded.isPresent()) {
            loadedDatePoll = loaded.get();
            final Set<DatePollBallot> loadedBallots = loadedDatePoll.getBallots();
            //Funktioniert nur, da der User nur fuer ein datepollentry voted.
            final Set<Set<DatePollEntry>> targetDatePollEntry = loadedBallots.stream()
                    .filter(datePollBallot -> datePollBallot.getUser().getId().equals("1"))
                    .map(DatePollBallot::getSelectedEntriesYes)
                    .collect(Collectors.toSet());
            assertThat(targetDatePollEntry.size()).isEqualTo(1);
        }
    }
    @SuppressWarnings("checkstyle:MagicNumber")
    private DatePoll createDatePoll(int users, int pollentries, String title) {
        final DatePoll datePoll;
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf(title, "Testing", "Uni", timespan);
        final User creator = createRandomUser();
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        final Group group = createGroup(users);

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, pollentries).forEach(i -> pollEntries.add(new DatePollEntry(
                new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));

        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participatingGroups(Set.of(group.getMetaInf().getId()))
                .datePollLink(datePollLink)
                .build();
        groupRepository.save(group);
        return datePoll;
    }
}
