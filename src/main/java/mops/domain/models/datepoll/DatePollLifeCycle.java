package mops.domain.models.datepoll;

import java.util.Date;

class DatePollLifeCycle {
    private Date startDate;
    private Date endDate;

    /**
     * Validierung der Datumsangaben im Konstruktor.
     * @param startDate
     * @param endDate
     */
    DatePollLifeCycle(Date startDate, Date endDate) {
        //startDate occurs before endDate
        if (startDate.compareTo(endDate) < 0) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
