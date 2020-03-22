package mops.infrastructure.database.daos.datepoll;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.Id;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
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
    private String link;
    @Embedded
    private PollRecordAndStatusDao pollRecordAndStatusDao;
    @Embedded
    private DatePollConfigDao configDao;
    @Embedded
    private DatePollMetaInfDao metaInfDao;
    @OneToOne(cascade = CascadeType.ALL)
    private UserDao creatorUserDao;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<DatePollEntryDao> entryDaos;
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userDaos;
}
