package mops.infrastructure.database.daos.datepoll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mops.domain.models.datepoll.DatePollConfig;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatePollConfigDao {
    private boolean prioritychoice;
    private boolean anonymous;
    private boolean openforownentries;
    private boolean visible;
    private boolean singlechoice;
    private boolean voteIsEditable;

    public static DatePollConfigDao of(DatePollConfig datePollConfig) {
        return new DatePollConfigDao(
                datePollConfig.isPriorityChoice(),
                datePollConfig.isAnonymous(),
                datePollConfig.isOpenForOwnEntries(),
                datePollConfig.isOpen(),
                datePollConfig.isSingleChoice(),
                datePollConfig.isVoteIsEditable()
        );
    }
}
