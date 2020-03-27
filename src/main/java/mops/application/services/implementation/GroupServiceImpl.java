package mops.application.services.implementation;

import mops.application.services.GroupService;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.infrastructure.rest.GroupRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    private final transient GroupRepositoryImpl groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepositoryImpl groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Gibt zurück, ob eine Gruppe existiert.
     *
     * @param groupId Id der Gruppe, die angefragt wird.
     * @return boolean, der angibt, ob die Gruppe existiert.
     */
    @Override
    public boolean groupExists(GroupId groupId) {
        return groupRepository.existsById(groupId);
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
        final Group group = groupRepository.getGroupById(groupId);
        return group.getUser().stream()
                .anyMatch(id -> id.equals(userId));
    }
}
