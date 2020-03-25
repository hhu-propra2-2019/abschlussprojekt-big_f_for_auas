package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatePollStatusDaoKey implements Serializable {
    public static final long serialVersionUID = 4140192L;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "datepoll_link")
    private String datePollLink;
}
