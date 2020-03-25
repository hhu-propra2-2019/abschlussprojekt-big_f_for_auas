package mops.infrastructure.groupsync.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupSyncDto {

    private long status = 0;
    private Set<GroupDto> groupList;

}
