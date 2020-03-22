package mops.application.services.dummy;

import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePoll;
import org.springframework.stereotype.Service;

@Service
public class DummyPublicationService implements PublicationService {

    @Override
    public boolean publishDatePoll(DatePoll datePoll) {
        return true;
    }
}
