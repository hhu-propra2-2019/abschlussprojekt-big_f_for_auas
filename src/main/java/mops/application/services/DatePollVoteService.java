package mops.application.services;


import mops.controllers.dtos.DatePollEntryDto;
import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollEntry;
import mops.domain.models.datepoll.DatePollLink;
import mops.domain.models.user.UserId;
import mops.domain.repositories.DatePollRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DatePollVoteService {


    private final transient DatePollRepository datePollRepository;

    public DatePollVoteService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

    public void vote(DatePollLink link, UserId user, Set<DatePollEntryDto> yes, Set<DatePollEntryDto> maybe) {
        Optional<DatePoll> datePoll = datePollRepository.load(link);
        if(datePoll.isPresent()) {
            datePoll.get().castBallot(user, convert(yes), convert(maybe));
        } else {
            // ?
        }
    }

    private Set<DatePollEntry> convert(Set<DatePollEntryDto> dtos) {
        return dtos.stream()
                .map(dto -> new DatePollEntry(dto.getSuggestedPeriod()))
                .collect(Collectors.toSet());
    }
}
