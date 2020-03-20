package mops.domain.repositories;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;

import java.util.Set;

/**
 * Repository interface für datePolls.
 */
public interface GroupRepository {
    /**
     * Methodenkopf für die Group-Lademethode.
     * @param groupId Id der zu ladenden Gruppe
     * @return DatePoll;
     */
    Group getGroupById(GroupId groupId);

    /**
     * Methodenkopf für eine Methode, von der man ein Set der Benutzer, die einer Gruppe angehören zurückbekommt.
     * @param groupId GruppenId, der Gruppe, von der man die Benutzer laden möchte.
     * @return List<User>
     */
    Set<UserId> getUsersFromGroupByGroupId(GroupId groupId);

}
