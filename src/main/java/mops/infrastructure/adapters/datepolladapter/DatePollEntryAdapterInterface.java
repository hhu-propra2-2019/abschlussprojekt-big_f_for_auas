package mops.infrastructure.adapters.datepolladapter;

import mops.infrastructure.controllers.dtos.DashboardItemDto;
import mops.infrastructure.controllers.dtos.DatePollEntryDto;
import mops.infrastructure.controllers.dtos.DatePollUserEntryOverview;
import mops.infrastructure.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;

import java.util.Set;

public interface DatePollEntryAdapterInterface {

     DatePollUserEntryOverview showUserEntryOverview(PollLink link, UserId user);
     Set<DatePollEntryDto> showAllEntries(PollLink link);
     Set<FormattedDatePollEntryDto> getAllEntriesFormatted(PollLink link);
     Set<DashboardItemDto> getAllListItemDtos(UserId userId);
     void vote(PollLink link, UserId user, DatePollUserEntryOverview overview);
}
