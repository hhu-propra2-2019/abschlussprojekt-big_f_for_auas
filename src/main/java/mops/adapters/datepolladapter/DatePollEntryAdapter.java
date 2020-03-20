package mops.adapters.datepolladapter;

import mops.application.services.DatePollVoteService;
import mops.application.services.PollInfoService;
import mops.controllers.dtos.DatePollEntryDto;
import mops.controllers.dtos.DatePollUserEntryOverview;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:DesignForExtension"})
/* keine Zeit sich um bessere Kapslung zu kümmern */
@Service
public class DatePollEntryAdapter {

    private final transient DatePollVoteService voteService;
    private final transient PollInfoService infoService;

    @Autowired
    public DatePollEntryAdapter(DatePollVoteService voteService, PollInfoService infoService) {
        this.voteService = voteService;
        this.infoService = infoService;
    }


    public DatePollUserEntryOverview showUserEntryOverview(DatePollLink link, UserId user) {
        final DatePollUserEntryOverview result = new DatePollUserEntryOverview();
        final DatePollBallot ballot = voteService.showUserVotes(user, link);
        result.setYes(
                ballot.getSelectedEntriesYes()
                        .stream()
                        .map(this::toDTO)
                        .collect(Collectors.toSet())
        );
        result.setNo(
                ballot.getSelectedEntriesMaybe()
                        .stream()
                        .map(this::toDTO)
                        .collect(Collectors.toSet())
        );
        return result;
    }

    public Set<DatePollEntryDto> showAllEntries(DatePollLink link) {
        final Set<DatePollEntry> enties = infoService.getEntries(link);
        return enties.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    private DatePollEntryDto toDTO(DatePollEntry entry) {
        return new DatePollEntryDto(
                entry.getSuggestedPeriod().getStartDate(),
                entry.getSuggestedPeriod().getEndDate()
        );
    }
}
