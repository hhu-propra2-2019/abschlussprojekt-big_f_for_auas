package mops.applicationServices;

import mops.domain.models.DatePoll;
import mops.domain.models.DatePollID;
import mops.domain.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatePollPublicationService {
    /**
     *
     * @param datePollID
     * @return
     */
    public DatePoll publicationByLink(final DatePollID datePollID) {
        DatePoll datePoll = getDatePollByID(datePollID);
        datePoll.setPublicationTypeToPublic(true);
    }

    public DatePoll publicationForGroup(final DatePollID datePollID, final GroupId groupID) {
        DatePoll datePoll = getDatePollByID(datePollID);
        datePoll.setParticipantsToList(getUsersFromGroupByID(groupID));
    }

    public DatePoll publicationForCertainUsers(final DatePollID datePollID, final List<User> participants) {
        DatePoll datePoll = getDatePollByID(datePollID);
        datePoll.setParticipantsToList(participants);
    }
}
