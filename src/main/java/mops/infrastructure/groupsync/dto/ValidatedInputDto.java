package mops.infrastructure.groupsync.dto;

import lombok.Data;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;

import java.util.Set;

@Data
public class ValidatedInputDto {

    private boolean errorsOccurred;
    private long status;
    private Set<Group> groups;
    private Set<GroupId> deletedGroups;
}
