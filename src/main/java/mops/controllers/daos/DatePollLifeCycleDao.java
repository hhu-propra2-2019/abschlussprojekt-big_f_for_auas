package mops.controllers.daos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
class DatePollLifeCycleDao {
    @DateTimeFormat
    private LocalDateTime startdate;
    @DateTimeFormat
    private LocalDateTime enddate;
}
