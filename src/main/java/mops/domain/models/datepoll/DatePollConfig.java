package mops.domain.models.datepoll;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@With
@AllArgsConstructor
public class DatePollConfig {

    /**
     * true: User kann selbst Termine zur Abstimmung hinzufügen.
     * false: User kann keine Termine zur Abstimmung hinzufügen.
     */
    private boolean usersCanCreateOption;
    /**
     * true: Jeder, der abstimmt kann nur einen Termin auswählen.
     * false: Jeder, der abstimmt kann mehrere Termine auswählen.
     */
    private boolean singleChoiceDatePoll;
    /**
     * true: Man kann für die Auswahl der Termine nach einem Ampelystem Prioritäten angeben.
     * false: Man kann für jeden Termin nur 'ich kann' oder 'ich kann nicht' angeben.
     */
    private boolean priorityChoice;
    /**
     * true: Es ist nicht einsehbar, wer für welche Termine gestimmt hat.
     * false: Es ist einsehbar, wer für welche Termine gestimmt hat.
     */
    private boolean datePollIsAnonymous;
    /**
     * true: Die DatePoll ist öffentlich; die Liste der Participants (siehe Klasse DatePoll) ist irrelevant.
     * false: Die DatePoll ist nur für User in der Liste der Participants (siehe Klasse DatePoll) bestimmt.
     */
    private boolean datePollIsPublic;

    /**
     * NoArgsConstructor - Default Werte fuer die Konfiguration einer Terminfindung.
     */
    public DatePollConfig() {
       this.usersCanCreateOption = false;
       this.singleChoiceDatePoll = true;
       this.priorityChoice = false;
       this.datePollIsAnonymous = true;
       this.datePollIsPublic = false;
    }
}
