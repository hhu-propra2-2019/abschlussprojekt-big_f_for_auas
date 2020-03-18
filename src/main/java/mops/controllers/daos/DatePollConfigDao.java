package mops.controllers.daos;

import javax.persistence.Embeddable;

@Embeddable
class DatePollConfigDao {
    private boolean prioritychoice;
    private boolean anononymous;
    private boolean openforownentries;
    private boolean visibility;
    private boolean singlechoice;
}
