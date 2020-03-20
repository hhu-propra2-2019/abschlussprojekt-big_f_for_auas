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
    private Set<DatePollOptionDao> datePollOptionSet;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userSet;
}
