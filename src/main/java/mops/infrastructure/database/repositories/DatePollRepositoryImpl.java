package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.domain.models.pollstatus.PollStatus;
import mops.domain.models.user.User;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.domain.repositories.UserRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.datepoll.DatePollStatusDao;
import mops.infrastructure.database.daos.datepoll.DatePollStatusDaoKey;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.DatePollStatusJpaRepository;
import mops.infrastructure.database.repositories.interfaces.GroupJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

// TODO: Entscheiden, ob die Excessive Imports ein Problem sind
// Hinweis: ich habe die Klasse nicht geschrieben, sonder nur den Master gemerged
@SuppressWarnings("PMD.ExcessiveImports")
@Repository
public class DatePollRepositoryImpl implements DatePollRepository {

    public static final String USER_IS_NOT_IN_THE_DATABASE = "User is not in the database!";
    private final transient DatePollJpaRepository datePollJpaRepository;
    private final transient GroupJpaRepository groupJpaRepository;
    //private final transient UserJpaRepository userJpaRepository;
    private final transient UserRepository userRepository;
    private final transient DatePollEntryRepositoryManager datePollEntryRepositoryManager;
    private final transient DatePollStatusJpaRepository datePollStatusJpaRepository;
    @Autowired
    public DatePollRepositoryImpl(DatePollJpaRepository datePollJpaRepository,
                                  GroupJpaRepository groupJpaRepository,
                                  UserJpaRepository userJpaRepository,
                                  UserRepository userRepository,
                                  DatePollEntryRepositoryManager datePollEntryRepositoryManager,
                                  DatePollStatusJpaRepository datePollStatusJpaRepository) {
        this.datePollJpaRepository = datePollJpaRepository;
        this.groupJpaRepository = groupJpaRepository;
        this.userRepository = userRepository;
        //this.userJpaRepository = userJpaRepository;
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
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    public Optional<DatePoll> load(PollLink link) {
        final DatePollDao loaded = datePollJpaRepository
                .findDatePollDaoByLink(link.getLinkUUIDAsString());
        final DatePoll targetDatePoll = ModelOfDaoUtil.pollOf(loaded);
        //Extrahiere alle Gruppen, extrahiere alle User in den Gruppen, lade alle DatePollEntries fuer
        //die der User gestimmt hat und rufe castBallot auf, damit diese im datePoll Objekt gespeichert werden.
        loaded.getGroupDaos().stream()
                .map(GroupDao::getUserDaos)
                .flatMap(Collection::stream)
                .map(ModelOfDaoUtil::userOf)
                .forEach(user -> setActualBallotForUserAndDatePoll(targetDatePoll, user));
        return Optional.of(targetDatePoll);
    }
    @SuppressWarnings("PMD.LawOfDemeter")
    private void setActualBallotForUserAndDatePoll(DatePoll targetDatePoll, User user) {
        final UserId targetUser = user.getId();
        // TODO: Unterscheidung zwischen Yes und Maybe Votes, findet nicht statt
        final Set<DatePollEntryDao> targetDatePollEntryDaos = datePollEntryRepositoryManager
                .findAllDatePollEntriesUserVotesFor(targetUser, targetDatePoll);
        final Set<DatePollEntryDao> targetEntriesMaybe = datePollEntryRepositoryManager
                .findAllDatePollEntriesUserVotesForMaybe(targetUser, targetDatePoll);
        final Set<DatePollEntry> targetDatePollEntries = ModelOfDaoUtil
                .extractDatePollEntries(targetDatePollEntryDaos);

        final Set<DatePollEntry> targetDatePollEntriesMaybe = ModelOfDaoUtil
                .extractDatePollEntries(targetEntriesMaybe);
        if (targetDatePollEntries.isEmpty()) {
            targetDatePoll.castBallot(targetUser, targetDatePollEntries, targetDatePollEntriesMaybe);
        }
        targetDatePoll.castBallot(targetUser, targetDatePollEntries, targetDatePollEntriesMaybe);
    }

    /**
     * Speichert ein DatePoll Aggregat.
     * @param datePoll Zu speichernde DatePoll
     */
    @Override
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.AvoidDuplicateLiterals"})
    public void save(DatePoll datePoll) {

        userRepository.saveUserIfNotPresent(datePoll.getCreator());

        final Set<GroupDao> groupDaos = datePoll.getGroups().stream()
                .map(GroupId::getId)
                .map(groupJpaRepository::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toSet());
        final GroupMetaInf groupMetaInf = new GroupMetaInf(
                new GroupId(UUID.randomUUID().toString()), "erstellerGruppe", GroupVisibility.PRIVATE);
        final Group group = new Group(groupMetaInf, Set.of(datePoll.getCreator()));
        groupDaos.add(DaoOfModelUtil.groupDaoOf(group));
        final DatePollDao datePollDao = DaoOfModelUtil.pollDaoOf(datePoll, groupDaos);
        datePollJpaRepository.save(datePollDao);
        //Save Votes for DatePoll without Priority
        checkDatePollBallotsForVotes(datePoll.getBallots(), datePoll);
        //Save PollStatus for each User ...
        saveDatePollStatus(datePoll, datePollDao);
        datePollJpaRepository.flush();
    }
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    private void saveDatePollStatus(DatePoll datePoll, DatePollDao datePollDao) {
        final Map<User, PollStatus> votingRecord = datePoll.getRecordAndStatus().getVotingRecord();
        votingRecord.forEach((user, pollStatus) -> {
            final DatePollStatusDaoKey nextStatusKey = new DatePollStatusDaoKey(
                    user.getId().getId(),
                    datePoll.getPollLink().getLinkUUIDAsString());
            final DatePollStatusDao datePollStatusDao = new DatePollStatusDao(
                    nextStatusKey,
                    DaoOfModelUtil.userDaoOf(user),
                    datePollDao,
                    DaoOfModelUtil.createPollStatusEnumDao(pollStatus)
            );
            datePollStatusJpaRepository.save(datePollStatusDao);
        });
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
    private void checkDatePollBallotsForVotes(Set<DatePollBallot> datePollBallots, DatePoll datePoll) {
        for (final DatePollBallot targetBallot:datePollBallots) {
            if (targetBallot.getSelectedEntriesYes().size() != 0) {
                setYesVoteForTargetUserAndEntry(targetBallot.getSelectedEntriesYes(),
                        datePoll,
                        targetBallot.getUser());
            }
            if (targetBallot.getSelectedEntriesMaybe().size() != 0) {
                setMaybeVoteForTargetUserAndEntry(targetBallot.getSelectedEntriesMaybe(),
                        datePoll,
                        targetBallot.getUser());
            }
        }
    }

    private void setMaybeVoteForTargetUserAndEntry(Set<DatePollEntry> maybeVotes, DatePoll datePoll, UserId user) {
        maybeVotes.forEach(targetEntry -> datePollEntryRepositoryManager
                .userVotesMaybeForDatePollEntry(user,
                        datePoll.getPollLink(),
                        targetEntry));
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    private void setYesVoteForTargetUserAndEntry(Set<DatePollEntry> yesVotes, DatePoll datePoll, UserId user) {
        yesVotes.forEach(targetEntry -> datePollEntryRepositoryManager
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
        final UserDao targetUserDao =
                DaoOfModelUtil.userDaoOf(
                        userRepository.load(userId).orElseThrow(
                                () -> new IllegalArgumentException(USER_IS_NOT_IN_THE_DATABASE)));
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
     * @param userId ...
     * @return Set<DatePoll>, ist leer wenn User keine DatePolls erstellt hat.
     */
    @Override
    @SuppressWarnings("PMD.LawOfDemeter") //stream
    public Set<DatePoll> getDatePollByCreator(@NonNull UserId userId) {
        final UserDao targetUser = DaoOfModelUtil.userDaoOf(userRepository.load(userId).orElseThrow());
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

    /**
     * Methode entfernt ein DatePollDao aus der Datenbank anhand des PollLinks.
     * @param pollLink der zum DatePollDao zugehoerige Link.
     */
    @SuppressWarnings("PMD.LawOfDemeter") //stream
    public void delete(PollLink pollLink) {
        datePollJpaRepository.delete(
                datePollJpaRepository.findDatePollDaoByLink(pollLink.getLinkUUIDAsString()));
    }

    /**
     * Methode gibt alle nach einem bestimmten Enddatum abeglaufenen DatePolls zurueck.
     * @param endDate End-Datum des DatePolls.
     * @return Set<PollLink> Die Links der zu entfernenden DatePolls.
     **/
    @SuppressWarnings("PMD.LawOfDemeter") //stream
    public Set<PollLink> getTerminatedDatePolls(LocalDate endDate) {
        final Set<DatePollDao> pollsToDelete = datePollJpaRepository.findAllTerminatedDatePollsByEndDate(endDate);
        return pollsToDelete.stream().map(DatePollDao::getLink)
                .map(ModelOfDaoUtil::linkOf)
                .collect(Collectors.toSet());
    }
}
