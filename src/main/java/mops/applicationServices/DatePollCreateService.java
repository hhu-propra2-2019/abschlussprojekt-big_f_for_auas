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
    public DatePollBuilderAndView initializeDatePoll(final User creator) {
        DatePollConfig config = new DatePollConfig();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        DatePollID datePollID = new DatePollID();
        datePollBuilder.datePollID(datePollID);
        datePollBuilder.creator(creator);
        datePollBuilder.datePollConfig(config);
        return new DatePollBuilderAndView(datePollBuilder,config);
    }

    /**
     * Zweite Instanz zur Erstellung der Terminfindung.
     * Hinzufuegen der Meta-Informationen: Titel, Ort, Beschreibung.
     *
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param datePollMetaInf Meta-Informationen: Titel, Ort, Beschreibund.
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilderAndView addDatePollMetaInf(final DatePollBuilderAndView datePollBuilderAndView, final DatePollMetaInf datePollMetaInf) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        builder.datePollMetaInf(new DatePollMetaInf()); //Title Location Description
        return datePollBuilderAndView.updateState(builder);
    }



    /**
     * Vierte Instanz zur Erstellung der Terminfindung.
     * Liste der Datum-Eintraege an denen der Termin stattfinden kann.
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param datePollOptions Alle Datums-Eintraege des User creator.
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilderAndView initDatePollOptionList(final DatePollBuilderAndView datePollBuilderAndView, final List<DatePollOption> datePollOptions) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        builder.datePollOptionList(datePollOptions);
        return datePollBuilderAndView.updateState(builder);
    }



    /**
     * Handelt es sich um eine oeffentliche oder geschlossene Abstimmung?
     * @param datePollBuilderAndView
     * @param openDatePoll
     * @return
     */
    public DatePollBuilderAndView openOrClosedPoll(final DatePollBuilderAndView datePollBuilderAndView, final boolean openDatePoll) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(openDatePoll);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }

    /**
     * Fuenfte Instanz zur Erstellung der Terminfindung.
     * Angabe ob es sich um ein "single" oder "multiple" Choice Terminfindungs-Objekt handelt.
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param singleChoicePoll Hat der User nur eine Wahlmoeglichkeit (true) oder mehrere (false).
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilderAndView setDatePollChoiceType(final DatePollBuilderAndView datePollBuilderAndView, final boolean singleChoicePoll) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(singleChoicePoll);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }

    /**
     * Fuenfte Instanz zur Erstellung der Terminfindung.
     * Angabe ob es priorisierte Auswahlmoeglichkeiten geben soll.
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param priority Prioritaet an (true) oder aus (false).
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilderAndView setDatePollChoicePriority(final DatePollBuilderAndView datePollBuilderAndView, final boolean priority) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(priority);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }

    /**
     * Sechste Instanz zur Erstellung der Terminfindung.
     * Angabe ob die Terminfindung anonym (true) oder öffentlich (false) stattfinden soll.
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param isAnonymous Anonym (true) oder öffentlich (false).
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilderAndView setDatePollVisibility(final DatePollBuilderAndView datePollBuilderAndView, final boolean isAnonymous) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(isAnonymous);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }



    /**
     * Die Methode gibt das fertige DatePoll Objekt zurueck.
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @return Das fertige DatePoll Objekt.
     */
    public DatePoll buildDatePoll(final DatePollBuilderAndView datePollBuilderAndView) {
        return datePollBuilderAndView.getBuilder().build();
    }
}
