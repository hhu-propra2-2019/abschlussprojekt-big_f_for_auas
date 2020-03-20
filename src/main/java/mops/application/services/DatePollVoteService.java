package mops.application.services;


import mops.controllers.dtos.DatePollEntryDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DatePollVoteService {


    private final transient DatePollRepository datePollRepository;

    public DatePollVoteService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

    public void vote(DatePollLink link, UserId user, Set<DatePollEntry> yes, Set<DatePollEntry> maybe) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if(datePoll.isPresent()) {
            datePoll.get().castBallot(user, yes, maybe);
        } else {
            // ?
        }
    }

    public Set<DatePollEntryDto> getEntryDtos(DatePollLink link) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if(datePoll.isPresent()) {
            datePoll.get().getDatePollEntries();
        } else {
            // ?
        }
    }

    public boolean isUserParticipant(UserId user, DatePollLink link) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if(datePoll.isPresent()) {
            datePoll.get().isUserParticipant(user);
        } else {
            // ?
        }
    }
}
