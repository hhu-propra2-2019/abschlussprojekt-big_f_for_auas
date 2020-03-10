package mops.domain.models.datepoll;

import java.util.Date;

class DatePollLifeCycle {

    private Date startDate;
    private Date endDate;

    /**
     * Erzeugt ein Value Objekt für die Lebenspanne einer Terminfindung.
     * @param startDate start der Terminfindung
     * @param endDate ende der Terminfindung
     */
    DatePollLifeCycle(Date startDate, Date endDate) {
        if(startDate.compareTo(endDate) < 0) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
