package mops.controllers.daos;

import javax.persistence.*;

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

    private Enum<>;

}
