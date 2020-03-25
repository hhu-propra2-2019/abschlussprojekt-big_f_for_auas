package mops.infrastructure.adapters.webflow.questionpolladapter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.Timespan;
import mops.domain.models.Validation;
import mops.domain.models.questionpoll.QuestionPollConfig;
import mops.domain.models.questionpoll.QuestionPollEntry;
import mops.domain.models.questionpoll.QuestionPollMetaInf;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.ConfigDto;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.EntriesDto;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.EntryDto;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.HeaderDto;
import mops.infrastructure.adapters.webflow.questionpolladapter.dtos.TimespanDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;

@Service
@PropertySource(value = "classpath:flows/errormappings/questionpollmappings.properties", encoding = "UTF-8")
public final class QuestionPollAdapter {

    private final transient ConversionService conversionService;
    private final transient Environment errorEnvironment;
    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public QuestionPollAdapter(ConversionService conversionService, Environment env) {
        this.conversionService = conversionService;
        this.errorEnvironment = env;
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", //NOPMD
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, " //NOPMD
                    + "auch wenn das Interface es erlaubt") //NOPMD
    @SuppressWarnings({"PMD.LawOfDemeter"}) //NOPMD
    public boolean validateHeader(HeaderDto headerDto, MessageContext messageContext) {
        final QuestionPollMetaInf header = conversionService.convert(headerDto, QuestionPollMetaInf.class);
        final Validation validation = header.validate();
        mapErrors(validation.getErrorMessages(), messageContext);
        return validation.hasNoErrors();
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateTimespan(TimespanDto timespanDto, MessageContext messageContext) {
        if (!isTimeParsable(timespanDto, messageContext)) {
            return false;
        }
        final Timespan timespan = conversionService.convert(timespanDto, Timespan.class);
        final Validation validation = timespan.validate();
        mapErrors(validation.getErrorMessages(), messageContext);
        return validation.hasNoErrors();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    public TimespanDto initializeTimespanDto() {
        return new TimespanDto(LocalDate.now().toString(),
                LocalTime.now().format(formatter),
                LocalDate.now().plusMonths(1).toString(),
                LocalTime.now().format(formatter));
    }

    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    private boolean isTimeParsable(TimespanDto timespanDto, MessageContext messageContext) {
        boolean parsable = true;
        try {
            LocalDate.parse(timespanDto.getStartDate());
        } catch (DateTimeParseException e) {
            addMessage("QUESTION_POLL_START_DATE_NOT_PARSABLE", messageContext);
            parsable = false;
        }
        try {
            LocalTime.parse(timespanDto.getStartTime());
        } catch (DateTimeParseException e) {
            addMessage("QUESTION_POLL_START_TIME_NOT_PARSABLE", messageContext);
            parsable = false;
        }
        try {
            LocalDate.parse(timespanDto.getEndDate());
        } catch (DateTimeParseException e) {
            addMessage("QUESTION_POLL_END_DATE_NOT_PARSABLE", messageContext);
            parsable = false;
        }
        try {
            LocalTime.parse(timespanDto.getEndTime());
        } catch (DateTimeParseException e) {
            addMessage("QUESTION_POLL_END_TIME_NOT_PARSABLE", messageContext);
            parsable = false;
        }
        return parsable;
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateConfig(ConfigDto configDto, MessageContext messageContext) {
        final QuestionPollConfig config = conversionService.convert(configDto, QuestionPollConfig.class);
        final Validation validation = config.validate();
        mapErrors(validation.getErrorMessages(), messageContext);
        return validation.hasNoErrors();
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateEntries(EntriesDto entriesDto, MessageContext messageContext) {
        final boolean isValid = !entriesDto.getEntries().isEmpty();
        if (!isValid) {
            addMessage("QUESTION_POLL_NO_ENTRIES", messageContext);
        }
        return isValid;
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean addEntry(EntriesDto entriesDto, String entry, MessageContext messageContext) {
        final EntryDto entryDto = new EntryDto(entry);
        final QuestionPollEntry pollEntry = conversionService.convert(entryDto, QuestionPollEntry.class);
        final Validation validation = pollEntry.validate();
        mapErrors(validation.getErrorMessages(), messageContext);
        if (!validation.hasNoErrors()) {
            return false;
        }
        entriesDto.getEntries().add(entryDto);
        return true;
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean deleteEntry(EntriesDto entriesDto, String entry) {
        entriesDto.getEntries().remove(new EntryDto(entry));
        return true;
    }

    private void mapErrors(EnumSet<FieldErrorNames> errors, MessageContext context) {
        errors.forEach(error -> context.addMessage(
                new MessageBuilder()
                        .error()
                        .code(error.toString())
                        .source(errorEnvironment.getProperty(error.toString(), "defaulterrors"))
                        .build()));
    }

    private void addMessage(String code, MessageContext context) {
        context.addMessage(new MessageBuilder()
                .error()
                .code(code)
                .source(errorEnvironment.getProperty(code, "defaulterrors"))
                .build());
    }
}
