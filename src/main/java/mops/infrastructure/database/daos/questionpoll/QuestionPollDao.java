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
import java.util.HashSet;
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
    private QuestionPollConfigDao configDao;
    @Embedded
    private QuestionPollMetaInfDao metaInfDao;
    @OneToOne
    private UserDao creatorUserDao;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<QuestionPollEntryDao> entryDaos;
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<UserDao> userDaos = new HashSet<>();
}
