package mops.adapters.datepolladapter;

import mops.application.services.FakeDatePollInfoService;
import mops.application.services.FakeDatePollVoteService;
import mops.controllers.dtos.DashboardItemDto;
import mops.controllers.dtos.DatePollEntryDto;
import mops.controllers.dtos.DatePollUserEntryOverview;
import mops.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.PollLink;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
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


    @Override
    public Set<DatePollEntryDto> showAllEntries(PollLink link) {
        final Set<DatePollEntry> entries = new HashSet<>();

        final LocalDateTime time1 = LocalDateTime.of(2020, 03, 22, 16, 15, 27);
        final LocalDateTime time2 = LocalDateTime.of(2021, 04, 29, 16, 15, 27);
        final Timespan timespan = new Timespan(time1, time2);


        final DatePollEntry entry1 = new DatePollEntry(timespan);
        final DatePollEntry entry2 = new DatePollEntry(new Timespan(time1.minusMonths(32)
                .minusHours(6).plusWeeks(3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry3 = new DatePollEntry(new Timespan(time1.minusMonths(1)
                .minusHours(12).plusWeeks(-3), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry4 = new DatePollEntry(new Timespan(time1
                .minusMonths(9).minusHours(4).plusWeeks(15), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry5 = new DatePollEntry(new Timespan(time1
                .minusMonths(5).minusHours(7).plusWeeks(13), time2.plusMonths(29).minusWeeks(23).plusHours(3)));
        final DatePollEntry entry6 = new DatePollEntry(new Timespan(time1.plusYears(100), time2.plusYears(100)));

        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);
        entries.add(entry5);
        entries.add(entry6);

        return entries.stream().map(this::toDTO) .collect(Collectors.toSet());
    }


    private DatePollEntryDto toDTO(DatePollEntry entry) {
        return new DatePollEntryDto(
                entry.getSuggestedPeriod().getStartDate(),
                entry.getSuggestedPeriod().getEndDate()
        );
    }

    @Override
    public Set<DashboardItemDto> getAllListItemDtos(UserId userId) {
        return infoService.getAllListItemDtos(userId);
    }

    @Override
    public Set<FormattedDatePollEntryDto> getAllEntriesFormatted(PollLink link) {
        return null;
    }
}
