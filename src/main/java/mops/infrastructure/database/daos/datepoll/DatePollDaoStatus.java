package mops.infrastructure.database.daos.datepoll;

import mops.infrastructure.database.daos.PollStatusEnum;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.EmbeddedId;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.EnumType;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "userstatusfordatepoll")
public class DatePollDaoStatus {
    @EmbeddedId
    private DatePollDaoUserDaoKey id;

    @ManyToOne
    @MapsId("user_id")
    private UserDao participant;

    @ManyToOne
    @MapsId("datepoll_link")
    private DatePollDao datePoll;

    @Enumerated(EnumType.STRING)
    private PollStatusEnum userStatusForDatePoll;

}
