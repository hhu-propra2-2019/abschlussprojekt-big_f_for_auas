package mops.application.services;

import lombok.NoArgsConstructor;
import mops.domain.repositories.DatePollRepository;
import mops.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor // PMD zuliebe
public class PollInfoService {

    private DatePollRepository datePollRepository;
    private UserRepository userRepository;

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
