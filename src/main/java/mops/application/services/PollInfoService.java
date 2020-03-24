package mops.application.services;

import mops.controllers.dtos.DashboardItemDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@SuppressWarnings({"checkstyle:DesignForExtension", "PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.DataflowAnomalyAnalysis"})
@Service
public class PollInfoService {


    private final transient DatePollRepository datePollRepository;

    public PollInfoService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    public Set<DatePollEntry> getEntries(PollLink link) {
        final DatePoll datePoll = datePollRepository.load(link).orElseThrow();
        return datePoll.getEntries();
    }

    public Set<DashboardItemDto> getAllListItemDtos(UserId userId) {
        final TreeSet<DashboardItemDto> dashboardItemDtos = new TreeSet<>();
        final Set<DatePoll> datePolls = datePollRepository.getDatePollsByUserId(userId);
        for (final DatePoll datePoll: datePolls) {
            dashboardItemDtos.add(new DashboardItemDto(datePoll, userId));
        }
        return dashboardItemDtos;
    }

    // TODO: Hier evtl. noch check einfügen, ob User berechtigt ist, diese Abstimmung zu sehen?
    // oder woanders? noch wird es jedenfalls ->nicht<- geprüft!!

    /**
     * Gibt das DTO für die Detailansicht einer Terminabstimmung zurück.
     * @param datePollLink die Referenz für die Terminabstimmung
     * @return ...
     */
    @SuppressWarnings("PMD.LawOfDemeter") // stream
    public DatePoll datePollViewService(PollLink datePollLink) {
        return datePollRepository.load(datePollLink).orElseThrow();
    }

}
