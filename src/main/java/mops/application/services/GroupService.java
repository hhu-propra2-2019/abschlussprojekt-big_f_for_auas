package mops.application.services;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;

import java.util.Set;

public interface GroupService {

    boolean groupExists(GroupId groupId);

    /**
     * Gibt alle Gruppen bzw. deren IDs zurück, die eine/e Nutzer*in zu einer Abstimmung hinzufügen kann.
     * Dazu zählen:
     *  - alle öffentlichen Gruppen
     *  - alle privaten Gruppen in denen die/der Nutzer*in Mitglied ist
     * @param userId ...
     * @return ...
     */
    Set<Group> getValidGroupsForUser(UserId userId);
}
