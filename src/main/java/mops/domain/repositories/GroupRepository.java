package mops.domain.repositories;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;

import java.util.List;

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
     * Methodenkopf für eine Methode, von der man eine Liste der Benutzer, die einer Gruppe angehören zurückbekommt.
     * @param groupId GruppenId, der Gruppe, von der man die Benutzer laden möchte.
     * @return List<User>
     */
    List<UserId> getUsersFromGroupByGroupId(GroupId groupId);

}
