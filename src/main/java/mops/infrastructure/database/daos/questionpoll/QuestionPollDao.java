package mops.infrastructure.database.daos.questionpoll;

import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.PollRecordAndStatusDao;
import mops.infrastructure.database.daos.UserDao;


import javax.persistence.Id;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "questionpoll")
public class QuestionPollDao {
    @Id
    private String link;
    @Embedded
    private PollRecordAndStatusDao pollRecordAndStatusDao;
    @Embedded
    private QuesionPollConfigDao questionPollConfigDao;
    @Embedded
    private QuestionPollMetaInfDao questionPollMetaInfDao;
    @OneToOne
    private UserDao creatorUser;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<QuestionPollEntryDao> questionPollEntrySet;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userSet;
}
