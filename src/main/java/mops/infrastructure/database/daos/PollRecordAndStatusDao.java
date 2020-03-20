package mops.infrastructure.database.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.pollstatus.PollRecordAndStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
public class PollRecordAndStatusDao {
    @DateTimeFormat
    private LocalDateTime lastmodified;

    public static PollRecordAndStatusDao of(PollRecordAndStatus pollRecordAndStatus) {
        return new PollRecordAndStatusDao(
                pollRecordAndStatus.getLastModified()
        );
    }
}
