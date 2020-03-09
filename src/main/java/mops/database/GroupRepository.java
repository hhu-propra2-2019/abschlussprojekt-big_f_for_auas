package mops.database;

import mops.domain.models.Group.Group;
import mops.domain.models.Group.GroupId;
import mops.domain.models.User;

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
    List<User> getUsersFromGroupByGroupId(GroupId groupId);

}
