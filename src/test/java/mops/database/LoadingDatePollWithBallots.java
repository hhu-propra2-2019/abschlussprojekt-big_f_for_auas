package mops.database;

import mops.MopsApplication;
import mops.config.H2DatabaseConfigForTests;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.*;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.repositories.DatePollEntryRepositoryManager;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.DomainGroupRepositoryImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports"})
public class LoadingDatePollWithBallots {
    private final transient Random random = new Random();
    @Autowired
    private transient DatePollRepositoryImpl datePollRepository;
    @Autowired
    private transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
    @Autowired
    private transient DomainGroupRepositoryImpl domainGroupRepository;

    @SuppressWarnings("checkstyle:MagicNumber")
    @Test
    public void loadSavedDatePoll() {
        final DatePoll targetDatePoll = createDatePoll(5, 5, "test", "1");
        final DatePollEntry targetEntry = targetDatePoll.getEntries().iterator().next();
        datePollRepository.save(targetDatePoll);
        datePollEntryRepositoryManager.userVotesForDatePollEntry(new UserId("1"),
                targetDatePoll.getPollLink(),
                targetEntry);
        final Optional<DatePoll> loaded = datePollRepository.load(targetDatePoll.getPollLink());
        DatePoll loadedDatePoll = null;

        assertThat(loaded.isPresent());
        if (loaded.isPresent()) {
            loadedDatePoll = loaded.get();
            Set<DatePollBallot> loadedBallots = loadedDatePoll.getBallots();
            assertThat(loadedBallots).hasSize(1);
            //Funktioniert nur, da der User nur fuer ein datepollentry voted.
            final Set<Set<DatePollEntry>> targetDatePollEntry = loadedBallots.stream()
                    .filter(datePollBallot -> datePollBallot.getUser().getId().equals("1"))
                    .map(DatePollBallot::getSelectedEntriesYes)
                    .collect(Collectors.toSet());
            assertThat(targetDatePollEntry.size()).isEqualTo(1);
        }
    }

    private DatePoll createDatePoll(int users, int pollentries, String title, String groupId) {
        final DatePoll datePoll;
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf(title, "Testing", "Uni", timespan);
        final UserId creator = new UserId(Integer.toString(random.nextInt()));
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        final Set<UserId> participants = new HashSet<>();
        IntStream.range(0, users).forEach(i -> participants.add(new UserId(Integer.toString(i))));

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, pollentries).forEach(i -> pollEntries.add(new DatePollEntry(
                new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        final Group group = new Group(new GroupId(groupId), participants);

        datePoll = new DatePollBuilder()
                .datePollMetaInf(datePollMetaInf)
                .creator(creator)
                .datePollConfig(datePollConfig)
                .datePollEntries(pollEntries)
                .participatingGroups(Set.of(group.getId()))
                .datePollLink(datePollLink)
                .build();
        domainGroupRepository.save(group);
        return datePoll;
    }
}
