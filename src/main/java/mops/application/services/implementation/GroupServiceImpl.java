package mops.application.services.implementation;

import mops.application.services.GroupService;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.infrastructure.rest.GroupRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class GroupServiceImpl implements GroupService {

    private final transient GroupRepositoryImpl groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepositoryImpl groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Gibt zurück, ob eine Gruppe existiert.
     * @param groupId Id der Gruppe, die angefragt wird.
     * @return boolean, der angibt, ob die Gruppe existiert.
     */
    @Override
    public boolean groupExists(GroupId groupId) {
        return groupRepository.existsById(groupId);
    }

    @Override
    public Set<Group> getValidGroupsForUser(UserId userId) {
        return new HashSet<>();
    }
}
