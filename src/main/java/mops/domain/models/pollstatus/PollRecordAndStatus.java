package mops.domain.models.pollstatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mops.domain.models.user.UserId;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Diese Klasse kümmert sich um das Handling des Status einer Abstimmung.
 * Ist die Abstimmung noch offen? Schon beendet? Gibt es neue Optionen?
 * Außerdem wird der userspezifische Status gehandled:
 * - OPEN: Der User hat noch nicht abgestimmt
 * - REOPENED: Der User hat schon abgestimmt, aber die Umfrage wurde geändert oder erweitert
 * - ONGOING: Der User hat schon abgestimmt, aber die Umfrage ist noch nicht beendet
 * - TERMINATED: Die Umfrage ist beendet
 * <p>
 * Mit User sind User*innen jedweden Geschlechts gemeint.
 * <p>
 * Die Klasse wird sowohl an das DatePoll- als auch an das QuestionPoll-Package vererbt.
 */

@RequiredArgsConstructor
public class PollRecordAndStatus {

    @Getter
    @Setter
    private LocalDateTime lastModified = LocalDateTime.now();
    @Getter
    private transient Map<UserId, PollStatus> votingRecord = new ConcurrentHashMap<>();
    @Getter
    private transient boolean isTerminated;

    /**
     * Sobald ein User abgestimmt hat, wird sein Status auf ONGOING gesetzt und bleibt dabei bis zum Ende der Umfrage,
     * es sei denn, die Umfrage wird zwischenzeitlich geändert.
     *
     * @param user der User, der abgestimmt hat
     */
    protected void recordUserVote(UserId user) {
        votingRecord.put(user, PollStatus.ONGOING);
    }

    /**
     * Jederzeit kann der Status für einen bestimmten User abgefragt werden. Wenn der User noch nicht abgestimmt hat,
     * ist der Status OPEN. Ansonsten wird der Status aus der Hashtable geladen oder durch isTerminated bestimmt.
     *
     * @param user der User, für den der Status abgefragt werden soll
     * @return der Status (siehe oben)
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public PollStatus getUserStatus(UserId user) {

        PollStatus status = PollStatus.TERMINATED;

        if (!isTerminated) {
            status = votingRecord.getOrDefault(user, PollStatus.OPEN);
        }
        return status;
    }

    /**
     * Alle User in der Hashtable haben entweder den Status ONGOING oder REOPENED. Deshalb wird, wenn die Umfrage
     * modifiziert wurde, für alle User der Status auf REOPENED gesetzt.
     */
    protected void markPollAsModified() {
        lastModified = LocalDateTime.now();
        votingRecord.forEach((user, status) -> votingRecord.put(user, PollStatus.REOPENED));
    }

    /**
     * Mit dieser Implementierung muss terminatePoll() von extern aufgerufen werden, sobald das Enddatum der Umfrage
     * abgelaufen ist. Man könnte den Wert auch bei jedem Zugriff auf das Aggregat aktualisieren.
     */
    protected void terminatePoll() {
        isTerminated = true;
    }

    /**
     * Der andere Ansatz, wie oben geschildert. Muss zuerst aufgerufen werden, wenn auf das Aggregat zugegriffen wird.
     *
     * @param endDate Das Datum, an dem die Umfrage endet
     */
    protected void updatePollStatus(LocalDateTime endDate) {
        if (endDate.isBefore(LocalDateTime.now())) {
            isTerminated = true;
        }
    }
}
