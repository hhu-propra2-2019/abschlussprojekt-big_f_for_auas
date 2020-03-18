package mops.controllers.daos;

import org.springframework.data.annotation.Id;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.Set;

@SuppressWarnings("checkstyle:WhitespaceAround")
@Entity(name = "DatePoll")
@Table(name = "datepoll")
public class DatePollDao {
    @Id
    @GeneratedValue
    private int id;
    private String link;
    @Embedded
    private PollRecordAndStatusDao pollRecordAndStatusDao;
    @Embedded
    private DatePollConfigDao datePollConfigDao;
    @Embedded
    private DatePollMetaInfDao datePollMetaInfDao;
    @Embedded
    private UserDao creatorUserDao;
    @OneToMany(
            mappedBy = "datepoll",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DatePollOptionDao> datePollOptionDaoSet;
}
