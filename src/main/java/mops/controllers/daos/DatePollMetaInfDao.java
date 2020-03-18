package mops.controllers.daos;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
class DatePollMetaInfDao {
    private String description;
    private String location;
    @Embedded
    private DatePollLifeCycleDao datePollLifeCycleDao;
}
