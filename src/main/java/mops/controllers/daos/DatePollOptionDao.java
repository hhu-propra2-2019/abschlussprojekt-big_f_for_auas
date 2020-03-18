package mops.controllers.daos;


import org.springframework.data.annotation.Id;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;


@Entity(name = "DatePollOption")
@Table(name = "datepolloption")
public class DatePollOptionDao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private DatePollDao datePollDaoId;
    private String description;
    @Embedded
    private PollLifeCycleDao pollLifeCycleDao;
}
