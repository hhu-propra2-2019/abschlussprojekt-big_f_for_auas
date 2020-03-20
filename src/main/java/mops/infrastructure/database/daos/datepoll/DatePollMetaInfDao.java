package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.infrastructure.database.daos.PollLifeCycleDao;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Data
@AllArgsConstructor
public class DatePollMetaInfDao {
    private String description;
    private String location;
    @Embedded
    private PollLifeCycleDao pollLifeCycleDao;

    public static DatePollMetaInfDao of(DatePollMetaInf datePollMetaInf) {
        PollLifeCycleDao lifeCycleDao = PollLifeCycleDao.of(datePollMetaInf.getDatePollLifeCycle());
        return new DatePollMetaInfDao(
                datePollMetaInf.getDatePollDescription().getDescription(),
                datePollMetaInf.getDatePollLocation().getLocation(), lifeCycleDao);
    }
}
