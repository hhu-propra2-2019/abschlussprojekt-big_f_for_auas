package mops.infrastructure.database.daos.questionpoll;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.UserDao;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;


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
