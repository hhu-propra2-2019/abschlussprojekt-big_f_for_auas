package mops.infrastructure.groupsync.dto;

import lombok.Data;
import mops.domain.models.group.Group;

import java.util.Set;

@Data
public class GroupSyncValidDto {

    private long status;
    private Set<Group> groups;
}
