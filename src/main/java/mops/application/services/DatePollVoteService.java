package mops.application.services;

import mops.controllers.dtos.DatePollEntryDto;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBallot;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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


    public void vote(DatePollLink link, UserId user, Set<DatePollEntry> yes, Set<DatePollEntry> maybe) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if (datePoll.isPresent()) {
            datePoll.get().castBallot(user, yes, maybe);
        }
    }

    public Set<DatePollEntryDto> getEntryDtos(DatePollLink link) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if (datePoll.isPresent()) {
            datePoll.get().getDatePollEntries();
        }
    }

    public boolean isUserParticipant(UserId user, DatePollLink link) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if (datePoll.isPresent()) {
            datePoll.get().isUserParticipant(user);
        }
    }
}
