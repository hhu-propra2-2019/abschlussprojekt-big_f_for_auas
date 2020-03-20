package mops.adapters.datepolladapter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.adapters.datepolladapter.dtos.ConfigDto;
import mops.adapters.datepolladapter.dtos.MetaInfDto;
import mops.adapters.datepolladapter.dtos.OptionDto;
import mops.adapters.datepolladapter.dtos.OptionsDto;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.PollFields;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;

@Service
@PropertySource(value = "classpath:errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class DatePollAdapter {

    private final transient ConversionService conversionService;
    private final transient Environment errorEnvironment;

    @Autowired
    public DatePollAdapter(ConversionService conversionService, Environment env) {
        this.conversionService = conversionService;
        this.errorEnvironment = env;
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
        final Validation validation = validateMetaInf(metaInfDto)
                .removeErrors(PollFields.TIMESPAN);
        mapErrors(validation.getErrorMessages(), context);
        return validation.hasNoErrors();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateSecondStep(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = validateMetaInf(metaInfDto);
        mapErrors(validation.getErrorMessages(), context);
        return validation.hasNoErrors();
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    private Validation validateMetaInf(MetaInfDto metaInfDto) {
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
        mapErrors(validation.getErrorMessages(), context);
        return validation.hasNoErrors();
    }

    public boolean validate(OptionsDto optionsDto, MessageContext context) {
        // Die Optionen werden in addOption() validiert, hier muss erst validiert werden, wenn es noch zusätzliche
        // Felder in OptionsDto gibt
        return true;
    }

    public boolean addOption(OptionsDto optionsDto,
                             String addDate,
                             String addStartTime,
                             String addEndTime,
                             MessageContext context) {
        // Testweise versuchen, das Datum und die Zeiten zu parsen
        final boolean isvalid = validate(new OptionDto(addDate, addStartTime, addEndTime), context);
        if (!isvalid) {
            return false;
        }
        optionsDto.getOptions().add(new OptionDto(addDate, addStartTime, addEndTime));
        return true;
    }

    public boolean deleteOption(OptionsDto optionsDto,
                                String deleteDate,
                                String deleteStartTime,
                                String deleteEndTime) {
        optionsDto.getOptions().remove(new OptionDto(deleteDate, deleteStartTime, deleteEndTime));
        return true;
    }

    private boolean validate(OptionDto optionDto, MessageContext context) {
        try {
            LocalDate.parse(optionDto.getDate());
        } catch (DateTimeParseException e) {
            addMessage("DATE_POLL_DATE_NOT_PARSEABLE", context);
            return false;
        }
        try {
            LocalTime.parse(optionDto.getStartTime());
            LocalTime.parse(optionDto.getEndTime());
        } catch (DateTimeParseException e) {
            addMessage("DATE_POLL_TIME_NOT_PARSEABLE", context);
            return false;
        }
        return true;
    }

    private void mapErrors(EnumSet<FieldErrorNames> errors, MessageContext context) {
        errors.forEach(error -> addMessage(error.toString(), context));
    }

    private void addMessage(String code, MessageContext context) {
        context.addMessage(new MessageBuilder()
                .error()
                .code(code)
                .source(errorEnvironment.getProperty(code, "defaulterrors"))
                .build());
    }
}
