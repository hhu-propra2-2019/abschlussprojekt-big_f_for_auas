package mops.application.services.implementation;

import mops.application.services.GroupService;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.domain.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    private final transient GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Prüft ob der User mitglied in der Gruppe ist.
     *
     * @param userId
     * @param groupId
     * @return boolean, der angibt true wird wenn der Nutzer Mitglied ist.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    public boolean isUserInGroup(UserId userId, GroupId groupId) {
        final Group group = groupRepository.load(groupId).orElseThrow();
        return group.getUser().stream()
                .anyMatch(id -> id.equals(userId));
    }
}
