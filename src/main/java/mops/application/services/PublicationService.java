package mops.application.services;

import mops.domain.models.datepoll.DatePoll;

public interface PublicationService {

    boolean publishDatePoll(DatePoll datePoll);
}
