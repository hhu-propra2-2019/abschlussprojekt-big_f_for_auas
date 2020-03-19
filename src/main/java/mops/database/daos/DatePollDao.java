package mops.database.daos;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.Set;

@Entity(name = "DatePoll")
@Table(name = "datepoll")
public class DatePollDao {
    @Id
    @GeneratedValue
    private Long id;
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
            mappedBy = "datepoll",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DatePollOptionDao> datePollOptionDaoSet;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userDaoSet;
}
