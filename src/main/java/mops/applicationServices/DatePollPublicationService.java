package mops.applicationServices;

import mops.domain.models.DatePoll.DatePoll;
import mops.domain.models.DatePoll.DatePollId;
import mops.domain.models.User.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatePollPublicationService {
    /**
     *
     * @param datePollID
     * @return
     */
    public DatePoll publicationByLink(final DatePollId datePollID) {
        DatePoll datePoll = getDatePollByID(datePollID);
        datePoll.setPublicationTypeToPublic(true);
    }

    public DatePoll publicationForGroup(final DatePollId datePollID, final GroupId groupID) {
        DatePoll datePoll = getDatePollByID(datePollID);
        datePoll.setParticipantsToList(getUsersFromGroupByID(groupID));
    }

    public DatePoll publicationForCertainUsers(final DatePollId datePollID, final List<User> participants) {
        DatePoll datePoll = getDatePollByID(datePollID);
        datePoll.setParticipantsToList(participants);
    }
}
