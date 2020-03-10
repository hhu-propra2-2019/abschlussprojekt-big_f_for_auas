package mops.application.services;

import mops.domain.repositories.DatePollRepository;
import mops.domain.repositories.GroupRepository;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DatePollPublicationService {
    /**
     * DatePollRepository mit den bisher gespeicherten DatePolls.
     */
    private DatePollRepository datePollRepository;
    /**
     * GroupRepository mit den bisher gespeicherten Groups.
     */
    private GroupRepository groupRepository;

    /**
     *
     * @param datePollBuilderAndView Um an den datePollConfigDto
     * @return DatePoll Objekt.
     */
    public DatePoll publishDatePoll(final DatePollBuilderAndView datePollBuilderAndView) {
        DatePoll created =  datePollBuilderAndView.startBuildingDatePoll();
        datePollRepository.save(created);
        return created;
    }

    /**
     * Gibt die DatePoll zurück, die nun die Information enthält, dass sie für die User einer
     * bestimmten Gruppe bestimmt ist.
     * @param datePollBuilderAndView Builder vom DatePollCreateService
     * @param groupID Id der betreffenden Gruppe
     */
    public void forGroup(final DatePollBuilderAndView datePollBuilderAndView, final GroupId groupID) {
        List<UserId> userList = groupRepository.getUsersFromGroupByGroupId(groupID);
        userList
                .forEach(datePollBuilderAndView::addSingleParticipant);
    }

    /**
     * Gibt die DatePoll zurück, die nun die Information enthält, dass sie für die übergebenen User
     * bestimmt ist.
     * @param datePollBuilderAndView Builder vom DatePollCreateService
     * @param participants Liste der betreffenden User
     */
    public void forCertainUsers(final DatePollBuilderAndView datePollBuilderAndView, final List<UserId> participants) {
        participants
                .forEach(datePollBuilderAndView::addSingleParticipant);
    }
}
