package mops.adapters.datepolladapter;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import mops.application.services.PollInfoService;
import mops.controllers.dtos.DatePollResultDto;
import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePollEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatePollResultAdapter {

    private final transient PollInfoService pollInfoService;

    @Autowired
    public DatePollResultAdapter(PollInfoService pollInfoService) {
        this.pollInfoService = pollInfoService;
    }

    /**
     * gibt Result dtos zurück. Absteigend sortiert nach yes votes.
     * @param link Link zum DatePoll.
     * @return treeset.
     */
    @SuppressWarnings("PMD.LawOfDemeter") // stream
    public SortedSet<DatePollResultDto> getAllDatePollResultDto(PollLink link) {
        final Set<DatePollEntry> entries = pollInfoService.getEntries(link);
        return entries.stream()
            .map(DatePollResultDto::new)
            .collect(Collectors.toCollection(TreeSet::new));
    }
}
