package mops.infrastructure.database.daos.datepoll;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity(name = "DatePollEntryDao")
@Table(name = "datepollentry")
@NoArgsConstructor
public class DatePollEntryDao {
    //TODO: add yes/maybe/no votes ???
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private DatePollDao datePoll;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<UserDao> userVotesFor = new HashSet<>();
    @Embedded
    private TimespanDao timespanDao;
}
