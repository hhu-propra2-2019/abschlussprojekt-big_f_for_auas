package mops.controllers.daos;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
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
