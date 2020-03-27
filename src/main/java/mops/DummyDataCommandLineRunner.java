package mops;

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
import mops.domain.models.user.UserId;
import mops.infrastructure.database.repositories.DatePollEntryRepositoryManager;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.GroupRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Die Klasse dient der Erstellung von Datenbank Objekten und stellt eine
 * Alternative zum Einlesen von Datenbank Objekten über eine *.sql Datei dar.
 * Eine Verletzung von LawOfDemeter ist hier gerechtfertigt, da 'nur' Objekte
 * zum Speichern in die Datenbank erzeugt werden.
 */
@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:MagicNumber"})
@Profile("production")
@Transactional
@Component
public class DummyDataCommandLineRunner implements CommandLineRunner {

    private final transient DatePollRepositoryImpl datePollRepo;
    private final transient Random random = new Random();
    private final transient GroupRepositoryImpl domainGroupRepository;
    private final transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;

    @Autowired
    public DummyDataCommandLineRunner(DatePollRepositoryImpl datePollRepository,
                                      GroupRepositoryImpl domainGroupRepository,
                                      DatePollEntryRepositoryManager datePollEntryRepositoryManager) {
        this.datePollRepo = datePollRepository;
        this.domainGroupRepository = domainGroupRepository;
        this.datePollEntryRepositoryManager = datePollEntryRepositoryManager;
    }

    /**
     * Erstellt und speichert Dummy Data fuer die Datenbank.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        final DatePoll firstDatePoll = createDatePoll(4, 5, "datepoll 1", "1");
        final DatePoll secondDatePoll = createDatePoll(2, 2, "datepoll 2", "2");
        final DatePoll thirdDatePoll = createDatePoll(3, 1, "datepoll 3", "3");
        final DatePoll orgaPoll1 = createDatePollForOrga(2, 4, "orga 1", "4");
        final DatePoll orgaPoll2 = createDatePollForOrga(4, 8, "orga 2", "5");
        final DatePoll orgaPoll3 = createDatePollForOrga(8, 16, "orga 4", "6");
        datePollRepo.save(firstDatePoll);
        datePollRepo.save(secondDatePoll);
        datePollRepo.save(thirdDatePoll);
        datePollRepo.save(orgaPoll1);
        datePollRepo.save(orgaPoll2);
        datePollRepo.save(orgaPoll3);
        datePollEntryRepositoryManager
            .userVotesForDatePollEntry(new UserId("1"),
                firstDatePoll.getPollLink(),
                firstDatePoll.getEntries().iterator().next());
    }

    /**
     * Beispiel Daten fuer ein DatePoll Objekt.
     *
     * @param users       Anzahl an Teilnehmern.
     * @param pollentries Anzahl an DatePollEntry Objekten.
     * @param title       Der Titel des DatePoll Objektes.
     * @param groupId     Die zugehoerige Gruppen ID.
     * @return DatePoll Das zu erstellende DatePoll Objekt.
     */
    private DatePoll createDatePoll(int users, int pollentries, String title, String groupId) {
        final DatePoll datePoll;
        final Timespan timespan = new Timespan(LocalDateTime.now(),
            LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf(title, "Testing", "Uni",
            timespan);
        final UserId creator = new UserId(Integer.toString(random.nextInt()));
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        final Set<UserId> participants = new HashSet<>();
        IntStream.range(0, users).forEach(i -> participants.add(new UserId(Integer.toString(i))));

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, pollentries).forEach(i -> pollEntries.add(new DatePollEntry(
            new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        final Group group = new Group(
                new GroupMetaInf(new GroupId(groupId), "Testgruppe", GroupVisibility.PRIVATE), participants);

        datePoll = new DatePollBuilder()
            .datePollMetaInf(datePollMetaInf)
            .creator(creator)
            .datePollConfig(datePollConfig)
            .datePollEntries(pollEntries)
            .participatingGroups(Set.of(group.getMetaInf().getId()))
            .datePollLink(datePollLink)
            .build();
        domainGroupRepository.save(group);
        return datePoll;
    }

    private DatePoll createDatePollForOrga(int users, int pollentries, String title,
        String groupId) {
        final DatePoll datePoll;
        final Timespan timespan = new Timespan(LocalDateTime.now(),
            LocalDateTime.now().plusDays(10));
        final DatePollMetaInf datePollMetaInf = new DatePollMetaInf(title, "Testing", "Uni",
            timespan);
        final UserId creator = new UserId("orga");
        final DatePollConfig datePollConfig = new DatePollConfig();
        final PollLink datePollLink = new PollLink();

        final Set<UserId> participants = new HashSet<>();
        IntStream.range(0, users).forEach(i -> participants.add(new UserId(Integer.toString(i))));

        final Set<DatePollEntry> pollEntries = new HashSet<>();
        IntStream.range(0, pollentries).forEach(i -> pollEntries.add(new DatePollEntry(
            new Timespan(LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(10 + i))
        )));
        final Group group = new Group(
                new GroupMetaInf(new GroupId(groupId), "Testgruppe", GroupVisibility.PRIVATE), participants);

        datePoll = new DatePollBuilder()
            .datePollMetaInf(datePollMetaInf)
            .creator(creator)
            .datePollConfig(datePollConfig)
            .datePollEntries(pollEntries)
            .participatingGroups(Set.of(group.getMetaInf().getId()))
            .datePollLink(datePollLink)
            .build();
        domainGroupRepository.save(group);
        return datePoll;
    }
}
