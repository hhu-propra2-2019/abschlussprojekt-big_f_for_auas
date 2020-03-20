package mops.application.services;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.repositories.DatePollRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@SuppressWarnings("checkstyle:DesignForExtension")
@Service
public class PollInfoService {


    private final transient DatePollRepository datePollRepository;

    public PollInfoService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

    public Set<DatePollEntry> getEntries(DatePollLink link) {
        DatePoll datePoll = datePollRepository.load(link).orElseThrow();
        return datePoll.getDatePollEntries();
    }

    /*
    private UserRepository userRepository;
     */

    /*
     * Gibt die Dtos für jeweils einen Eintrag im Dashboard zurück.
     * @param userId für diesen User
     * @return ...
     */
    /*@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<DashboardListItemDto> dashboardService(UserId userId) {
        final List<DashboardListItemDto> dashboardListItemDtos = new LinkedList<>();
        final List<DatePoll> datePolls = datePollRepository.getDatePollsByUserId(userId);
        for (final DatePoll datePoll: datePolls
             ) {
            final DashboardListItemDto dashboardListItemDto = new DashboardListItemDto();
            dashboardListItemDto.setTitle(datePoll.getDatePollMetaInf().getTitle());
            dashboardListItemDto.setEndDate(datePoll.getDatePollMetaInf().getDatePollLifeCycle().getEndDate());
            dashboardListItemDto.setStatus(datePoll.getUserStatus(userRepository.getUserById(userId)).getIconName());
            dashboardListItemDto.setDatePollIdentifier(datePoll.getDatePollLink().getDatePollIdentifier());
            dashboardListItemDtos.add(dashboardListItemDto);
        }
        return dashboardListItemDtos;
    }*/

    // TODO: Hier evtl. noch check einfügen, ob User berechtigt ist, diese Abstimmung zu sehen?
    // oder woanders? noch wird es jedenfalls ->nicht<- geprüft!!

    /*
     * Gibt das DTO für die Detailansicht einer Terminabstimmung zurück.
     * @param userId für diesen User
     * @param datePollLink die Referenz für die Terminabstimmung
     * @return ...
     */
    /*public DatePollDto datePollViewService(UserId userId, DatePollLink datePollLink) {
        final DatePoll datePoll = datePollRepository.getDatePollByLink(datePollLink);
        final DatePollDto datePollDto = new DatePollDto();
        datePollDto.setDescription(datePoll.getDatePollMetaInf().getDatePollDescription().getDescription());
        datePollDto.setEndDate(datePoll.getDatePollMetaInf().getDatePollLifeCycle().getEndDate());
        datePollDto.setLocation(datePoll.getDatePollMetaInf().getDatePollLocation().getLocation());
        datePollDto.setPollStatus(datePoll.getUserStatus(userRepository.getUserById(userId)).getIconName());
        datePollDto.setTitle(datePoll.getDatePollMetaInf().getTitle());

        return datePollDto;
    }*/


}
