package mops.application.services;

import mops.controllers.dtos.DashboardListItemDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import mops.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PollInfoService {

    private DatePollRepository datePollRepository;
    private UserRepository userRepository;

    /**
     * ...
     * @param userId ...
     * @return ...
     */
    public List<DashboardListItemDto> dashboardService(UserId userId) {
        List<DashboardListItemDto> dashboardListItemDtos = new LinkedList<>();
        List<DatePoll> datePolls = datePollRepository.getDatePollsByUserId(userId);
        for (DatePoll datePoll: datePolls
             ) {
            DashboardListItemDto dashboardListItemDto = new DashboardListItemDto();
            dashboardListItemDto.setTitle(datePoll.getDatePollMetaInf().getTitle());
            dashboardListItemDto.setEndDate(datePoll.getDatePollMetaInf().getDatePollLifeCycle().getEndDate());
            dashboardListItemDto.setStatus(datePoll.getUserStatus(userRepository.getUserById(userId)));
            dashboardListItemDto.setDatePollIdentifier(datePoll.getDatePollLink().getDatePollIdentifier());
            dashboardListItemDtos.add(dashboardListItemDto);
        }
        return dashboardListItemDtos;
    }
}
