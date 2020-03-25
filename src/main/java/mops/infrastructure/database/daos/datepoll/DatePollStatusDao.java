package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mops.infrastructure.database.daos.PollStatusEnum;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "datepollstatus")
@NoArgsConstructor
@AllArgsConstructor
public class DatePollStatusDao {
    @EmbeddedId
    private DatePollStatusDaoKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("user_id")
    private UserDao participant;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("datepoll_link")
    private DatePollDao datePoll;

    @Enumerated(EnumType.STRING)
    private PollStatusEnum userStatusForDatePoll;

}
