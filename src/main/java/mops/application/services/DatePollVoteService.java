package mops.application.services;


import mops.domain.repositories.DatePollRepository;
import org.springframework.stereotype.Service;

@Service
public class DatePollVoteService {


    private final transient DatePollRepository datePollRepository;

    public DatePollVoteService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
    }

}
