
package mops.infrastructure.database.daos;

import lombok.Data;
import mops.domain.models.group.GroupVisibility;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
    private GroupVisibility visibility;

    @ManyToMany
    private Set<UserDao> userDaos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DatePollDao> datePollDaos = new HashSet<>();

    // TODO: SQL: CascadeType.ALL
    @ManyToMany(mappedBy = "groupDaos")
    private Set<QuestionPollDao> questionPollDaos = new HashSet<>();
}
