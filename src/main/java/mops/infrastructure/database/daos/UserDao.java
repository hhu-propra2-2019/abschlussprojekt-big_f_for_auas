package mops.infrastructure.database.daos;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollOptionDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserDao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DatePollDao> datePollSet;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DatePollOptionDao> datePollOptionSet;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<QuestionPollDao> questionPollSet;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<QuestionPollEntryDao> questionPollEntrySet;

}
