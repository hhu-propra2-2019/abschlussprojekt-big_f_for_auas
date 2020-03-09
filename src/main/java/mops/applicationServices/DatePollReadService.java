package mops.applicationServices;

import mops.domain.models.DatePoll.DatePoll;
import mops.domain.models.DatePoll.DatePollId;
import org.springframework.stereotype.Service;

/**
 * TODO Implementierung des Services, der die Daten zu einer Terminfindung (anhand eines Links einer ID ...) ausliest.
 * Die Daten der Termine gehen an den Controller.
 * Termine werden in Form von DatePoll Objekten bereitgestellt, in denen sich die einzelnen Termine befinden: datePollOptions.
 *
 * TODO Wo werden die Stimmen fuer eine datePollOption gesammelt? - In dem Objekt selbst? - Absprache mit QuestionPoll.
 */
@Service
public class DatePollReadService {

    /**
     *
     * @param datePollID
     * @return
     */
    public DatePoll readDatePoll(DatePollId datePollID) {
        //return getDatePollByID(datePollID);
    }
}