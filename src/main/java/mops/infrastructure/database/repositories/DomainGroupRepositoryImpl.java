package mops.infrastructure.database.repositories;

import mops.domain.models.group.Group;
import mops.domain.models.group.GroupId;
import mops.domain.repositories.DomainGroupRepository;
import mops.infrastructure.database.daos.GroupDao;
import mops.infrastructure.database.daos.translator.DaoOfModelUtil;
import mops.infrastructure.database.daos.translator.ModelOfDaoUtil;
import mops.infrastructure.database.repositories.interfaces.GroupJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DomainGroupRepositoryImpl implements DomainGroupRepository {

    private final transient GroupJpaRepository groupJpaRepository;

    @Autowired
    public DomainGroupRepositoryImpl(GroupJpaRepository groupJpaRepository) {
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
}