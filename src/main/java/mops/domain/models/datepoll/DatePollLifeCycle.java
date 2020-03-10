package mops.domain.models.datepoll;

import java.util.Date;

class DatePollLifeCycle {
    private Date startDate;
    private Date endDate;

    DatePollLifeCycle(Date startDate, Date endDate) {
        if(startDate.compareTo(endDate) < 0){
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
