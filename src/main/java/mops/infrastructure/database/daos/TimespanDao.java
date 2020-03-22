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
@SuppressWarnings("PMD.ShortMethodName")
public class TimespanDao {
    @DateTimeFormat
    private LocalDateTime startDate;
    @DateTimeFormat
    private LocalDateTime endDate;

    public static TimespanDao of(Timespan pollTimeSpan) {
        return new TimespanDao(pollTimeSpan.getStartDate(), pollTimeSpan.getEndDate());
    }
}
