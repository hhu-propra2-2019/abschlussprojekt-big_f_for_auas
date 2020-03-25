package mops.infrastructure.adapters.datepolladapter;

import mops.application.services.DatePollVoteService;
import mops.application.services.PollInfoService;
import mops.infrastructure.controllers.dtos.DashboardItemDto;
import mops.infrastructure.controllers.dtos.DatePollEntryDto;
import mops.infrastructure.controllers.dtos.DatePollUserEntryOverview;
import mops.infrastructure.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.PollLink;
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


    public DatePollUserEntryOverview showUserEntryOverview(PollLink link, UserId user) {
        final DatePollUserEntryOverview result = new DatePollUserEntryOverview();
        final DatePollBallot ballot = voteService.showUserVotes(user, link);
        result.setVotedYes(
                ballot.getSelectedEntriesYes()
                        .stream()
                        .map(this::toDTO)
                        .collect(Collectors.toSet())
        );
        result.setVotedMaybe(
                ballot.getSelectedEntriesMaybe()
                        .stream()
                        .map(this::toDTO)
                        .collect(Collectors.toSet())
        );
        return result;
    }

    public Set<DatePollEntryDto> showAllEntries(PollLink link) {
        final Set<DatePollEntry> entries = infoService.getEntries(link);
        return entries.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

    public Set<FormattedDatePollEntryDto> getAllEntriesFormatted(PollLink link) {
        final Set<DatePollEntry> entries = infoService.getEntries(link);
        return entries.stream()
                .map(this::toFormattedDTO)
                .collect(Collectors.toSet());
    }

    private DatePollEntryDto toDTO(DatePollEntry entry) {
        return new DatePollEntryDto(
                entry.getSuggestedPeriod().getStartDate(),
                entry.getSuggestedPeriod().getEndDate()
        );
    }

    private FormattedDatePollEntryDto toFormattedDTO(DatePollEntry entry) {
        final String formatted = entry.getSuggestedPeriod().toString();
        return new FormattedDatePollEntryDto(formatted);
    }


    public Set<DashboardItemDto> getAllListItemDtos(UserId userId) {
        return null;
    }
}
