package mops.infrastructure.rest;

import lombok.NoArgsConstructor;
import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.user.UserId;
import mops.domain.repositories.GroupRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@NoArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {
    /**
     * Fragt eine Gruppe bei der Gruppen Gruppe an.
     * @param groupId Id der zu ladenden Gruppe
     * @return
     */
    @Override
    public Group getGroupById(GroupId groupId) {
        throw new UnsupportedOperationException();
    }

    /**
     * Fragt alle Nutzer in einer Gruppe bei der Gruppen gruppe an.
     * @param groupId GruppenId, der Gruppe, von der man die Benutzer laden möchte.
     * @return
     */
    @Override
    public Set<UserId> getUsersFromGroupByGroupId(GroupId groupId) {
        throw new UnsupportedOperationException();
    }
}
