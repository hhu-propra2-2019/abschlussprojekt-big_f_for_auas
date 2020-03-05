package mops.applicationServices;

import mops.domain.models.*;
import mops.domain.models.DatePoll.DatePollBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatePollCreateService {
    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public DatePollBuilder initializeDatePoll(final User creator) {
        DatePoll datePoll = new DatePoll();
        DatePollBuilder datePollBuilder = datePoll.builder();
        DatePollID datePollID = new DatePollID();
        datePollBuilder.datePollID(datePollID);
        datePollBuilder.creator(creator);
        return datePollBuilder;
    }

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public DatePollBuilder addDatePollMetaInf(final DatePollBuilder datePollBuilder, final DatePollMetaInf datePollMetaInf) {
        datePollBuilder.datePollMetaInf(new DatePollMetaInf()); //Title Location Description
        return datePollBuilder;
    }

    public DatePollBuilder openOrClosedPoll(final DatePollBuilder datePollBuilder, final boolean openDatePoll) {
        datePollBuilder.usersCanCreateOption(openDatePoll);
        return datePollBuilder;
    }

    @SuppressWarnings("checkstyle:WhitespaceAround")
    public DatePollBuilder initDatePollOptionList(final DatePollBuilder datePollBuilder, final List<DatePollOption> datePollOptions) {
        datePollBuilder.datePollOptionList(datePollOptions);
        return datePollBuilder;
    }

    public DatePollBuilder setDatePollChoiceType(final DatePollBuilder datePollBuilder, final boolean singleChoicePoll) {
        datePollBuilder.singleChoiceDatePoll(singleChoicePoll);
        return datePollBuilder;
    }

    public DatePollBuilder setDatePollChoicePriority(final DatePollBuilder datePollBuilder, final boolean priority) {
        datePollBuilder.priorityChoice(priority);
        return datePollBuilder;
    }

    public DatePollBuilder setDatePollVisibilty(final DatePollBuilder datePollBuilder, final boolean isAnonymous) {
        datePollBuilder.datePollIsAnonymous(isAnonymous);
        return datePollBuilder;
    }

    public DatePoll buildDatePoll(final DatePollBuilder datePollBuilder) {
        return datePollBuilder.build();
    }
}
