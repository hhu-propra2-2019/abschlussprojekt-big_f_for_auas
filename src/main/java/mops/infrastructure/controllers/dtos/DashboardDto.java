package mops.infrastructure.controllers.dtos;

import lombok.Data;

import java.util.List;

@Data
public final class DashboardDto {

    private final List<DashboardItemDto> polls;

    private final int openReopenedPolls;
    private final int ongoingPolls;
    private final int terminatedPolls;
}
