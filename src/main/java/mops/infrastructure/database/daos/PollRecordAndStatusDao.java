package mops.infrastructure.database.daos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
public class PollRecordAndStatusDao {
    @DateTimeFormat
    private LocalDateTime lastmodified;
    private boolean isterminated;
}
