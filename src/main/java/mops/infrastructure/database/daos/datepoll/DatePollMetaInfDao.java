package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.infrastructure.database.daos.TimespanDao;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatePollMetaInfDao {
    private String title;
    private String description;
    private String location;
    @Embedded
    private TimespanDao timespan;
}
