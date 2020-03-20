package mops.infrastructure.database.daos.datepoll;

import lombok.Getter;
import lombok.Setter;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollOption;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: refactoring @data annotation -> entities shouldn't have @Data annotation only getters and setters
@Getter
@Setter
@Entity(name = "DatePoll")
@Table(name = "datepoll")
public class DatePollDao {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String link;
    @Embedded
    private PollRecordAndStatusDao pollRecordAndStatusDao;
    @Embedded
    private DatePollConfigDao datePollConfigDao;
    @Embedded
    private DatePollMetaInfDao datePollMetaInfDao;
    @OneToOne
    private UserDao creatorUserDao;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DatePollOptionDao> datePollOptionSet;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userSet;

    public static DatePollDao of(DatePoll datePoll) {
        //Extract DatePollDaoMetaInf
        DatePollMetaInfDao datePollMetaInfDao = DatePollMetaInfDao.of(datePoll.getDatePollMetaInf());
        //Extract DatePollDaoConfig
        DatePollConfigDao datePollConfigDao = DatePollConfigDao.of(datePoll.getDatePollConfig());
        //Extract DatePollDaoRecordAndStatus
        PollRecordAndStatusDao pollRecordAndStatusDao = PollRecordAndStatusDao.of(
                datePoll.getDatePollRecordAndStatus());
        //Extract DatePollLink
        String newLink = datePoll.getDatePollLink().getDatePollIdentifier();
        //Create new DatePollDao and set current values
        DatePollDao datePollDao = new DatePollDao();
        datePollDao.setLink(newLink);
        datePollDao.setDatePollMetaInfDao(datePollMetaInfDao);
        datePollDao.setDatePollConfigDao(datePollConfigDao);
        datePollDao.setPollRecordAndStatusDao(pollRecordAndStatusDao);
        //Extract DatePollDaoOptions
        datePollDao.setDatePollOptionSet(extractDatePollOptionDaos(datePoll, datePollDao));
        //Extract DatePollDaoParticipants
        datePollDao.setUserSet(extractDatePollUser(datePoll));
        return datePollDao;
    }

    private static Set<DatePollOptionDao> extractDatePollOptionDaos(DatePoll datePoll, DatePollDao datePollDao) {
        List<DatePollOption> datePollOptionList = datePoll.getDatePollOptions();
        Set<DatePollOptionDao> datePollOptionDaos = new HashSet<>();
        for (DatePollOption datePollOption:datePollOptionList
        ) {
            DatePollOptionDao currentOption = DatePollOptionDao.of(datePollOption, datePollDao);
            datePollOptionDaos.add(currentOption);
        }
        return datePollOptionDaos;
    }

    private static Set<UserDao> extractDatePollUser(DatePoll datePoll) {
        List<UserId> userIds = datePoll.getParticipants();
        Set<UserDao> userDaoSet = new HashSet<>();
        for (UserId currentUserId:userIds
        ) {
            userDaoSet.add(UserDao.of(currentUserId));
        }
        return userDaoSet;
    }
}
