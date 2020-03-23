package mops.infrastructure.adapters.webflow.datepoll;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.infrastructure.adapters.webflow.datepoll.dtos.ConfigDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.EntryDto;
import mops.infrastructure.adapters.webflow.datepoll.dtos.MetaInfDto;
import mops.domain.models.PollFields;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.addMessage;
import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.mapErrors;

@Service
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
@SuppressWarnings("PMD.TooManyMethods")
public final class DatePollWebFlowValidationAdapter {

    private final transient ConversionService conversionService;
    private final transient Environment errorEnvironment;

    @Autowired
    public DatePollWebFlowValidationAdapter(ConversionService conversionService, Environment env) {
        this.conversionService = conversionService;
        this.errorEnvironment = env;
    }

    public MetaInfDto initializeMetaInfDto() {
        return new MetaInfDto();
    }

    public ConfigDto initializeConfigDto() {
        return  new ConfigDto();
    }

    public EntriesDto initializeEntriesDto() {
        return new EntriesDto();
    }

    /**
     * Validiert MetaInfDto, nachdem Titel und evtl. Beschreibung und Ort eingegeben wurden.
     *
     * @param metaInfDto ...
     * @param context    im MessageContext können die Fehlermeldungen angehängt werden
     * @return ob die Transition in den nächsten State stattfinden soll oder nicht
     */
    @SuppressWarnings("PMD.LawOfDemeter")//NOPMD
    /*
     * Verletzung wird in Kauf genommen um in Validation die entscheidung zu Kapseln wann eine Validierung erfolgreich
     * war, aber die Validierung selbst kann nur das zu validierende Objekt selbst sinvoll lösen
     */
    public boolean validateFirstStep(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = validate(metaInfDto)
                .removeErrors(PollFields.TIMESPAN);
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateSecondStep(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = validate(metaInfDto);
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    private Validation validate(MetaInfDto metaInfDto) {
        return conversionService.convert(metaInfDto, DatePollMetaInf.class).validate();
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validate(ConfigDto configDto, MessageContext context) {
        final DatePollConfig config = conversionService.convert(configDto, DatePollConfig.class);
        final Validation validation = config.validate();
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean validate(EntriesDto entriesDto, MessageContext context) {
        // Die Optionen werden in addOption() validiert. Hier wird nur validiert,
        // dass minedestens ein Termin zur Auswahl steht
        final boolean isvalid = !entriesDto.getOptions().isEmpty();
        if (!isvalid) {
            addMessage("DATE_POLL_NO_ENTRIES", context, errorEnvironment);
        }
        return isvalid;
    }

    public boolean addOption(EntriesDto entriesDto,
                             String addDate,
                             String addStartTime,
                             String addEndTime,
                             MessageContext context) {
        final boolean isvalid = validate(new EntryDto(addDate, addStartTime, addEndTime), context);
        if (!isvalid) {
            return false;
        }
        entriesDto.getOptions().add(new EntryDto(addDate, addStartTime, addEndTime));
        return true;
    }

    public boolean deleteOption(EntriesDto entriesDto,
                                String deleteDate,
                                String deleteStartTime,
                                String deleteEndTime) {
        entriesDto.getOptions().remove(new EntryDto(deleteDate, deleteStartTime, deleteEndTime));
        return true;
    }

    private boolean validate(EntryDto entryDto, MessageContext context) {
        try {
            LocalDate.parse(entryDto.getDate());
        } catch (DateTimeParseException e) {
            addMessage("DATE_POLL_DATE_NOT_PARSEABLE", context, errorEnvironment);
            return false;
        }
        try {
            LocalTime.parse(entryDto.getStartTime());
            LocalTime.parse(entryDto.getEndTime());
        } catch (DateTimeParseException e) {
            addMessage("DATE_POLL_TIME_NOT_PARSEABLE", context, errorEnvironment);
            return false;
        }
        return true;
    }
}
