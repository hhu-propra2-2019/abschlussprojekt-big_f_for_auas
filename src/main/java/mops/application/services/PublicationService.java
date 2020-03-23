package mops.application.services;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.questionpoll.QuestionPoll;

public interface PublicationService {

    boolean saveAndPublish(DatePoll datePoll);
    boolean saveAndPublish(QuestionPoll questionPoll);
}
