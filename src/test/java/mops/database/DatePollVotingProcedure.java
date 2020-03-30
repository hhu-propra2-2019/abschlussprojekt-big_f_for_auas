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
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.repositories.DatePollEntryRepositoryManager;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.GroupRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MopsApplication.class, H2DatabaseConfigForTests.class})
@Transactional
@SuppressWarnings({"PMD.LawOfDemeter", "PMD.AtLeastOneConstructor", "PMD.ExcessiveImports"})
public class DatePollVotingProcedure {
    @Autowired
    private transient DatePollRepositoryImpl datePollRepo;
    private final transient Random random = new Random();
    @Autowired
    private transient GroupRepositoryImpl groupRepository;
    @Autowired
    private transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
    @Test
    public void oneUserVotesForOneEntry() {
        final DatePoll firstDatePoll = createDatePoll(4, 5, "datepoll 1");
        datePollRepo.save(firstDatePoll);
        datePollEntryRepositoryManager
                .userVotesForDatePollEntry(new UserId("1"),
                        firstDatePoll.getPollLink(),
                        firstDatePoll.getEntries().iterator().next());
        final Set<DatePollEntryDao> entriesUserVotesFor = datePollEntryRepositoryManager
                .findAllDatePollEntriesUserVotesFor(new UserId("1"),
                        firstDatePoll);
        assertThat(entriesUserVotesFor.size()).isEqualTo(1);
    }

    /**
     * Beispiel Daten fuer ein DatePoll Objekt.
     * @param users Anzahl an Teilnehmern.
     * @param pollentries Anzahl an DatePollEntry Objekten.
     * @param title Der Titel des DatePoll Objektes.
     * @return DatePoll Das zu erstellende DatePoll Objekt.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    private DatePoll createDatePoll(int users, int pollentries, String title) {
        final DatePoll datePoll;
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf(title, "Testing", "Uni", timespan);
        final User creator = new User(new UserId(Integer.toString(random.nextInt())));
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        final Set<User> participants = new HashSet<>();
        IntStream.range(0, users).forEach(i -> participants.add(new User(new UserId(Integer.toString(i)))));

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, pollentries).forEach(i -> pollEntries.add(new DatePollEntry(
                new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        final Group group = new Group(
                new GroupMetaInf(new GroupId("1"), "Testgruppe", GroupVisibility.PRIVATE), participants);

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
