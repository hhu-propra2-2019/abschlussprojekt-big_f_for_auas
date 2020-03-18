package mops.controllers.daos;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private DatePollDao datePollDao;
}
