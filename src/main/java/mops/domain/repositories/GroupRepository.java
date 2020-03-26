package mops.domain.repositories;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.user.UserId;

import java.util.Optional;
import java.util.Set;

public interface GroupRepository {
    Optional<Group> load(GroupId groupId);
    void save(Group group);

    boolean exists(GroupId groupId);
    Set<GroupMetaInf> getMetaInfForPublicGroups();
    Set<GroupMetaInf> getMetaInfForGroupsOfUser(UserId userId);
}
