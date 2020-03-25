package mops.domain.models.questionpoll;
import lombok.RequiredArgsConstructor;
/**
 * Um die Kopplung zwischen Aggregaten und Packages aufrecht zu erhalten, wird hier einfach alles
 * aus PollRecordAndStatus geerbt.
 */
@RequiredArgsConstructor
public class QuestionPollRecordAndStatus extends mops.domain.models.pollstatus.PollRecordAndStatus {
    /**
     * Beendet den Questionpoll.
     */
    void terminate() { //NOPMD
        super.terminatePoll();
    }
}
