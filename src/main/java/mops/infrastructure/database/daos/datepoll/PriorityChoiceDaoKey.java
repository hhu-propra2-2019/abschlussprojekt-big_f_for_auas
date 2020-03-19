package mops.infrastructure.database.daos.datepoll;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class PriorityChoiceDaoKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "datepolloption_id")
    private Long datePollDaoId;
}
