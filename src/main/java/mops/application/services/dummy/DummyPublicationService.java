package mops.application.services.dummy;

import lombok.NoArgsConstructor;
import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.questionpoll.QuestionPoll;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class DummyPublicationService implements PublicationService {

    /**
     * Methode zum Speichern eines DatePolls in die Datenbank.
     * @param datePoll der QuestionPoll der gespeichert werden soll.
     * @return boolean, ob es geklappt hat.
     */
    @Override
    public boolean saveAndPublish(DatePoll datePoll) {
        return true;
    }

    /**
     * Methode zum Speichern eines Questionpolls in die Datenbank.
     * @param questionPoll der QuestionPoll der gespeichert werden soll.
     * @return boolean, ob es geklappt hat.
     */
    @Override
    public boolean saveAndPublish(QuestionPoll questionPoll) {
        return true;
    }
}
