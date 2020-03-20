package mops.infrastructure.database.daos;

import lombok.Getter;
import lombok.Setter;
import mops.domain.models.user.UserId;
import mops.infrastructure.database.daos.datepoll.DatePollDao;
import mops.infrastructure.database.daos.datepoll.DatePollOptionDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollDao;
import mops.infrastructure.database.daos.questionpoll.QuestionPollEntryDao;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserDao {
    @Setter
    @Id
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<DatePollDao> datePollSet;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userVotesFor")
    private Set<DatePollOptionDao> datePollOptionSet;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<QuestionPollDao> questionPollSet;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userVotesFor")
    private Set<QuestionPollEntryDao> questionPollEntrySet;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userSet")
    private Set<GroupDao> groupSet;

    @Enumerated(EnumType.STRING)
    private PollStatusEnum pollStatusEnum;

    public static UserDao of(UserId userId) {
        UserDao creator = new UserDao();
        creator.setId(Long.parseLong(userId.getId()));
        return creator;
    }

}
