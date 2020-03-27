package mops.application.services;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

//TODO: Refactoring im Sinne der vorhandenen Repositories
@SuppressWarnings({"PMD.LawOfDemeter", "checkstyle:DesignForExtension"})
@Service
public class DatePollVoteService {

    public static final String NOT_A_MEMBER_OF_THIS_POLL = "User is not a member of this Poll";
    private final transient DatePollRepository datePollRepository;
    private final transient GroupService groupService;

    @Autowired
    public DatePollVoteService(DatePollRepository datePollRepository, GroupService groupService) {
        this.datePollRepository = datePollRepository;
        this.groupService = groupService;
    }

    public DatePollBallot showUserVotes(UserId id, PollLink link) {
        final DatePoll poll = datePollRepository.load(link).orElseThrow();
        if (checkUserIsParticipant(id, poll)) {
            return poll.getUserBallot(id).orElseThrow();
        } else {
            throw new IllegalArgumentException(NOT_A_MEMBER_OF_THIS_POLL);
        }
    }

    public void vote(PollLink link, UserId user, Set<DatePollEntry> yes, Set<DatePollEntry> maybe) {
        final DatePoll datepoll = datePollRepository.load(link).orElseThrow();
        if (checkUserIsParticipant(user, datepoll)) {
            datepoll.castBallot(user, yes, maybe);
            datePollRepository.save(datepoll);
        } else {
            throw new IllegalArgumentException(NOT_A_MEMBER_OF_THIS_POLL);
        }
    }

    private boolean checkUserIsParticipant(UserId id, DatePoll poll) {
        if (poll.getConfig().isOpen()) {
            return true;
        } else {
            return userIsParticipantInGroups(id, poll.getGroups());
        }
    }

    private boolean userIsParticipantInGroups(UserId id, Set<GroupId> groups) {
        return groups.stream()
                .anyMatch(group -> groupService.isUserInGroup(id, group));
    }
}
