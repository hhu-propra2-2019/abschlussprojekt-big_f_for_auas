package mops.domain.models;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.EqualsAndHashCode;

@With
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DatePollConfig {

    /**
     * true: User kann selbst Termine zur Abstimmung hinzufügen.
     * false: User kann keine Termine zur Abstimmung hinzufügen.
     */
    private boolean usersCanCreateOption = false;

    /**
     * true: Jeder, der abstimmt kann nur einen Termin auswählen.
     * false: Jeder, der abstimmt kann mehrere Termine auswählen.
     */
    private boolean singleChoiceDatePoll = true;

    /**
     * true: Man kann für die Auswahl der Termine nach einem Ampelystem Prioritäten angeben.
     * false: Man kann für jeden Termin nur 'ich kann' oder 'ich kann nicht' angeben.
     */
    private boolean priorityChoice = false;

    /**
     * true: Es ist nicht einsehbar, wer für welche Termine gestimmt hat.
     * false: Es ist einsehbar, wer für welche Termine gestimmt hat.
     */
    private boolean datePollIsAnonymous = false;

    /**
     * true: Die DatePoll ist öffentlich; die Liste der Participants (siehe Klasse DatePoll) ist irrelevant.
     * false: Die DatePoll ist nur für User in der Liste der Participants (siehe Klasse DatePoll) bestimmt.
     */
    private boolean datePollIsPublic = false;

    /**
     * Setter für datePollIsPublic.
     * @param isPublic boolean, auf den datePollIsPublic gesetzt wird
     */
    void setDatePollIsPublic(final boolean isPublic) {
        this.datePollIsPublic = isPublic;
    }
}
