package mops.infrastructure.database.daos;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserDao {
    @Setter
    @Id
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<DatePollDao> datePollSet;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userVotesFor")
    private Set<DatePollEntryDao> datePollEntrySet;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<QuestionPollDao> questionPollSet;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userVotesFor")
    private Set<QuestionPollEntryDao> questionPollEntrySet;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<GroupDao> groupSet;
}
