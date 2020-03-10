package mops.application.services;

import mops.controllers.DatePollOptionDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.UserId;
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
     * Initiale Methode zur Erstellung der Terminfindung.
     *
     * @param creator User der die Terminfindung erstellt.
     * @return Lombok-Builder DatePoll Objekt.
     */
    public DatePollBuilderAndView initializeDatePoll(final UserId creator) {
        DatePollBuilder builder = DatePoll.builder();
        DatePollBuilderAndView datePollBuilderAndView = new DatePollBuilderAndView(builder);
        datePollBuilderAndView.setValidation(
                builder.creator(creator).getValidationState()
        );
        return datePollBuilderAndView;
    }

    /**
     * Zweite Instanz zur Erstellung der Terminfindung.
     * Hinzufuegen der Meta-Informationen: Titel, Ort, Beschreibung.
     *
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param datePollMetaInf        Ein Objekt welches die Meta-Informationen enthaelt.
     */
    public void addDatePollMetaInf(
            final DatePollBuilderAndView datePollBuilderAndView, final DatePollMetaInf datePollMetaInf) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        datePollBuilderAndView.setValidation(
                builder.datePollMetaInf(datePollMetaInf).getValidationState()
        );
        datePollBuilderAndView.setMetaInf(datePollMetaInf);
    }

    /**
     * Vierte Instanz zur Erstellung der Terminfindung.
     * Liste der Datum-Eintraege an denen der Termin stattfinden kann.
     *
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param datePollOptionDtos     Alle Datums-Eintraege des User creator.
     */
    public void initDatePollOptionList(
            final DatePollBuilderAndView datePollBuilderAndView, final List<DatePollOptionDto> datePollOptionDtos) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        datePollBuilderAndView.setValidation(
                builder.datePollOptions(datePollOptionDtos).getValidationState()
        );
        datePollBuilderAndView.setDatePollOptionDtos(datePollOptionDtos);
    }

    /**
     * Handelt es sich um eine oeffentliche oder geschlossene Abstimmung?
     *
     * @param datePollBuilderAndView
     * @param openDatePoll
     */
    public void openOrClosedPoll(final DatePollBuilderAndView datePollBuilderAndView, final boolean openDatePoll) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getConfig();
        DatePollConfig newConfig = oldConfig.withOpenForOwnEntries(openDatePoll);
        datePollBuilderAndView.setValidation(
                builder.datePollConfig(newConfig).getValidationState()
        );
        datePollBuilderAndView.setConfig(newConfig);
    }

    /**
     * Fuenfte Instanz zur Erstellung der Terminfindung.
     * Angabe ob es sich um ein "single" oder "multiple" Choice Terminfindungs-Objekt handelt.
     *
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param singleChoicePoll       Hat der User nur eine Wahlmoeglichkeit (true) oder mehrere (false).
     */
    public void setDatePollChoiceType(
            final DatePollBuilderAndView datePollBuilderAndView, final boolean singleChoicePoll) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getConfig();
        DatePollConfig newConfig = oldConfig.withSingleChoice(singleChoicePoll);
        datePollBuilderAndView.setValidation(
                builder.datePollConfig(newConfig).getValidationState()
        );
        datePollBuilderAndView.setConfig(newConfig);
    }

    /**
     * Fuenfte Instanz zur Erstellung der Terminfindung.
     * Angabe ob es priorisierte Auswahlmoeglichkeiten geben soll.
     *
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param priority               Prioritaet an (true) oder aus (false).
     */
    public void setDatePollChoicePriority(final DatePollBuilderAndView datePollBuilderAndView, final boolean priority) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getConfig();
        DatePollConfig newConfig = oldConfig.withPriorityChoice(priority);
        datePollBuilderAndView.setValidation(
                builder.datePollConfig(newConfig).getValidationState()
        );
        datePollBuilderAndView.setConfig(newConfig);
    }

    /**
     * Sechste Instanz zur Erstellung der Terminfindung.
     * Angabe ob die Terminfindung anonym (true) oder öffentlich (false) stattfinden soll.
     *
     * @param datePollBuilderAndView Das Lombok-Builder Objekt aus initializeDatePoll.
     * @param anonymous              Anonym (true) oder öffentlich (false).
     */
    public void setDatePollVisibility(final DatePollBuilderAndView datePollBuilderAndView, final boolean anonymous) {
        DatePollBuilder builder = datePollBuilderAndView.getBuilder();
        DatePollConfig oldConfig = datePollBuilderAndView.getConfig();
        DatePollConfig newConfig = oldConfig.withAnonymous(anonymous);
        datePollBuilderAndView.setValidation(
                builder.datePollConfig(newConfig).getValidationState()
        );
        datePollBuilderAndView.setConfig(newConfig);
    }
}
