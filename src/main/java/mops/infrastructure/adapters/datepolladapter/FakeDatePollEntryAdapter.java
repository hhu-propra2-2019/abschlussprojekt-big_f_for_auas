package mops.infrastructure.adapters.datepolladapter;

import mops.application.services.FakeDatePollInfoService;
import mops.application.services.FakeDatePollVoteService;
import mops.infrastructure.controllers.dtos.DashboardItemDto;
import mops.infrastructure.controllers.dtos.DatePollEntryDto;
import mops.infrastructure.controllers.dtos.DatePollUserEntryOverview;
import mops.infrastructure.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@SuppressWarnings({"PMD.LawOfDemeter", "PMD.DataflowAnomalyAnalysis"})
@Service
public class FakeDatePollEntryAdapter implements DatePollEntryAdapterInterface {



    private final transient FakeDatePollVoteService voteService;
    private final transient FakeDatePollInfoService infoService;

    @Autowired
    public FakeDatePollEntryAdapter(FakeDatePollVoteService voteService, FakeDatePollInfoService infoService) {
        this.voteService = voteService;
        this.infoService = infoService;
    }

    /**
     * befüllt eine Overview von den abgegebenen Stimmen eines Users zu einem DatePoll.
     * @param link
     * @param user
     * @return Overview
     */
    @Override
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
        result.setAllEntries(infoService.getEntries(link)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toSet()));

        final Set<DatePollEntryDto> entries = showAllEntries(link);

        final TreeSet<DatePollEntryDto> treeEntries = new TreeSet<>();
        for (final DatePollEntryDto entry: entries) {
            treeEntries.add(entry);
        }
        result.setAllEntries(treeEntries);

        return result;
    }


    /**
     *  gibt alle Entries raus von einem Poll.
     * @param link
     * @return Set<DatePollEntryDto>
     */
    @Override
    public Set<DatePollEntryDto> showAllEntries(PollLink link) {
        return infoService.getEntries(link).stream().map(this::toDTO).collect(Collectors.toSet());
    }

    /**
     * konvertiert entry zu entryDto.
     * @param entry
     * @return DatePollEntryDto
     */
    private DatePollEntryDto toDTO(DatePollEntry entry) {
        return new DatePollEntryDto(
                entry.getSuggestedPeriod().getStartDate(),
                entry.getSuggestedPeriod().getEndDate()
        );
    }

    /** ruft die getAllListItemDtos methode vom infoservice auf.
     *
     * @param userId
     * @return Set<DashboardItemDto>
     */
    @Override
    public Set<DashboardItemDto> getAllListItemDtos(UserId userId) {
        return infoService.getAllListItemDtos(userId);
    }

    /** noch ungenutzt.
     *
     * @param link
     * @return Set<FormattedDatePollEntryDto>
     */
    @Override
    public Set<FormattedDatePollEntryDto> getAllEntriesFormatted(PollLink link) {
        return null;
    }

    private DatePollEntry toEntryDomain(DatePollEntryDto entryDto) {
        return new DatePollEntry(entryDto.getTimespan());
    }

    /** parsed die Overview in domain models und ruft den service auf.
     *
     * @param link
     * @param user
     * @param overview
     */
    @Override
    public void vote(PollLink link, UserId user, DatePollUserEntryOverview overview) {
        voteService.vote(link, user,
                overview.getVotedYes().stream()
                        .map(this::toEntryDomain)
                        .collect(Collectors.toSet()),
                overview.getVotedMaybe().stream()
                        .map(this::toEntryDomain)
                        .collect(Collectors.toSet()));
    }

}
