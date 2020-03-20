package mops.application.services;

import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("checkstyle:DesignForExtension")
@Service
public class DatePollVoteService {

    private final transient DatePollRepository datePollRepository;

    @Autowired
    public DatePollVoteService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

    @SuppressWarnings({"PMD.LawOfDemeter"})// Nullcheck
    public DatePollBallot showUserVotes(UserId id, DatePollLink link) {
        final DatePoll poll = datePollRepository.load(link).orElseThrow();
        return poll.getUserBallot(id).orElseThrow();
    }
}
