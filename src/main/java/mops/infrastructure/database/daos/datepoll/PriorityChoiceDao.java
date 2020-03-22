package mops.infrastructure.database.daos.datepoll;


import lombok.Getter;
import lombok.Setter;
import mops.infrastructure.database.daos.UserDao;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Getter
@Setter
@Entity(name = "PriorityChoice")
@Table(name = "prioritychoice")
public class PriorityChoiceDao  {

    @EmbeddedId
    private PriorityChoiceDaoKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("user_id")
    private UserDao participant;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("datepollentry_id")
    private DatePollEntryDao datePollEntry;

    @Enumerated(EnumType.STRING)
    private PriorityTypeEnum datePollPriority;

}
