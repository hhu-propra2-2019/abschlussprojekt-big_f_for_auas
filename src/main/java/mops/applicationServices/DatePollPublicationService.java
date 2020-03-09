package mops.applicationServices;

import mops.database.DatePollRepository;
import mops.database.GroupRepository;
import mops.domain.models.DatePoll;
import mops.domain.models.DatePollID;
import mops.domain.models.GroupId;
import mops.domain.models.User;
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
     * Gibt einen DatePoll zurück, der nun die Information enthält mit einem
     * öffentlichen Link veröffentlicht zu werden.
     * @param datePollID Id der betreffenden DatePoll
     * @return DatePoll
     */
    public DatePoll publicationByLink(final DatePollID datePollID) {
        DatePoll datePoll = datePollRepository.getDatePollById(datePollID);
        datePoll.setPublicationTypeToPublic(true);
        return datePoll;
    }

    /**
     * Gibt die DatePoll zurück, die nun die Information enthält, dass sie für die User einer
     * bestimmten Gruppe bestimmt ist.
     * @param datePollID Id der betreffenden datePoll
     * @param groupID Id der betreffenden Gruppe
     * @return DatePoll
     */
    public DatePoll publicationForGroup(final DatePollID datePollID, final GroupId groupID) {
        DatePoll datePoll = datePollRepository.getDatePollById(datePollID);
        datePoll.addListOfUsersToParticipants(groupRepository.getUsersFromGroupByGroupId(groupID));
        return datePoll;
    }

    /**
     * Gibt die DatePoll zurück, die nun die Information enthält, dass sie für die übergebenen User
     * bestimmt ist.
     * @param datePollID Id der betreffenden datePoll
     * @param participants Liste der betreffenden User
     * @return DatePoll
     */
    public DatePoll publicationForCertainUsers(final DatePollID datePollID, final List<User> participants) {
        DatePoll datePoll = datePollRepository.getDatePollById(datePollID);
        datePoll.addListOfUsersToParticipants(participants);
        return datePoll;
    }
}
