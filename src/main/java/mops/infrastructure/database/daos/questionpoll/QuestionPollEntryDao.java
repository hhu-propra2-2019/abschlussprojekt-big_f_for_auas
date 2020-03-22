package mops.infrastructure.database.daos.questionpoll;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    private String entryName;
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userVotesFor = new HashSet<>();
}
