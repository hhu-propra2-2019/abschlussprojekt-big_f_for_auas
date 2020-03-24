package mops.adapters.datepolladapter;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import mops.application.services.PollInfoService;
import mops.controllers.dtos.DatePollMetaInfDto;
import mops.controllers.dtos.DatePollResultDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:DesignForExtension"})
/* keine Zeit sich um bessere Kapslung zu kümmern */
public class DatePollInfoAdapter {

    private final transient PollInfoService infoService;

    public DatePollInfoAdapter(PollInfoService infoService) {
        this.infoService = infoService;
    }

    public DatePollMetaInfDto showMetaInformation(PollLink link, UserId userId) {
        final DatePoll poll = infoService.datePollViewService(link);
        final DatePollMetaInfDto dto = new DatePollMetaInfDto();
        dto.setTitle(poll.getMetaInf().getTitle());
        dto.setDescription(poll.getMetaInf().getDescription().getDescriptionText());
        dto.setLocation(poll.getMetaInf().getLocation().getLocation());
        dto.setPollStatus(poll.getUserStatus(userId).getIconName());
        dto.setEndDate(poll.getMetaInf().getTimespan().getEndDate());
        return dto;
    }

    @SuppressWarnings("PMD.LawOfDemeter") // stream
    public SortedSet<DatePollResultDto> getAllDatePollResultDto(PollLink link) {
        final Set<DatePollEntry> entries = infoService.getEntries(link);
        return entries.stream()
            .map(DatePollResultDto::new)
            .collect(Collectors.toCollection(TreeSet::new));
    }
}
