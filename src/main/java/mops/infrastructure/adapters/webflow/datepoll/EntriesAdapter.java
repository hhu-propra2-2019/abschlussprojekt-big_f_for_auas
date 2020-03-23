package mops.infrastructure.adapters.webflow.datepoll;

import mops.infrastructure.adapters.webflow.WebFlowAdapter;
import mops.infrastructure.adapters.webflow.datepoll.builderdtos.Entries;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntriesDto;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntryDto;
import org.springframework.binding.message.MessageContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static mops.infrastructure.adapters.webflow.ErrorMessageHelper.addMessage;

@Service
@PropertySource(value = "classpath:flows/errormappings/datepollmappings.properties", encoding = "UTF-8")
public final class EntriesAdapter implements WebFlowAdapter<EntriesDto, Entries> {

    private final transient Environment errorEnvironment;
    private final transient EntryAdapter entryAdapter;

    public EntriesAdapter(Environment errorEnvironment, EntryAdapter entryAdapter) {
        this.errorEnvironment = errorEnvironment;
        this.entryAdapter = entryAdapter;
    }

    public boolean addOption(EntriesDto entriesDto,
                             String addDate,
                             String addStartTime,
                             String addEndTime,
                             MessageContext context) {
        final boolean isvalid = entryAdapter.validateDto(new EntryDto(addDate, addStartTime, addEndTime), context);
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

    @Override
    public EntriesDto initializeDto() {
        return new EntriesDto();
    }

    @Override
    @SuppressWarnings("PMD.LawOfDemeter")
    public boolean validateDto(EntriesDto entriesDto, MessageContext context) {
        // Die Optionen werden in addOption() validiert. Hier wird nur validiert,
        // dass minedestens ein Termin zur Auswahl steht
        if (entriesDto.getOptions().isEmpty()) {
            addMessage("DATE_POLL_NO_ENTRIES", context, errorEnvironment);
            return false;
        }
        return true;
    }

    @Override
    public Entries build(EntriesDto entriesDto) {
        return new Entries(
                entriesDto.getOptions().stream().map(entryAdapter::build).collect(Collectors.toSet())
        );
    }
}
