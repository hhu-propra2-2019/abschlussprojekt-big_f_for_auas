package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.group.GroupId;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollStatusDao;
import mops.infrastructure.database.daos.datepoll.DatePollStatusDaoKey;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollStatusJpaRepository;
import mops.infrastructure.database.repositories.interfaces.GroupJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DatePollRepositoryImpl implements DatePollRepository {

    public static final String USER_IS_NOT_IN_THE_DATABASE = "User is not in the database!";
    private final transient DatePollJpaRepository datePollJpaRepository;
    private final transient GroupJpaRepository groupJpaRepository;
    private final transient UserJpaRepository userJpaRepository;
    private final transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
    private final transient DatePollStatusJpaRepository datePollStatusJpaRepository;
    @Autowired
    public DatePollRepositoryImpl(DatePollJpaRepository datePollJpaRepository,
                                  GroupJpaRepository groupJpaRepository,
                                  UserJpaRepository userJpaRepository,
                                  DatePollEntryRepositoryManager datePollEntryRepositoryManager,
                                  DatePollStatusJpaRepository datePollStatusJpaRepository) {
        this.datePollJpaRepository = datePollJpaRepository;
        this.groupJpaRepository = groupJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.datePollEntryRepositoryManager = datePollEntryRepositoryManager;
        this.datePollStatusJpaRepository = datePollStatusJpaRepository;
    }

    /**
     * Die Methode läd alle DatePollDaos eines bestimmten Users aus der Datenbank und
     * wandelt diese zu DatePolls um.
     * @param userId Der User fuer den alle DatePolls aus der Datenbank geladen werden.
     * @return Das Set der DatePoll Objekte.
     */
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.AvoidDuplicateLiterals"})
    public Set<DatePoll> findDatePollsByUser(UserId userId) {
        final Set<DatePollDao> targetDatePollDaos = datePollJpaRepository.findAllDatePollsOfTargetUser(userId.getId());
        return targetDatePollDaos.stream()
                .map(ModelOfDaoUtil::pollOf)
                .collect(Collectors.toSet());
    }
    /**
     * Gibt das zugehoerige DatePollDao Objekt in der Datenbank zurueck.
     * @param link DatePoll Identifier.
     * @return DatePollDao
     */
    public DatePollDao findDatePollDaoByLink(String link) {
        return datePollJpaRepository.findDatePollDaoByLink(link);
    }
    /**
     * Lädt das DatePoll Aggregat anhand seines links.
     *
     * @param link Eindeutig identifizierender link einer Terminfindung.
     * @return An Inputlink gekoppeltes DatePoll
     */
    @Override
    public Optional<DatePoll> load(PollLink link) {
        final DatePollDao loaded = datePollJpaRepository
                .findDatePollDaoByLink(link.getLinkUUIDAsString());
        //TODO: DatePollBallots hinzufuegen?
        final DatePoll targetDatePoll = ModelOfDaoUtil.pollOf(loaded);
        return Optional.of(targetDatePoll);
    }

    /**
     * Speichert ein DatePoll Aggregat.
     * @param datePoll Zu speichernde DatePoll
     */
    @Transactional
    @Override
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.AvoidDuplicateLiterals"})
    public void save(DatePoll datePoll) {
        final Set<GroupDao> groupDaos = datePoll.getGroups().stream()
                .map(GroupId::getId)
                .map(groupJpaRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toSet());
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll, groupDaos);
        datePollJpaRepository.save(datePollDao);
        //Save Votes for DatePoll without Priority
        checkDatePollBallotsForVotes(datePoll.getBallots(), datePoll);
        //Save PollStatus for each User ...
        saveDatePollStatus(datePoll, datePollDao);
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    private void saveDatePollStatus(DatePoll datePoll, DatePollDao datePollDao) {
        final Map<UserId, PollStatus> votingRecord = datePoll.getRecordAndStatus().getVotingRecord();
        votingRecord.forEach((userId, pollStatus) -> {
            final DatePollStatusDaoKey nextStatusKey = new DatePollStatusDaoKey(
                    userId.getId(),
                    datePoll.getPollLink().getLinkUUIDAsString());
            final DatePollStatusDao datePollStatusDao = new DatePollStatusDao(
                    nextStatusKey,
                    DaoOfModelUtil.userDaoOf(userId),
                    datePollDao,
                    DaoOfModelUtil.createPollStatusEnumDao(pollStatus)
            );
            datePollStatusJpaRepository.save(datePollStatusDao);
        });
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    private void checkDatePollBallotsForVotes(Set<DatePollBallot> datePollBallots, DatePoll datePoll) {
        for (final DatePollBallot targetBallot:datePollBallots
             ) {
            if (targetBallot.getSelectedEntriesYes().size() != 0) {
                setVoteForTargetUserAndEntry(targetBallot.getSelectedEntriesYes(),
                        datePoll,
                        targetBallot.getUser());
            }
        }
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private void setVoteForTargetUserAndEntry(Set<DatePollEntry> datePollEntries, DatePoll datePoll, UserId user) {
        datePollEntries.forEach(targetEntry -> datePollEntryRepositoryManager
                .userVotesForDatePollEntry(user,
                        datePoll.getPollLink(),
                        targetEntry));
    }

    /**
     * Lädt alle DatePolls in denen ein Nutzer teilnimmt.
     *
     * @param userId Der User, welcher an den DatePolls teilnimmt.
     * @return Set<DatePoll> die entsprechenden DatePolls.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public Set<DatePoll> getDatePollByGroupMembership(UserId userId) {
        final Optional<UserDao> targetUser = userJpaRepository.findById(userId.toString());
        final UserDao targetUserDao = targetUser.orElseThrow(
                () -> new IllegalArgumentException(USER_IS_NOT_IN_THE_DATABASE));
        final Set<GroupDao> groupDaos = groupJpaRepository.findAllByUserDaosContaining(targetUserDao);
        final Set<DatePollDao> datePollDaosFromUser = new HashSet<>();
        groupDaos.stream()
                .map(datePollJpaRepository::findByGroupDaosContaining)
                .forEach(datePollDaosFromUser::addAll);
        final Set<DatePoll> targetDatePolls = new HashSet<>();
        datePollDaosFromUser.forEach(
                datePollDao -> targetDatePolls.add(ModelOfDaoUtil.pollOf(datePollDao)));
        return targetDatePolls;
    }

    /**
     * Gibt ein Set mit allen Datepolls zurück, welche der angegebene User erstellt hat.
     * @param userId
     * @return Set<DatePoll>, ist leer wenn User keine DatePolls erstellt hat.
     */
    @Override
    @SuppressWarnings("PMD.LawOfDemeter") //stream
    public Set<DatePoll> getDatePollByCreator(UserId userId) {
        final UserDao targetUser = DaoOfModelUtil.userDaoOf(userId);
        final Set<DatePollDao> datePollDaosCreatedByUser = datePollJpaRepository
            .findByCreatorUserDao(targetUser);
        return datePollDaosCreatedByUser.stream()
            .map(ModelOfDaoUtil::pollOf)
            .collect(Collectors.toSet());
    }

// TODO: Needs fixing and tests
//    /**
//     * Gibt ein Set mit allen Usern zurück bei denen der User schon einmal abgestimmt hat.
//     * @param userId
//     * @return Set<DatePoll>
//     */
//    @SuppressWarnings("PMD.LawOfDemeter") //stream
//    @Override
//    public Set<DatePoll> getDatePollWhereUserHasStatus(UserId userId) {
//        return datePollJpaRepository.findDatePollDaoWhereUserHasStatus(userId).stream()
//            .map(ModelOfDaoUtil::pollOf)
//            .collect(Collectors.toSet());
//    }
}
