package mops.adapters.datepolladapter;

import mops.application.services.PollInfoService;
import mops.controllers.dtos.DatePollDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.PollLink;
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

    public DatePollDto showMetaInformations(PollLink link, UserId userId) {
        final DatePoll poll = infoService.datePollViewService(link);
        final DatePollDto dto = new DatePollDto();
        dto.setTitle(poll.getMetaInf().getTitle());
        dto.setDescription(poll.getMetaInf().getDescription().getDescriptionText());
        dto.setLocation(poll.getMetaInf().getLocation().getLocation());
        dto.setPollStatus(poll.getUserStatus(userId).getIconName());
        dto.setEndDate(poll.getMetaInf().getTimespan().getEndDate());
        return dto;
    }
}
