package mops.domain.models.datepoll;

import lombok.RequiredArgsConstructor;

/**
 * Um die Kopplung zwischen Aggregaten und Packages aufrecht zu erhalten, wird hier einfach alles
 * aus PollRecordAndStatus geerbt.
 */
@RequiredArgsConstructor
class DatePollRecordAndStatus extends mops.domain.models.pollstatus.PollRecordAndStatus {

    void terminate() { //NOPMD
        super.terminatePoll();
    }
}
