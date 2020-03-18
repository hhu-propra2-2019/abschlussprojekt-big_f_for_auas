package mops.controllers.daos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
class PollRecordAndStatusDao {
    @DateTimeFormat
    private LocalDateTime lastmodified;
    private boolean isterminated;
}
