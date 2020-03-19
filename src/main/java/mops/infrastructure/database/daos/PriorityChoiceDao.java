package mops.infrastructure.database.daos;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Enumerated;
import javax.persistence.MapsId;
import javax.persistence.EnumType;

@Entity(name = "PriorityChoice")
@Table(name = "prioritychoice")
public class PriorityChoiceDao  {

    @EmbeddedId
    private PriorityChoiceDaoKey id;

    @ManyToOne
    @MapsId("user_id")
    private UserDao participant;

    @ManyToOne
    @MapsId("datepolloption_id")
    private DatePollOptionDao datePollOptionDao;

    @Enumerated(EnumType.STRING)
    private Enum<PriorityType> datePollPriority;

}
