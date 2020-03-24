package mops.domain.repositories;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;

import java.util.Optional;

public interface DomainGroupRepository {

    Optional<Group> load(GroupId groupId);

    void save(Group group);
}
