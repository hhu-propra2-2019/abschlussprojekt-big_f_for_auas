package mops.infrastructure.database.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
public class PollLifeCycleDao {
    @DateTimeFormat
    private LocalDateTime startdate;
    @DateTimeFormat
    private LocalDateTime enddate;
}
