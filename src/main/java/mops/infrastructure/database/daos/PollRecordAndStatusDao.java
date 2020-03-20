package mops.infrastructure.database.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
public class PollRecordAndStatusDao {
    @DateTimeFormat
    private LocalDateTime lastmodified;
}
