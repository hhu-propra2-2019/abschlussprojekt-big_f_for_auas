package mops.applicationServices;

import mops.domain.models.*;
import mops.domain.models.DatePoll.DatePollBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dieser Service dient zur Erstellung eines(!) Terminfindungs-Objekt (DatePoll).
 * Der Service erhaelt die Daten zur Termindung durch den User (creator).
 * Das DatePoll Objekt enthaelt ein DatePollConfig Objekt, welches nach bestimmten Konfigurationsschritten
 * geupdated wird.
 * Die Terminfingung wird dann durch einen Lombok-Builder erzeugt, der sich in der Klasse
 * DatePollBuilderAndView befindet. Dieser durchlaueft mehrere Methoden.
 *
 * @author Niclas, Tim, Lukas
 */
@Service
public class DatePollCreateService {
    /**
     *Initiale Methode zur Erstellung der Terminfindung.
     * @param creator User der die Terminfindung erstellt.
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
     */
    public DatePollBuilderAndView initializeDatePoll(final User creator) {
        DatePollConfig config = new DatePollConfig();
        DatePollBuilder datePollBuilder = DatePoll.builder();
        DatePollID datePollID = new DatePollID();
        datePollBuilder.datePollID(datePollID);
        datePollBuilder.creator(creator);
        datePollBuilder.datePollConfig(config);
        return new DatePollBuilderAndView(datePollBuilder, config);
    }

    /**
     * Hinzufuegen der Meta-Informationen: Titel, Ort, Beschreibung.
     *
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt aus initializeDatePoll.
     * @param datePollMetaInf Meta-Informationen: Titel, Ort, Beschreibund.
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
     */
    public DatePollBuilderAndView addDatePollMetaInf(final DatePollBuilderAndView datePollBuilderAndView, final DatePollMetaInf datePollMetaInf) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        builder.datePollMetaInf(new DatePollMetaInf()); //Title Location Description
        return datePollBuilderAndView.updateState(builder);
    }



    /**
     * Fuegt die Liste der Datum-Eintraege an denen der Termin stattfinden kann hinzu.
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt nach addDatePollMetaInf.
     * @param datePollOptions Alle Datums-Eintraege des User creator.
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
     */
    public DatePollBuilderAndView initDatePollOptionList(final DatePollBuilderAndView datePollBuilderAndView, final List<DatePollOption> datePollOptions) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        builder.datePollOptionList(datePollOptions);
        return datePollBuilderAndView.updateState(builder);
    }



    /**
     * Handelt es sich um eine oeffentliche oder geschlossene Abstimmung?
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt nach initDatePollOptionList.
     * @param openDatePoll oeffentlich (true) oder geschlossen (false).
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
     */
    public DatePollBuilderAndView openOrClosedPoll(final DatePollBuilderAndView datePollBuilderAndView, final boolean openDatePoll) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(openDatePoll);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }

    /**
     * Angabe ob es sich um ein "single" oder "multiple" Choice Terminfindungs-Objekt handelt.
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt nach openOrClosedPoll
     * @param singleChoicePoll Hat der User nur eine Wahlmoeglichkeit (true) oder mehrere (false).
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
     */
    public DatePollBuilderAndView setDatePollChoiceType(final DatePollBuilderAndView datePollBuilderAndView, final boolean singleChoicePoll) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(singleChoicePoll);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }

    /**
     * Angabe ob es priorisierte Auswahlmoeglichkeiten geben soll.
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt nach setDatePollChoiceType
     * @param priority Prioritaet an (true) oder aus (false).
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
     */
    public DatePollBuilderAndView setDatePollChoicePriority(final DatePollBuilderAndView datePollBuilderAndView, final boolean priority) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getUnfinishedConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoiceDatePoll(priority);
        builder.datePollConfig(newConfig);
        return datePollBuilderAndView.updateState(builder);
    }

    /**
     * Angabe ob die Terminfindung anonym (true) oder öffentlich (false) stattfinden soll.
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt nach setDatePollChoicePriority
     * @param isAnonymous Anonym (true) oder öffentlich (false).
     * @return DatePollBuilderAndView Objekt (enthaelt: DatePollBuilder, DatePoll, DatePollConfig).
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
     * @param datePollBuilderAndView DatePollBuilderAndView Objekt nach setDatePollVisibility
     * @return Das fertige DatePoll Objekt.
     */
    public DatePoll buildDatePoll(final DatePollBuilderAndView datePollBuilderAndView) {
        return datePollBuilderAndView.getBuilder().build();
    }
}
