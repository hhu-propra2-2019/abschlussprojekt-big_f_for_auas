package mops.infrastructure.adapters.webflow.datepoll;

import mops.domain.models.PollFields;
import mops.domain.models.Timespan;
import mops.domain.models.Validation;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.infrastructure.adapters.webflow.WebFlowAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.mapErrors;

@Service
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class MetaInfAdapter implements WebFlowAdapter<MetaInfDto, DatePollMetaInf> {

    private final transient Environment errorEnvironment;

    private final transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    public MetaInfAdapter(Environment errorEnvironment) {
        this.errorEnvironment = errorEnvironment;
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis", "PMD.EmptyCatchBlock"}) // NOPMD
    public DatePollMetaInf convert(MetaInfDto dto) {
        if (dto.getTitle() == null) {
            dto.setTitle("");
        }
        if (dto.getDescription() == null) {
            dto.setDescription("");
        }
        if (dto.getLocation() == null) {
            dto.setLocation("");
        }
        Timespan timespan = new Timespan(null, null);
        try {
            timespan = new Timespan(
                    LocalDate.parse(dto.getStartDate()).atTime(LocalTime.parse(dto.getStartTime())), null);
        } catch (DateTimeParseException | NullPointerException e) {
        }
        try {
            timespan = new Timespan(timespan.getStartDate(),
                    LocalDate.parse(dto.getEndDate()).atTime(LocalTime.parse(dto.getEndTime())));
        } catch (DateTimeParseException | NullPointerException e) {
        }
        return new DatePollMetaInf(dto.getTitle(), dto.getDescription(), dto.getLocation(), timespan);
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    public MetaInfDto initializeDto() {
        return new MetaInfDto("", "", "",
                LocalDate.now().toString(),
                LocalTime.now().format(formatter),
                LocalDate.now().plusMonths(1).toString(),
                LocalTime.now().format(formatter));
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean validateFirstStep(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = convert(metaInfDto).validate().removeErrors(PollFields.TIMESPAN);
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @Override
    @SuppressWarnings({"PMD.LawOfDemeter"})
     public boolean validateDto(MetaInfDto metaInfDto, MessageContext context) {
        final Validation validation = convert(metaInfDto).validate();
        mapErrors(validation.getErrorMessages(), context, errorEnvironment);
        return validation.hasNoErrors();
    }

    @Override
    public DatePollMetaInf build(MetaInfDto metaInfDto) {
        return convert(metaInfDto);
    }
}
