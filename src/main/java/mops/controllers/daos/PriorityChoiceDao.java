package mops.controllers.daos;

import javax.persistence.Entity;

@Entity
public class PriorityChoiceDao {
    private DatePollOptionDao datePollOptionDao;
    private ParticipantDao participantDao;
}
