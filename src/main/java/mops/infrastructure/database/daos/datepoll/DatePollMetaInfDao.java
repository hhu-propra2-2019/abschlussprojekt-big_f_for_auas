package mops.infrastructure.database.daos.datepoll;

import lombok.Data;
import mops.infrastructure.database.daos.PollLifeCycleDao;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Data
class DatePollMetaInfDao {
    private String description;
    private String location;
    @Embedded
    private PollLifeCycleDao pollLifeCycleDao;
}
