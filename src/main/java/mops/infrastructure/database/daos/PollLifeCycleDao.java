package mops.infrastructure.database.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.Timespan;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollLifeCycleDao {
    @DateTimeFormat
    private LocalDateTime startdate;
    @DateTimeFormat
    private LocalDateTime enddate;

    public static PollLifeCycleDao of(Timespan pollTimeSpan) {
        return new PollLifeCycleDao(pollTimeSpan.getStartDate(), pollTimeSpan.getEndDate());
    }
}
