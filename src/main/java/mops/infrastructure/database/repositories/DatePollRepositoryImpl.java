package mops.infrastructure.database.repositories;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.DatePollJpaRepository;
import mops.infrastructure.database.repositories.interfaces.GroupJpaRepository;
import mops.infrastructure.database.repositories.interfaces.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
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

    @Autowired
    public DatePollRepositoryImpl(DatePollJpaRepository datePollJpaRepository,
                                  GroupJpaRepository groupJpaRepository,
                                  UserJpaRepository userJpaRepository,
                                  DatePollEntryRepositoryManager datePollEntryRepositoryManager) {
        this.datePollJpaRepository = datePollJpaRepository;
        this.groupJpaRepository = groupJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.datePollEntryRepositoryManager = datePollEntryRepositoryManager;
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
        final DatePoll targetDatePoll = ModelOfDaoUtil.pollOf(loaded);
        return Optional.of(targetDatePoll);
    }

    /**
     * Speichert ein DatePoll aggregat.
     *
     * @param datePoll Zu speichernde DatePoll
     */
    @Override
    @SuppressWarnings({"PMD.LawOfDemeter"})
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
    }
    private void checkDatePollBallotsForVotes(Set<DatePollBallot> datePollBallots, DatePoll datePoll) {
        for (DatePollBallot datePollBallot:datePollBallots
             ) {
            if (datePollBallot.getSelectedEntriesYes().size() != 0) {
                setVoteForTargetUserAndEntry(datePollBallot.getSelectedEntriesYes(),
                        datePoll,
                        datePollBallot.getUser());
            }
        }
    }
    private void setVoteForTargetUserAndEntry(Set<DatePollEntry> datePollEntries, DatePoll datePoll, UserId user) {
        datePollEntries.forEach(targetEntry -> {
            datePollEntryRepositoryManager
                    .userVotesForDatePollEntry(user,
                            datePoll.getPollLink(),
                            targetEntry);
        });
    }
    /**
     * Lädt alle DatePolls in denen ein Nutzer teilnimmt.
     *
     * @param userId Der User, welcher an den DatePolls teilnimmt.
     * @return Set<DatePoll> die entsprechenden DatePolls.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public Set<DatePoll> getDatePollsByUserId(UserId userId) {
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
     * Die Methode gibt den DatePoll anhand des Erstellers zurueck.
     *
     * @param userId Die userId des DatePoll Creators.
     * @return Optional<DatePoll> Das DatePoll Objekt, welches vom User mit userId erstellt wurde.
     */
    @Override
    public Optional<DatePoll> getDatePollByCreator(UserId userId) {
        final UserDao targetUser = DaoOfModelUtil.userDaoOf(userId);
        final DatePollDao byCreatorUserDao = datePollJpaRepository.findByCreatorUserDao(targetUser);
        return Optional.of(ModelOfDaoUtil.pollOf(byCreatorUserDao));
    }
}
