package mops.applicationServices;

import lombok.Getter;
import mops.domain.models.DatePoll;
import mops.domain.models.DatePoll.DatePollBuilder;
import mops.domain.models.DatePollConfig;

public class DatePollBuilderAndView {
    @Getter
    private final DatePollBuilder builder;
    private final DatePoll invalidStatePoll;
    @Getter
    private final DatePollConfig unfinishedConfig;

    public DatePollBuilderAndView(DatePollBuilder builder, DatePollConfig config) {
        this.builder = builder;
        this.unfinishedConfig = config;
        builder.datePollConfig(config);
        this.invalidStatePoll = builder.build();

    }

    private DatePollBuilderAndView(DatePollBuilder builder) {
        this.builder = builder;
        this.invalidStatePoll = builder.build();
        this.unfinishedConfig = invalidStatePoll.getDatePollConfig();
    }

    public DatePollBuilderAndView updateState(DatePollBuilder builder) {
        return new DatePollBuilderAndView(builder);
    }


}
