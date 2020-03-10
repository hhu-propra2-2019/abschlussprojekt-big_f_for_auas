package mops.domain.models.datepoll;

import lombok.Value;

import java.util.Date;

@Value
public class DatePollLifeCycle {
    private Date startDate;
    private Date endDate;

    /**
     * Erzeugt ein Value Objekt für die Lebenspanne einer Terminfindung.
     * @param startDate start der Terminfindung
     * @param endDate ende der Terminfindung
     */
    public DatePollLifeCycle(Date startDate, Date endDate) {
         this.startDate = startDate;
         this.endDate = endDate;
    }
}
