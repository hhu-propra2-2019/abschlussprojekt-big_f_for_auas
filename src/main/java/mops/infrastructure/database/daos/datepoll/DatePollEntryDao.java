package mops.infrastructure.database.daos.datepoll;


import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.TimespanDao;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import java.util.Set;


@Getter
@Setter
@Entity(name = "DatePollOptionDao")
@Table(name = "datepolloption")
public class DatePollEntryDao {
    //TODO: add yes/maybe/no votes ???
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private DatePollDao datePoll;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<UserDao> userVotesFor;
    @Embedded
    private TimespanDao timespanDao;
}
