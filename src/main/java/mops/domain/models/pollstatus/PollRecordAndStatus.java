package mops.domain.models.pollstatus;

import lombok.Getter;
import lombok.Setter;
import mops.domain.models.user.User;

import java.time.LocalDateTime;
import java.util.Hashtable;

/**
 * Diese Klasse kümmert sich um das Handling des Status einer Abstimmung.
 * Ist die Abstimmung noch offen? Schon beendet? Gibt es neue Optionen?
 * Außerdem wird der userspezifische Status gehandled:
 *  - OPEN: Der User hat noch nicht abgestimmt
 *  - REOPENED: Der User hat schon abgestimmt, aber die Umfrage wurde geändert oder erweitert
 *  - ONGOING: Der User hat schon abgestimmt, aber die Umfrage ist noch nicht beendet
 *  - TERMINATED: Die Umfrage ist beendet
 *
 *  Mit User sind User*innen jedweden Geschlechts gemeint.
 */

public final class PollRecordAndStatus {

    @Getter
    @Setter
    private LocalDateTime lastModified = LocalDateTime.now();
    private Hashtable<User, PollStatus> votingRecord = new Hashtable<>();
    private boolean isTerminated = false;

    /**
     * Sobald ein User abgestimmt hat, wird sein Status auf ONGOING gesetzt und bleibt dabei bis zum Ende der Umfrage,
     * es sei denn, die Umfrage wird zwischenzeitlich geändert.
     * @param user
     */
    void recordUserVote(User user) {
        votingRecord.put(user, PollStatus.ONGOING);
    }

    /**
     * Jederzeit kann der Status für einen bestimmten User abgefragt werden. Wenn der User noch nicht abgestimmt hat,
     * ist der Status OPEN. Ansonsten wird der Status aus der Hashtable geladen oder durch isTerminated bestimmt.
     * @param user
     * @return
     */
    PollStatus getUserStatus(User user) {
        if (!isTerminated) {
            return votingRecord.getOrDefault(user, PollStatus.OPEN);
        }
        return PollStatus.TERMINATED;
    }

    /**
     * Alle User in der Hashtable haben entweder den Status ONGOING oder REOPENED. Deshalb wird, wenn die Umfrage
     * modifiziert wurde, für alle User der Status auf REOPENED gesetzt.
     */
    void pollHasBeenModified() {
        lastModified = LocalDateTime.now();
        votingRecord.forEach((user, status) -> votingRecord.put(user, PollStatus.REOPENED));
    }

    /**
     * Mit dieser Implementierung muss terminatePoll() von extern aufgerufen werden, sobald das Enddatum der Umfrage
     * abgelaufen ist. Man könnte den Wert auch bei jedem Zugriff auf das Aggregat aktualisieren.
     */
    void terminatePoll() {
        isTerminated = true;
    }
}
