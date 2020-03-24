package mops.adapters.datepolladapter;

import mops.controllers.dtos.DashboardItemDto;
import mops.controllers.dtos.DatePollEntryDto;
import mops.controllers.dtos.DatePollUserEntryOverview;
import mops.controllers.dtos.FormattedDatePollEntryDto;
import mops.domain.models.PollLink;
import mops.domain.models.user.UserId;

import java.util.Set;

public interface DatePollEntryAdapterInterface {

     DatePollUserEntryOverview showUserEntryOverview(PollLink link, UserId user);
     Set<DatePollEntryDto> showAllEntries(PollLink link);
     Set<FormattedDatePollEntryDto> getAllEntriesFormatted(PollLink link);
     Set<DashboardItemDto> getAllListItemDtos(UserId userId);
}
