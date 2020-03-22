package mops.infrastructure.database.daos.datepoll;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class DatePollStatusDaoKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "datepoll_link")
    private String datePollLink;
}