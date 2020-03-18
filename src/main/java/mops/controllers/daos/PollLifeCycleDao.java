package mops.controllers.daos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
class PollLifeCycleDao {
    @DateTimeFormat
    private LocalDateTime startdate;
    @DateTimeFormat
    private LocalDateTime enddate;
}
