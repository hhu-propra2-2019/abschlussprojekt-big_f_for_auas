package mops.infrastructure.database.daos;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<DatePollDao> datePollDao;
}
