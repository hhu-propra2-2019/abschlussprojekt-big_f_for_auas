package mops.infrastructure.adapters.webflow.datepoll;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollEntry;
import mops.infrastructure.adapters.webflow.WebFlowAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.addMessage;

@Component
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class EntryAdapter implements WebFlowAdapter<EntryDto, DatePollEntry> {

    private final transient Environment errorEnvironment;

    @Autowired
    public EntryAdapter(Environment errorEnvironment) {
        this.errorEnvironment = errorEnvironment;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public EntryDto initializeDto() {
        return new EntryDto(LocalDate.now().toString(),
                LocalTime.now().toString(),
                LocalTime.now().plusHours(3).toString());
    }

    @Override
    public boolean validateDto(EntryDto entryDto, MessageContext context) {
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

    @Override
    public DatePollEntry build(EntryDto entryDto) {
        return new DatePollEntry(
                new Timespan(
                        LocalDate.parse(entryDto.getDate()).atTime(LocalTime.parse(entryDto.getStartTime())),
                        LocalDate.parse(entryDto.getDate()).atTime(LocalTime.parse(entryDto.getEndTime()))
                ));
    }
}
