package mops.infrastructure.database.daos.datepoll;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "DatePoll")
@Table(name = "datepoll")
@NoArgsConstructor
public class DatePollDao {
    @Id
    private String link;
    @Embedded
    private PollRecordAndStatusDao pollRecordAndStatusDao;
    @Embedded
    private DatePollConfigDao configDao;
    @Embedded
    private DatePollMetaInfDao metaInfDao;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private UserDao creatorUserDao;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<DatePollEntryDao> entryDaos = new HashSet<>();

    @ManyToMany
    private Set<GroupDao> groupDaos = new HashSet<>();
    /*@ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userDaos = new HashSet<>();*/
}
