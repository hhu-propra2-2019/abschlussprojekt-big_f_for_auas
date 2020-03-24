package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatePollConfigDao {
    private boolean voteIsEditable;
    private boolean openForOwnEntries;
    private boolean singleChoice;
    private boolean priorityChoice;
    private boolean anonymous;
    private boolean open;
}
