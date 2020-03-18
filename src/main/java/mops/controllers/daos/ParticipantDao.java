package mops.controllers.daos;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ParticipantDao {
    @OneToOne
    private UserDao userAsParticipantDao;
}
