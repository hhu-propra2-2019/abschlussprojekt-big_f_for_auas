package mops.infrastructure.database.daos.questionpoll;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.UserDao;

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
@Table(name = "questionpollentry")
public class QuestionPollEntryDao {
    @Id
    @GeneratedValue
    private Long id;
    private String choiceoption;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userDaoSet;
}
