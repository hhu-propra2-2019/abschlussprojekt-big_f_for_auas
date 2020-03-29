package mops.infrastructure.database.repositories.interfaces;

import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;

import java.util.Set;

public interface GroupJpaRepository extends JpaRepository<GroupDao, String> {

    boolean existsGroupDaoById(String id);

    Set<GroupDao> findAllByUserDaosContaining(UserDao userDao);

    void deleteById(@NonNull String groupId);

    // Direkt zu Dto lesen, um nicht für eine Read-Operation ein Entity zu laden,
    // das gemanaged werden muss.
    @Query("SELECT new mops.domain.models.group.GroupMetaInf(g.id, g.title, g.visibility)"
            + " FROM GroupDao AS g WHERE g.visibility = :visibility")
    Set<GroupMetaInf> findAllMetaInfUsingVisibility(GroupVisibility visibility);

    @Query("SELECT new mops.domain.models.group.GroupMetaInf(g.id, g.title, g.visibility)"
            + " FROM GroupDao AS g WHERE :user MEMBER OF g.userDaos AND g.visibility = :visibility")
    Set<GroupMetaInf> findAllMetaInfForUser(UserDao user, GroupVisibility visibility);
}
