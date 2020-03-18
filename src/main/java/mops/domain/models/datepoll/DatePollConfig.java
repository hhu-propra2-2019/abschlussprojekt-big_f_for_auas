package mops.domain.models.datepoll;

import lombok.Value;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public final class DatePollConfig implements ValidateAble {

    /**
     * true: User kann selbst Termine zur Abstimmung hinzufügen.
     * false: User kann keine Termine zur Abstimmung hinzufügen.
     */
    private boolean openForOwnEntries;
    /**
     * true: Jeder, der abstimmt kann nur einen Termin auswählen.
     * false: Jeder, der abstimmt kann mehrere Termine auswählen.
     */
    private boolean singleChoice;
    /**
     * true: Man kann für die Auswahl der Termine nach einem Ampelystem Prioritäten angeben.
     * false: Man kann für jeden Termin nur 'ich kann' oder 'ich kann nicht' angeben.
     */
    private boolean priorityChoice;
    /**
     * true: Es ist nicht einsehbar, wer für welche Termine gestimmt hat.
     * false: Es ist einsehbar, wer für welche Termine gestimmt hat.
     */
    private boolean anonymous;
    /**
     * true: Die DatePoll ist öffentlich; die Liste der Participants (siehe Klasse DatePoll) ist irrelevant.
     * false: Die DatePoll ist nur für User in der Liste der Participants (siehe Klasse DatePoll) bestimmt.
     */
    private boolean open;

    /**
     * NoArgsConstructor - Default Werte fuer die Konfiguration einer Terminfindung.
     */
    public DatePollConfig() {
        this.priorityChoice = false;
        this.anonymous = false;
        this.openForOwnEntries = false;
        this.open = false;
        this.singleChoice = false;
    }

    @Override
    public Validation validate() {
        return Validation.noErrors();
    }
}
