package mops.adapters.datepolladapter;

import mops.application.services.FakeDatePollInfoService;
import mops.application.services.FakeDatePollVoteService;
import mops.controllers.dtos.DatePollEntryDto;
import mops.controllers.dtos.DatePollUserEntryOverview;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.user.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

//CHECKSTYLE:OFF
@SuppressWarnings({"PMD.LawOfDemeter"})
@Service
public class FakeDatePollEntryAdapter {



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
    public DatePollUserEntryOverview showUserEntryOverview(String link, UserId user) {
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

        final Set<DatePollEntryDto> entries = infoService
                .getEntries(link)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
        final TreeSet<DatePollEntryDto> treeEntries = new TreeSet<>();
        for (final DatePollEntryDto entry: entries) {
            treeEntries.add(entry);
        }
        result.setAllEntries(treeEntries);

        return result;
    }

    private DatePollEntryDto toDTO(DatePollEntry entry) {
        return new DatePollEntryDto(
                entry.getSuggestedPeriod().getStartDate(),
                entry.getSuggestedPeriod().getEndDate()
        );
    }

}
