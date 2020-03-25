package mops.infrastructure.database.daos;

import lombok.Data;
import mops.domain.models.group.Group;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "usergroup")
public class GroupDao {
    @Id
    private String id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Group.GroupVisibility visibility;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserDao> userDaos = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupDaos")
    private Set<DatePollDao> datePollDaos = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "groupDaos")
    private Set<QuestionPollDao> questionPollDaos = new HashSet<>();
}
