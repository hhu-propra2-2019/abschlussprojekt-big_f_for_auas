package mops.application.services;

import mops.domain.repositories.QuestionPollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO: was ist das hier?
@Service
public class QuestionPollSyndicationService {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    // will be used in the future (hopefully)
    private final transient QuestionPollRepository questionPollRepo;

    @Autowired
    public QuestionPollSyndicationService(QuestionPollRepository questionPollRepo) {
        this.questionPollRepo = questionPollRepo;
    }

//    /**
//     * Schließt den Builder Prozess ab und speichert den generierte QuestionPolll ab.
//     * Gibt generierte Url zurück
//     *
//     * @param builder
//     * @return QuestionPollLink
//     */
    /*
    public PollLink publishQuestionPoll(final QuestionPollBuilder builder) {
        return questionPollRepo.save(builder.build());
    }
    */
}
