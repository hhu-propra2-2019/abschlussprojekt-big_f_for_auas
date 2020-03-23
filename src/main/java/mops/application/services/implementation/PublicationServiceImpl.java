package mops.application.services.implementation;
import mops.application.services.PublicationService;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.infrastructure.database.repositories.DatePollRepositoryImpl;
import mops.infrastructure.database.repositories.QuestionPollRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final transient DatePollRepositoryImpl datePollRepository;
    private final transient QuestionPollRepositoryImpl questionPollRepository;

    @Autowired
    public PublicationServiceImpl(DatePollRepositoryImpl datePollRepository,
                                  QuestionPollRepositoryImpl questionPollRepository) {
        this.datePollRepository = datePollRepository;
        this.questionPollRepository = questionPollRepository;
    }
    /**
     * Methode zum Speichern eines DatePolls in die Datenbank.
     * @param datePoll der QuestionPoll der gespeichert werden soll.
     * @return boolean, ob es geklappt hat.
     */
    @Override
    public boolean saveAndPublish(DatePoll datePoll) {
        boolean saveSuccessful;
        try {
            datePollRepository.save(datePoll);
            saveSuccessful = true;
        } catch (IllegalArgumentException e) {
            saveSuccessful = false;
        }
        return  saveSuccessful;
    }

    /**
     * Methode zum Speichern eines Questionpolls in die Datenbank.
     * @param questionPoll der QuestionPoll der gespeichert werden soll.
     * @return boolean, ob es geklappt hat.
     */
    @Override
    public boolean saveAndPublish(QuestionPoll questionPoll) {
        boolean saveSuccessful;
        try {
            questionPollRepository.save(questionPoll);
            saveSuccessful = true;
        } catch (IllegalArgumentException e) {
            saveSuccessful = false;
        }
        return  saveSuccessful;
    }
}
