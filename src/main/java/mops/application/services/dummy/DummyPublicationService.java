package mops.application.services.dummy;

import lombok.NoArgsConstructor;
import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePoll;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class DummyPublicationService implements PublicationService {

    /**
     * Baldmöglichst ersetzen.
     * @param datePoll ...
     * @return ...
     */
    @Override
    public boolean publishDatePoll(DatePoll datePoll) {
        return false;
    }
}
