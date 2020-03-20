package mops.infrastructure.database.daos;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.util.Set;

@Data
@Entity
@Table(name = "group")
class GroupDao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<UserDao> userSet;
}
