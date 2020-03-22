package mops.infrastructure.database.daos.questionpoll;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.datepoll.DatePollDao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "questionpollentry")
public class QuestionPollEntryDao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private QuestionPollDao questionPoll;
    private String entryName;
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userVotesFor = new HashSet<>();
}
