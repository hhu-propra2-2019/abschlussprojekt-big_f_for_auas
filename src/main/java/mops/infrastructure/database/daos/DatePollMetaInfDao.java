package mops.infrastructure.database.daos;

import lombok.Data;

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
