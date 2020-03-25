package mops.infrastructure.database.daos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mops.infrastructure.database.daos.datepoll.DatePollEntryDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserDao {
    @Setter
    @Id
    private String id;

    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userDaos")
    private Set<DatePollDao> datePollSet = new HashSet<>();*/
  
    //CascadeType.MERGE
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userVotesFor")
    private Set<DatePollEntryDao> datePollEntrySet = new HashSet<>();

    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userDaos")
    private Set<QuestionPollDao> questionPollSet = new HashSet<>();*/
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userVotesFor")
    private Set<QuestionPollEntryDao> questionPollEntrySet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userDaos")
    private Set<GroupDao> groupSet = new HashSet<>();
}
