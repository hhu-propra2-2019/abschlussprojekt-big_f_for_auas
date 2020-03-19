package mops.infrastructure.database.daos;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
class DatePollConfigDao {
    private boolean prioritychoice;
    private boolean anononymous;
    private boolean openforownentries;
    private boolean visibility;
    private boolean singlechoice;
}
