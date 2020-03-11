package mops.controllers.Dto;

import lombok.Data;

import java.util.List;

@Data
public final class DashboardDto {

    private final List<DashboardListItemDto> polls;

    private final int openReopenedPolls;
    private final int ongoingPolls;
    private final int terminatedPolls;
}
