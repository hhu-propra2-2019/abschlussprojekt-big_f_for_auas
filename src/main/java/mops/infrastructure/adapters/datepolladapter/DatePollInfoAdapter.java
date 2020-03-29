package mops.infrastructure.adapters.datepolladapter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import mops.application.services.PollInfoService;
import mops.domain.models.datepoll.DatePollConfig;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.domain.models.user.User;
import mops.domain.repositories.UserRepository;
import mops.infrastructure.controllers.dtos.DashboardItemDto;
import mops.infrastructure.controllers.dtos.DatePollConfigDto;
import mops.infrastructure.controllers.dtos.DatePollMetaInfDto;
import mops.infrastructure.controllers.dtos.DatePollResultDto;
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
    private final transient UserRepository userRepository;

    public DatePollInfoAdapter(PollInfoService infoService, UserRepository userRepository) {
        this.infoService = infoService;
        this.userRepository = userRepository;
    }

    public DatePollMetaInfDto getDatePollMetaInformation(PollLink link, UserId userId) {
        final DatePoll poll = infoService.getDatePollByLink(link);
        return DatePollInfoAdapter.datePollMetaInfToDto(poll.getMetaInf());
    }

    public DatePollConfigDto getDatePollConfig(PollLink link) {
        final DatePoll poll = infoService.getDatePollByLink(link);
        return DatePollInfoAdapter.datePollConfigToDto(poll.getConfig());
    }

    private static DatePollConfigDto datePollConfigToDto(DatePollConfig config) {
        return new DatePollConfigDto(config.isAnonymous(), config.isSingleChoice(), config.isPriorityChoice());
    }

    @SuppressWarnings("PMD.LawOfDemeter") // stream
    public List<DatePollResultDto> getAllDatePollResultDto(PollLink link) {
        final Set<DatePollEntry> entries = infoService.getEntries(link);
        final List<DatePollResultDto> results = entries.stream()
            .map(DatePollInfoAdapter::datePollEntryToResultDto)
            .collect(Collectors.toCollection(ArrayList::new));
        results.sort(DatePollResultDto::compareTo);
        return results;
    }

    @SuppressWarnings("PMD.LawOfDemeter") // stream
    public List<DashboardItemDto> getOwnPollsForDashboard(UserId userId) {
        // TODO: checken, ob User vorhanden
        final User user = userRepository.load(userId).get();
        final Set<DatePoll> datePolls = infoService.getDatePollByCreator(userId);
        final List<DashboardItemDto> items = datePolls.stream()
            .map(datePoll -> DatePollInfoAdapter.datePollToDasboardDto(datePoll, user))
            .collect(Collectors.toCollection(ArrayList::new));
        items.sort(DashboardItemDto::compareTo);
        return items;
    }

    public List<DashboardItemDto> getPollsByOthersForDashboard(UserId userId) {
        // TODO: checken, ob User vorhanden
        final User user = userRepository.load(userId).get();
        final Set<DatePoll> datePolls = infoService.getDatePollByStatusFromUser(userId);
        final List<DashboardItemDto> items = datePolls.stream()
            .map(datePoll -> DatePollInfoAdapter.datePollToDasboardDto(datePoll, user))
            .collect(Collectors.toCollection(ArrayList::new));
        items.sort(DashboardItemDto::compareTo);
        return items;
    }

    private static DatePollResultDto datePollEntryToResultDto(DatePollEntry entry) {
        return new DatePollResultDto(entry.getSuggestedPeriod(),
            entry.getYesVotes(),
            entry.getMaybeVotes());
    }

    private static DatePollMetaInfDto datePollMetaInfToDto(DatePollMetaInf metaInf) {
        return new DatePollMetaInfDto(metaInf.getTitle(), metaInf.getDescription().getDescriptionText(),
            metaInf.getLocation().getLocation(), metaInf.getTimespan().getEndDate());
    }

    private static DashboardItemDto datePollToDasboardDto(DatePoll datePoll, User user) {
        return new DashboardItemDto(
            datePoll.getPollLink().getPollIdentifier(),
            datePoll.getMetaInf().getTitle(),
            datePoll.getMetaInf().getTimespan().getEndDate(),
            datePoll.getRecordAndStatus().getUserStatus(user).getIconName(),
            datePoll.getMetaInf().getTimespan().getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            datePoll.getMetaInf().getTimespan().getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")),
            datePoll.getRecordAndStatus().getLastModified(),
            DashboardItemDto.DATEPOLL_TYPE
        );
    }
}
