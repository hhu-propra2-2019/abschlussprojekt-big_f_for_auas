package mops.infrastructure.database.daos.datepoll;

import lombok.Getter;
import lombok.Setter;
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
    private Set<DatePollEntryDao> datePollEntrySet;
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userSet;
/*
    public static DatePoll to(DatePollDao datePollDao) {
        //Extract DatePollMetaInf
        DatePollMetaInf datePollMetaInf = DatePollMetaInfDao.to(datePollDao.getDatePollMetaInfDao());
        //Extract DatePollConfig
        DatePollConfig datePollConfig = DatePollConfigDao.to(datePollDao.getDatePollConfigDao());
        //Extract DatePollRecordAndStatus
        PollRecordAndStatus pollRecordAndStatus = PollRecordAndStatusDao.to(
                datePollDao.getPollRecordAndStatusDao());
        //Extract DatePollLink
        DatePollLink newLink = new DatePollLink(datePollDao.getLink());
        //Create new DatePollDao and set current values
        DatePoll datePoll = new DatePollBuilder()
                .datePollLink(newLink)
                .datePollMetaInf(datePollMetaInf)
                .datePollConfig(datePollConfig)
                //entries
                .datePollEntries(datePollDao.getDatePollEntrySet())
                //participants
                //creator
                //.creator(datePollDao.getCreatorUserDao())
                .build();
         datePoll.datePoll(pollRecordAndStatus);
        //Extract DatePollDaoOptions
        datePoll.setDatePollOptionSet(extractDatePollOptionDaos(datePoll, datePollDao));
        //Extract DatePollDaoParticipants
        datePollDao.setUserSet(extractDatePollUser(datePoll));

    }*/
}
