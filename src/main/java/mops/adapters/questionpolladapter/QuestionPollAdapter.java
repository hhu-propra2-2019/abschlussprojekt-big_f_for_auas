package mops.adapters.questionpolladapter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import mops.adapters.questionpolladapter.dtos.HeaderDto;
import mops.adapters.questionpolladapter.dtos.TimespanDto;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.Timespan;
import mops.domain.models.Validation;
import mops.domain.models.questionpoll.QuestionPollHeader;
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
public final class QuestionPollAdapter {

    private final transient ConversionService conversionService;
    private final transient Environment errorEnvironment;

    @Autowired
    public QuestionPollAdapter(ConversionService conversionService, Environment env) {
        this.conversionService = conversionService;
        this.errorEnvironment = env;
    }

    @SuppressFBWarnings(
            value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
            justification = "Der eingesetzte Converter kann niemals eine null refrence zurückgeben, "
                    + "auch wenn das Interface es erlaubt")
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public boolean validateHeader(HeaderDto headerDto, MessageContext messageContext) {
        final QuestionPollHeader header = conversionService.convert(headerDto, QuestionPollHeader.class);
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
