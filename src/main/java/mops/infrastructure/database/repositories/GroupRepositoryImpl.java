package mops.infrastructure.database.repositories;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.models.group.GroupMetaInf;
import mops.domain.models.group.GroupVisibility;
import mops.domain.models.user.UserId;
import mops.domain.repositories.GroupRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.UserDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.GroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final transient GroupJpaRepository groupJpaRepository;

    @Autowired
    public GroupRepositoryImpl(GroupJpaRepository groupJpaRepository) {
        this.groupJpaRepository = groupJpaRepository;
    }

    /**
     * Die Methode soll falls vorhanden eine Gruppe aus der Datenbank laden.
     * @param groupId ID der Gruppe, welche aus der Datenbank geladen werden soll.
     * @return Die Gruppe aus der Datenbank.
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    @Override
    public Optional<Group> load(GroupId groupId) {
        final Optional<GroupDao> dao = groupJpaRepository.findById(groupId.getId());
        final Optional<Group> result;
        if (dao.isPresent()) {
            final GroupDao present = dao.get();
            result = Optional.of(ModelOfDaoUtil.groupOf(present));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    /**
     * Die Methode speichert die Gruppe in die Datenbank.
     * @param group Die zu speichernde Gruppe.
     */
    @Override
    public void save(Group group) {
        final GroupDao dao = DaoOfModelUtil.groupDaoOf(group);
        groupJpaRepository.save(dao);
    }

    /**
     * Löscht eine Gruppe anhand ihrer ID.
     * @param groupId Die ID der zu löschenden Gruppe
     */
    @Override
    public void deleteById(GroupId groupId) {
        if (groupJpaRepository.existsGroupDaoById(groupId.getId())) {
            groupJpaRepository.deleteById(groupId.getId());
        }
    }

    /**
     * Gibt zurück, ob eine Gruppe existiert.
     * @param groupId Die Gruppen-ID der abzufragenden Gruppe
     * @return true, wenn die Gruppe existiert, ansonsten false
     */
    @Override
    public boolean exists(GroupId groupId) {
        return groupJpaRepository.existsGroupDaoById(groupId.getId());
    }

    /**
     * Gibt die Metainformationen aller öffentlichen Gruppen zurück.
     * @return ...
     */
    @Override
    public Set<GroupMetaInf> getMetaInfForPublicGroups() {
        return groupJpaRepository.findAllMetaInfUsingVisibility(GroupVisibility.PUBLIC);
    }

    /**
     * Gibt die Metainformationen aller Gruppen zurück, in denen ein User Mitglied ist.
     * @param userId
     * @return
     */
    @Override
    public Set<GroupMetaInf> getMetaInfForPrivateGroupsOfUser(UserId userId) {
        final UserDao user = new UserDao();
        user.setId(userId.getId());
        return groupJpaRepository.findAllMetaInfForUser(user, GroupVisibility.PRIVATE);
    }
}
