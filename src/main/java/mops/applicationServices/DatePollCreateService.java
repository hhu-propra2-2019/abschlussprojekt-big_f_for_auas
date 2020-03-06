package mops.applicationServices;

import mops.domain.models.*;
import mops.domain.models.DatePoll.DatePollBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dieser Service dient zur Erstellung eines(!) Terminfindungs-Objekt (DatePoll).
 * Der Service erhaelt die Daten zur Termindung durch den User (creator).
 * Die Terminfingung wird dann durch einen Lombok-Builder erzeugt. Dieser durchlaueft mehrere Methoden.
 *
 * @author Niclas, Tim, Lukas
 */
@Service
public class DatePollCreateService {
    /**
     *Initiale Methode zur Erstellung der Terminfindung.
     * @param creator User der die Terminfindung erstellt.
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilder initializeDatePoll(final User creator) {
        DatePollConfig config = new DatePollConfig();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        DatePollID datePollID = new DatePollID();
        datePollBuilder.datePollID(datePollID);
        datePollBuilder.creator(creator);
        datePollBuilder.datePollConfig(config);
        return datePollBuilder;
    }

    /**
     * Zweite Instanz zur Erstellung der Terminfindung.
     * Hinzufuegen der Meta-Informationen: Titel, Ort, Beschreibung.
     *
     * @param datePollBuilder Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param datePollMetaInf Meta-Informationen: Titel, Ort, Beschreibund.
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilder addDatePollMetaInf(final DatePollBuilder datePollBuilder, final DatePollMetaInf datePollMetaInf) {
        datePollBuilder.datePollMetaInf(new DatePollMetaInf()); //Title Location Description
        return datePollBuilder;
    }

    /**
     * @param datePollBuilder
     * @param config
     * @param openDatePoll
     * @return
     */
    public DatePollBuilder openOrClosedPoll(final DatePollBuilder datePollBuilder, final DatePollConfig config, final boolean openDatePoll) {
        DatePollConfig newConfig = config.withUsersCanCreateOption(openDatePoll);
        datePollBuilder.datePollConfig()
        datePollBuilder.datePollConfig(newConfig);
        return datePollBuilder;
    }

    /**
     * Vierte Instanz zur Erstellung der Terminfindung.
     * Liste der Datum-Eintraege an denen der Termin stattfinden kann.
     * @param datePollBuilder Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param datePollOptions Alle Datums-Eintraege des User creator.
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilder initDatePollOptionList(final DatePollBuilder datePollBuilder, final List<DatePollOption> datePollOptions) {
        datePollBuilder.datePollOptionList(datePollOptions);
        return datePollBuilder;
    }
    /**
     * Fuenfte Instanz zur Erstellung der Terminfindung.
     * Angabe ob es sich um ein "single" oder "multiple" Choice Terminfindungs-Objekt handelt.
     * @param datePollBuilder Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param singleChoicePoll Hat der User nur eine Wahlmoeglichkeit (true) oder mehrere (false).
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilder setDatePollChoiceType(final DatePollBuilder datePollBuilder, final boolean singleChoicePoll) {
        datePollBuilder.singleChoiceDatePoll(singleChoicePoll);
        return datePollBuilder;
    }

    /**
     * Fuenfte Instanz zur Erstellung der Terminfindung.
     * Angabe ob es priorisierte Auswahlmoeglichkeiten geben soll.
     * @param datePollBuilder Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param priority Prioritaet an (true) oder aus (false).
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilder setDatePollChoicePriority(final DatePollBuilder datePollBuilder, final boolean priority) {
        datePollBuilder.priorityChoice(priority);
        return datePollBuilder;
    }

    /**
     * Sechste Instanz zur Erstellung der Terminfindung.
     * Angabe ob die Terminfindung anonym (true) oder öffentlich (false) stattfinden soll.
     * @param datePollBuilder Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param isAnonymous Anonym (true) oder öffentlich (false).
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilder setDatePollVisibilty(final DatePollBuilder datePollBuilder, final boolean isAnonymous) {
        datePollBuilder.datePollIsAnonymous(isAnonymous);
        return datePollBuilder;
    }

    /**
     * Die Methode gibt das fertige DatePoll Objekt zurueck.
     * @param datePollBuilder Das Lombok-Builder Objekt aus initializeDatePoll.
     * @return Das fertige DatePoll Objekt.
     */
    public DatePoll buildDatePoll(final DatePollBuilder datePollBuilder) {
        return datePollBuilder.build();
    }
}
