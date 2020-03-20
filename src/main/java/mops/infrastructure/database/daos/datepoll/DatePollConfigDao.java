package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
public class DatePollConfigDao {
    private boolean prioritychoice;
    private boolean anonymous;
    private boolean openforownentries;
    private boolean visible;
    private boolean singlechoice;
}
